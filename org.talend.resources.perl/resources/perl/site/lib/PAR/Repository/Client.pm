package PAR::Repository::Client;

use 5.006;
use strict;
use warnings;

our $VERSION = '0.21';

# list compatible repository versions
# This is a list of numbers of the form "\d+.\d".
# Before comparison, any versions are reduced to the
# first digit after the period.
# Incompatible changes require a change in version in the
# first digit after the period.
our $Compatible_Versions = {
    '0.1' => 1,
};

use constant MODULES_DBM_FILE     => 'modules_dists.dbm';
use constant SYMLINKS_DBM_FILE    => 'symlinks.dbm';
use constant SCRIPTS_DBM_FILE     => 'scripts_dists.dbm';
use constant REPOSITORY_INFO_FILE => 'repository_info.yml';
use constant DBM_CHECKSUMS_FILE   => 'dbm_checksums.txt';

use base 'PAR::Repository::Query';
use base 'PAR::Repository::Client::Util';

require PAR::Repository::Client::HTTP;
require PAR::Repository::Client::Local;

use Carp qw/croak/;
use File::Spec::Functions qw/splitpath/;
require version;
require Config;
require PAR::Dist;
require DBM::Deep;
require Archive::Zip;
require File::Temp;
require File::Copy;
require YAML::Tiny;

=head1 NAME

PAR::Repository::Client - Access PAR repositories

=head1 SYNOPSIS

  use PAR::Repository::Client;
  
  my $client = PAR::Repository::Client->new(
    uri => 'http://foo/repository',
    http_timeout => 20, # default is 180s
  );
  
  # This is happening at run-time, of course:
  # But calling import from your namespace
  $client->use_module('Foo::Bar') or die $client->error;
  
  $client->require_module('Bar::Baz') or die $client->error;
  
  $client->run_script('foo', 'arg1', 'arg2') or die $client->error;
  # should not be reached since we ran 'foo'!

=head1 DESCRIPTION

This module represents the client for PAR repositories as
implemented by the L<PAR::Repository> module.

Chances are, you should be looking at the L<PAR> module
instead. Starting with version 0.950, it supports
automatically loading any modules that aren't found on your
system from a repository. If you need finer control than that,
then this module is the right one to use.

You can use this module to access repositories in one of
two ways: On your local filesystem or via HTTP. The
access methods are implemented in
L<PAR::Repository::Client::HTTP> and L<PAR::Repository::Client::Local>.
Any common code is in this module.

L<PAR::Repository::Query> implements the querying interface. The methods
described in that module's documentation can be called on
C<PAR::Repository::Client> objects.

=head2 PAR REPOSITORIES

For a detailed discussion of the structure of PAR repositories, please
have a look at the L<PAR::Repository> distribution.

A PAR repository is, well, a repository of F<.par> distributions which
contain Perl modules and scripts. You can create F<.par> distributions
using the L<PAR::Dist> module or the L<PAR> module itself.

If you are unsure what PAR archives are, then have a look
at the L<SEE ALSO> section below, which points you at the
relevant locations.

=head1 PUBLIC METHODS

Following is a list of class and instance methods.
(Instance methods until otherwise mentioned.)

=cut

=head2 new

Creates a new PAR::Repository::Client object. Takes named arguments. 

Mandatory paramater:

I<uri> specifies the URI of the repository to use. Initially, http and
file URIs will be supported, so you can access a repository locally
using C<file:///path/to/repository> or just with C</path/to/repository>.
HTTP accessible repositories can be specified as C<http://foo> and
C<https://foo>.

If the optional I<auto_install> parameter is set to a true value
(default: false), any F<.par> file that is about to be loaded is
installed on the local system instead. In this context, please
refer to the C<install_module()> method.

Similar to I<auto_install>, the I<auto_upgrade> parameter installs
a distribution that is about to be loaded - but only if the
specified module does not exist on the local system yet or is outdated.

You cannot set both I<auto_install> and I<auto_upgrade>. If you do,
you will get a fatal error.

In order to control where the modules are installed to, you can
use the C<installation_targets> method.

The optional C<architecture> and C<perl_version> parameters
can be used to specify the architecture and perl version that are
used to choose the right PAR archives from the repository.
Defaults to your running perl, so
please read the comments on C<architecture> and C<perl_version>
below before blindly using this.

Upon client creation, the repository's version is validated to be
compatible with this version of the client.

You may specify a C<http_timeout> in seconds.

=cut

sub new {
  my $proto = shift;
  my $class = ref($proto) || $proto;

  croak(__PACKAGE__."->new() takes an even number of arguments.")
    if @_ % 2;
  my %args = @_;

  croak(__PACKAGE__."->new() needs an 'uri' argument.")
    if not defined $args{uri};

  my $uri = $args{uri};

  my $obj_class = 'Local';
  if ($uri =~ /^https?:\/\//) {
    $obj_class = 'HTTP';
  }

  if ($args{auto_install} and $args{auto_upgrade}) {
    croak(__PACKAGE__."->new(): You can only specify one of 'auto_upgrade' and 'auto_install'");
  }

  my $self = bless {
    # the repository uri
    uri => $uri,
    # The last error message
    error => '',
    # The hash ref of checksums for checking whether we
    # need to update the dbms
    checksums => undef,
    supports_checksums => undef,
    # the modules- and scripts dbm storage
    # both the local temp file for cleanup
    # and the actual tied hash
    modules_dbm_temp_file => undef,
    modules_dbm_hash => undef,
    scripts_dbm_temp_file => undef,
    scripts_dbm_hash => undef,
    info => undef, # used for YAML info caching
    auto_install => $args{auto_install},
    auto_upgrade => $args{auto_upgrade},
    installation_targets => {}, # see PAR::Dist
    perl_version => (defined($args{perl_version}) ? $args{perl_version} : $Config::Config{version}),
    architecture => (defined($args{architecture}) ? $args{architecture} : $Config::Config{archname}),
  } => "PAR::Repository::Client::$obj_class";

  $self->_init(\%args);

  $self->validate_repository()
    or croak $self->{error};

  return $self;
}



=head2 require_module

First argument must be a package name (namespace) to require.
The method scans the repository for distributions that
contain the specified package.

When one or more distributions are found, it determines which
distribution to use using the C<prefered_distribution()> method.

Then, it fetches the prefered F<.par> distribution from the
repository and opens it using the L<PAR> module. Finally,
it loads the specified module from the downloaded
F<.par> distribution using C<require()>.

Returns 1 on success, the empty list on failure. In case
of failure, an error message can be obtained with the
C<error()> method.

=cut

sub require_module {
  my $self = shift;
  my $namespace = shift;
  $self->{error} = undef;

  # fetch the module, load preferably (fallback => 0)
  my $file = $self->get_module($namespace, 0);

  eval "require $namespace;";
  if ($@) {
    $self->{error} = "An error occurred while executing 'require $namespace;'. Error: $@";
    return();
  }

  return 1;
}


=head2 use_module

Works the same as the C<require_module> method except that
instead of only requiring the specified module, it also
calls the C<import> method if it exists. Any arguments to
this methods after the package to load are passed to the
C<import> call.

=cut

sub use_module {
  my $self = shift;
  my $namespace = shift;
  my @args = @_;
  $self->{error} = undef;

  my ($pkg) = caller();

  my $required = $self->require_module($namespace);
  return() if not $required; # error set by require_module

    eval "package $pkg; ${namespace}->import(\@args) if ${namespace}->can('import');";
  if ($@) {
    $self->{error} = "An error occurred while executing 'package $pkg; ${namespace}->import(\@args);'. Error: $@";
    return();
  }
  return 1;
}

=head2 get_module

First parameter must be a namespace, second parameter may be
a boolean indicating whether the PAR is a fallback-PAR or one
to load from preferably. (Defaults to false which means
loading preferably.)

Searches for a specified namespace in the repository and downloads
the corresponding PAR distribution. Automatically loads PAR
and appends the downloaded PAR distribution to the list of
PARs to load from.

Returns the name of the local
PAR file. Think of this as C<require_module> without actually
doing a C<require()> of the module.

=cut


sub get_module {
  my $self = shift;
  my $namespace = shift;
  my $fallback = shift;

  $self->{error} = undef;

  my $local_par_file;
  if ($self->{auto_install}) {
    $local_par_file = $self->install_module($namespace);
  }
  elsif ($self->{auto_upgrade}) {
    $local_par_file = $self->upgrade_module($namespace);
  }
  else {
    $local_par_file = $self->_fetch_module($namespace);
    return() if not defined $local_par_file;

    require PAR;
    PAR->import( { file => $local_par_file, fallback => ($fallback?1:0) } );
  }
  return() if not defined $local_par_file;

  return $local_par_file;
}

sub _fetch_module {
  my $self = shift;
  my $namespace = shift;

  $self->{error} = undef;

  my ($modh) = $self->modules_dbm;
  if (not defined $modh) {
    return();
  }

  my $dists = $modh->{$namespace};
  if (not defined $dists) {
    $self->{error} = "Could not find module '$namespace' in the repository.";
    return();
  }

  my $dist = $self->prefered_distribution($namespace, $dists);
  if (not defined $dist) {
    $self->{error} = "PAR: Could not find a distribution for package '$namespace'";
    return();
  }

  my $local_par_file = $self->fetch_par($dist);
  if (not defined $local_par_file or not -f $local_par_file) {
    return();
  }

  return $local_par_file;
}

=head2 install_module

Works the same as C<get_module> but instead of loading the
F<.par> file using PAR, it installs its contents using
L<PAR::Dist>'s C<install_par()> routine.

First argument must be the namespace of a module to install.

Note that this method always installs the whole F<.par> distribution
that contains the newest version of the specified namespace and not
only the F<.pm> file from the distribution which contains the
specified namespace.

Returns the name of the local F<.par> file which was installed or
the empty list on failure.

=cut

sub install_module {
  my $self = shift;
  my $namespace = shift;

  $self->{error} = undef;

  my $local_par_file = $self->_fetch_module($namespace);
  return() if not defined $local_par_file;

  PAR::Dist::install_par(
    %{$self->installation_targets()},
    dist => $local_par_file,
  ) or return ();

  return $local_par_file;
}


=head2 upgrade_module

Works the same as C<get_module> but instead of loading the
F<.par> file using PAR, it checks whether the local version of
the module is current. If it isn't, the distribution containing
the newest version of the module is installed using
L<PAR::Dist>'s C<install_par()> routine.

First argument must be the namespace of a module to upgrade.

Note that this method always installs the whole F<.par> distribution
that contains the newest version of the specified namespace and not
only the F<.pm> file from the distribution which contains the
specified namespace.

Returns the name of the local F<.par> file which was installed or
the empty list on failure or if the local version of the module is
already current.

=cut

sub upgrade_module {
  my $self = shift;
  my $namespace = shift;

  $self->{error} = undef;

  # get local version
  my $local_version;
  eval "require $namespace; \$local_version = ${namespace}->VERSION;";

  # no local version found. Install from repo
  if (not defined $local_version) {
    my $mod = $namespace;
    $mod =~ s/::/\//;
    $mod .= '.pm';
    delete $INC{$mod};
    return $self->install_module($namespace);
  }

  # The following code is all for determining the newest
  # version in the repository.
  my ($modh) = $self->modules_dbm;
  if (not defined $modh) {
    return();
  }

  my $dists = $modh->{$namespace};
  if (not defined $dists) {
    $self->{error} = "Could not find module '$namespace' in the repository.";
    return();
  }

  my $dist = $self->prefered_distribution($namespace, $dists);
  if (not defined $dist) {
    $self->{error} = "PAR: Could not find a distribution for package '$namespace'";
    return();
  }

  my $repo_version = $modh->{$namespace}{$dist};

  if ($repo_version > $local_version) {
    my $mod = $namespace;
    $mod =~ s/::/\//;
    $mod .= '.pm';
    delete $INC{$mod};
    return $self->install_module($namespace);
  }

  return();
}


=head2 run_script

First parameter must be a script name.

Searches for a specified script in the repository and downloads
the corresponding PAR distribution. Automatically loads PAR
and appends the downloaded PAR distribution to the list of
PARs to load from.

Then, it runs the script. It does not return unless some error occurrs.

If either I<auto_install> or I<auto_upgrade> were specified as
parameters to the constructor, the downloaded PAR distribution will
be installed regardless of the versions of any previously installed
scripts. This differs from the behaviour for mdoules.

=cut


sub run_script {
  my $self = shift;
  my $script = shift;

  $self->{error} = undef;

  my ($scrh) = $self->scripts_dbm;
  if (not defined $scrh) {
    return();
  }

  my $dists = $scrh->{$script};
  if (not defined $dists) {
    $self->{error} = "Could not find script '$script' in the repository.";
    return();
  }
  my $dist = $self->prefered_distribution($script, $dists);
  if (not defined $dist) {
    $self->{error} = "PAR: Could not find a distribution for script '$script'";
    return();
  }

  my $local_par_file = $self->fetch_par($dist);
  if (not defined $local_par_file or not -f $local_par_file) {
    return();
  }

  if ($self->{auto_install}) {
    PAR::Dist::install_par(
        %{ $self->installation_targets() },
        dist => $local_par_file,
        ) or return ();
  }
  elsif ($self->{auto_upgrade}) {
    # FIXME This is not the right way to do it!
    PAR::Dist::install_par(
        %{ $self->installation_targets() },
        dist => $local_par_file,
        ) or return ();
  }
  
  require PAR;
  PAR->import( { file => $local_par_file, run => $script } );

  # doesn't happen!?
  return 1;
}


=head2 installation_targets

Sets the installation targets for modules and scripts if any arguments are
passed. Returns the current setting otherwise.

Arguments should be key/value pairs of installation targets
as recognized by the C<install_par()> routine in L<PAR::Dist>.
The contents of this hash are passed verbatim to every call to
C<install_par()> made by this package.

In this context, note that aside from the normal i<inst_lib> and similar
targets, you can also specify a I<custom_targets> element starting with
C<PAR::Dist> version 0.20. For details, refer to the L<PAR::Dist> manual.

Returns a hash reference to a hash containing the installation targets.

=cut

sub installation_targets {
  my $self = shift;
  if (not @_) {
    return {%{$self->{installation_targets}}};
  }
    
  my %args = @_;

  $self->{installation_targets} = \%args;
  return {%{$self->{installation_targets}}};
}


=head2 error

Returns the last error message if there was an error or
the empty list otherwise.

=cut


sub error {
  my $self = shift;
  my $err = $self->{error};
  return(defined($err) ? $err : ());
}

=head1 ACCESSORS

These methods get or set some attributes of the repository client.

=head2 perl_version

Sets and/or returns the perl version which is used to choose the right
C<.par> packages from the repository. Defaults to the currently running
perl version (from C<%Config>).

You'd better know what you're doing if you plan to set this to something
you're not actually running. One valid use is if you use the
C<installation_targets> possibly in conjunction with
L<ExtUtils::InferConfig> to install into a different perl than the
one that's running!

=cut

sub perl_version {
  my $self = shift;
  $self->{perl_version} = shift @_ if @_;
  return $self->{perl_version};
}

=head2 architecture 

Sets and/or returns the name of the architecture which is used to choose the right
C<.par> packages from the repository. Defaults to the currently running
architecture (from C<%Config>).

You'd better know what you're doing if you plan to set this to something
you're not actually running. One valid use is if you use the
C<installation_targets> possibly in conjunction with
L<ExtUtils::InferConfig> to install into a different perl than the
one that's running!

=cut

sub architecture {
  my $self = shift;
  $self->{architecture} = shift @_ if @_;
  return $self->{architecture};
}

=head1 OTHER METHODS

These methods, while part of the official interface, should need rarely be
called by most users.

=head2 prefered_distribution

This method decides from which distribution a module will be loaded.
It returns the corresponding distribution file name.

Takes a namespace as first argument followed by a reference
to a hash of distribution file names with associated module
versions. Example:

  'Math::Symbolic',
  { 'Math-Symbolic-0.502-x86_64-linux-gnu-thread-multi-5.8.7.par' => '0.502',
    'Math-Symbolic-0.128-any_arch-any_version.par' => '0.128'
  }

This means that the C<Math::Symbolic> namespace was found in version C<0.502>
and C<0.128> in said distribution files. If you were using linux on an x86_64
computer using perl 5.8.7, this would return the first file name. Otherwise,
you would only get version C<0.128>.

=cut

sub prefered_distribution {
  my $self = shift;
  $self->{error} = undef;
  my $ns = shift;
  my $dists = shift;

  # potentially faster not to query the db here and rely
  # on the while/each
  #return() if not keys %$dists;

  my $this_pver = $self->perl_version;
  my $this_arch = $self->architecture;

  my @sorted;
  while (my ($dist, $ver) = each(%$dists)) {
    # distfile, version, distname, distver, arch, pver
    my $version = version->new($ver||0);
    my ($n, $v, $a, $p) = PAR::Dist::parse_dist_name($dist);
    next if not defined $a or not defined $p;
    # skip the ones for other archs
    next if $a ne $this_arch and $a ne 'any_arch';
    next if $p ne $this_pver and $p ne 'any_version';

    # as a fallback while sorting, prefer arch and pver
    # specific dists to fallbacks
    my $order_num =
      ($a eq 'any_arch' ? 2 : 0)
      + ($p eq 'any_version' ? 1 : 0);
    push @sorted, [$dist, $version, $order_num];
  }
  return() if not @sorted;

  # sort by version, highest first.
  @sorted =
    sort {
      # sort version
      $b->[1] <=> $a->[1]
      or
      # specific before any_version before any_arch before any_*
      $a->[2] <=> $b->[2]
    }
  @sorted;

  my $dist = shift @sorted;
  return $dist->[0];
}

=head2 validate_repository_version

Accesses the repository meta information and validates that it
has a compatible version. This is done on object creation, so
it should not normally be necessary to call this from user code.

Returns a boolean indicating the outcome of the operation.

=cut

sub validate_repository_version {
  my $self = shift;
  $self->{error} = undef;

  my $info = $self->_repository_info;
  if (not defined $info) {
    return();
  }
  elsif (not exists $info->[0]{repository_version}) {
    $self->{error} = "Repository info file ('repository_info.yml') does not contain a version.";
    return();
  }

  # check for compatibility
  my $repo_version = $info->[0]{repository_version};

  my $main_repo_version = $repo_version;
  $main_repo_version =~ s/^(\d+\.\d).*$/$1/;

  if ( not exists $PAR::Repository::Client::Compatible_Versions->{$main_repo_version} ) {
    $self->{error} = "Repository has an incompatible version (".$info->[0]{repository_version}.")";
    return();
  }
  return 1;
}

=head2 need_dbm_update

Takes one or no arguments. Without arguments, all DBM files are
checked. With an argument, only the specified DBM file will be checked.

Returns true if either one of the following conditions match:

=over 2

=item

The repository does not support checksums.

=item

The checksums (and thus also the DBM files) haven't been
downloaded yet.

=item

The local copies of the checksums do not match those of the repository.

=back

In cases two and three above, the return value is actually the hash
reference of checksums that was fetched from the repository.

Returns the empty list if the local checksums match those of the
repository exactly.

You don't usually need to call this directly. By default, DBM files
are only fetched from the repository if necessary.

=cut

sub need_dbm_update {
  my $self = shift;
  $self->{error} = undef;

  my $check_file = shift;
  $check_file .= '.zip' if defined $check_file and not $check_file =~ /\.zip$/;

  my $support = $self->{supports_checksums};
  if (defined $support and not $support) {
    return 1;
  }

  my $checksums = $self->_dbm_checksums();

  if (not defined $checksums) {
    $self->{supports_checksums} = 0;
    return 1;
  }
  else {
    $self->{supports_checksums} = 1;
  }

  if (not defined $self->{checksums}) {
    # never fetched checksums before.
    return $checksums;
  }
  else {
    # we fetched checksums earlier, match them
    my $local_checksums = $self->{checksums};
    if (not defined $check_file) {
      return $checksums if keys(%$local_checksums) != keys(%$checksums);
      foreach my $file (keys %$checksums) {
        return $checksums
          if not exists $local_checksums->{$file}
          or not $local_checksums->{$file} eq $checksums->{$file};
      }
    }
    else {
      return $checksums
        if not exists $local_checksums->{$check_file}
        or not exists $checksums->{$check_file}
        or not $local_checksums->{$check_file} eq $checksums->{$check_file};
    }
    return 0;
  }
}


=head2 modules_dbm

Fetches the C<modules_dists.dbm> database from the repository,
ties it to a L<DBM::Deep> object and returns a tied hash
reference or the empty list on failure. Second return
value is the name of the local temporary file.

In case of failure, an error message is available via
the C<error()> method.

The method uses the C<_fetch_dbm_file()> method which must be
implemented in a subclass such as L<PAR::Repository::Client::HTTP>.

=cut

sub modules_dbm {
  my $self = shift;
  $self->{error} = undef;

  my $checksums = $self->need_dbm_update();
  if ($self->{modules_dbm_hash}) {
    # need new dbm file?
    return $self->{modules_dbm_hash} if not $checksums;

    # does this particular dbm need to be updated?
    if ($self->{checksums}) {
      my $local_checksum = $self->{checksums}{MODULES_DBM_FILE().".zip"};
      my $remote_checksum = $checksums->{MODULES_DBM_FILE().".zip"};
      return $self->{modules_dbm_hash}
        if defined $local_checksum and defined $remote_checksum
           and $local_checksum eq $remote_checksum;
    }

    # just to make sure
    $self->close_modules_dbm;
  }

  my $file = $self->_fetch_dbm_file(MODULES_DBM_FILE().".zip");
  # (error set by _fetch_dbm_file)
  return() if not defined $file; # or not -f $file; # <--- _fetch_dbm_file should do the stat!

  my ($tempfh, $tempfile) = File::Temp::tempfile(
    'temporary_dbm_XXXXX',
    UNLINK => 0,
    DIR => File::Spec->tmpdir(),
  );

  if (not $self->_unzip_file($file, $tempfile, MODULES_DBM_FILE())) {
    $self->{error} = "Could not unzip dbm file '$file' to '$tempfile'";
    return();
  }

  unlink $file;

  $self->{modules_dbm_temp_file} = $tempfile;

  my %hash;
  my $obj = tie %hash, "DBM::Deep", {
    file => $tempfile,
    locking => 1,
    autoflush => 0,
  }; 

  $self->{modules_dbm_hash} = \%hash;

  # save this dbm file checksum
  if (ref($checksums)) {
    if (not $self->{checksums}) {
      $self->{checksums} = {};
    }
    $self->{checksums}{MODULES_DBM_FILE().".zip"} = $checksums->{MODULES_DBM_FILE().".zip"};
  }

  return (\%hash, $tempfile);
}

=head2 scripts_dbm

Fetches the C<scripts_dists.dbm> database from the repository,
ties it to a L<DBM::Deep> object and returns a tied hash
reference or the empty list on failure. Second return
value is the name of the local temporary file.

In case of failure, an error message is available via
the C<error()> method.

The method uses the C<_fetch_dbm_file()> method which must be
implemented in a subclass such as L<PAR::Repository::Client::HTTP>.

=cut

sub scripts_dbm {
  my $self = shift;
  $self->{error} = undef;

  my $checksums = $self->need_dbm_update();
  if ($self->{scripts_dbm_hash}) {
    # need new dbm file?
    return $self->{scripts_dbm_hash} if not $checksums;

    # does this particular dbm need to be updated?
    if ($self->{checksums}) {
      my $local_checksum = $self->{checksums}{SCRIPTS_DBM_FILE().".zip"};
      my $remote_checksum = $checksums->{SCRIPTS_DBM_FILE().".zip"};
      return $self->{scripts_dbm_hash}
        if defined $local_checksum and defined $remote_checksum
           and $local_checksum eq $remote_checksum;
    }

    # just to make sure
    $self->close_scripts_dbm;
  }

  my $file = $self->_fetch_dbm_file(SCRIPTS_DBM_FILE().".zip");
  # (error set by _fetch_dbm_file)
  return() if not defined $file; # or not -f $file; # <--- _fetch_dbm_file should do the stat!

  my ($tempfh, $tempfile) = File::Temp::tempfile(
    'temporary_dbm_XXXXX',
    UNLINK => 0,
    DIR => File::Spec->tmpdir(),
  );

  if (not $self->_unzip_file($file, $tempfile, SCRIPTS_DBM_FILE())) {
    $self->{error} = "Could not unzip dbm file '$file' to '$tempfile'";
    return();
  }

  unlink $file;

  $self->{scripts_dbm_temp_file} = $tempfile;

  my %hash;
  my $obj = tie %hash, "DBM::Deep", {
    file => $tempfile,
    locking => 1,
    autoflush => 0,
  }; 

  $self->{scripts_dbm_hash} = \%hash;

  # save this dbm file checksum
  if (ref($checksums)) {
    if (not $self->{checksums}) {
      $self->{checksums} = {};
    }
    $self->{checksums}{SCRIPTS_DBM_FILE().".zip"} = $checksums->{SCRIPTS_DBM_FILE().".zip"};
  }

  return (\%hash, $tempfile);
}


=head2 close_modules_dbm

Closes the C<modules_dists.dbm> file and does all necessary
cleaning up.

This is called when the object is destroyed.

=cut

sub close_modules_dbm {
  my $self = shift;
  my $hash = $self->{modules_dbm_hash};
  return if not defined $hash;

  my $obj = tied($hash);
  $self->{modules_dbm_hash} = undef;
  undef $hash;
  undef $obj;

  unlink $self->{modules_dbm_temp_file};
  $self->{modules_dbm_temp_file} = undef;
  if ($self->{checksums}) {
    delete $self->{checksums}{MODULES_DBM_FILE().".zip"};
  }

  return 1;
}

=head2 close_scripts_dbm

Closes the C<scripts_dists.dbm> file and does all necessary
cleaning up.

This is called when the object is destroyed.

=cut

sub close_scripts_dbm {
  my $self = shift;
  my $hash = $self->{scripts_dbm_hash};
  return if not defined $hash;

  my $obj = tied($hash);
  $self->{scripts_dbm_hash} = undef;
  undef $hash;
  undef $obj;

  unlink $self->{scripts_dbm_temp_file};
  $self->{scripts_dbm_temp_file} = undef;
  if ($self->{checksums}) {
    delete $self->{checksums}{SCRIPTS_DBM_FILE().".zip"};
  }

  return 1;
}

=head1 PRIVATE METHODS

These private methods should not be relied upon from the outside of
the module. (See also: L<PAR::Repository::Client::Util>)

=cut

sub DESTROY {
  my $self = shift;
  $self->close_modules_dbm;
  $self->close_scripts_dbm;
}

1;
__END__

=head1 SEE ALSO

This module is directly related to the C<PAR> project. You need to have
basic familiarity with it. Its homepage is at L<http://par.perl.org/>

See L<PAR>, L<PAR::Dist>, L<PAR::Repository>, etc.

L<PAR::Repository::Query> implements the querying interface. The methods
described in that module's documentation can be called on
C<PAR::Repository::Client> objects.

L<PAR::Repository> implements the server side creation and manipulation
of PAR repositories.

L<PAR::WebStart> is doing something similar but is otherwise unrelated.

=head1 AUTHOR

Steffen Mueller, E<lt>smueller@cpan.orgE<gt>

=head1 COPYRIGHT AND LICENSE

Copyright 2006-2008 by Steffen Mueller

This library is free software; you can redistribute it and/or modify
it under the same terms as Perl itself, either Perl version 5.6 or,
at your option, any later version of Perl 5 you may have available.

=cut
