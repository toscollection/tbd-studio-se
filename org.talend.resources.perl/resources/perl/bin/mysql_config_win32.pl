use strict;
use warnings;

my $VERSION = 0.1;

use Getopt::Long;
use File::Basename;
require File::Spec;

BEGIN {
    die 'This script is intended for Win32' unless $^O =~ /Win32/i;
}

my ($mysql_dir, $mysqladmin, $help);
my $result = GetOptions("with-mysql=s" => \$mysql_dir, "help" => \$help);
usage() if $help;

if ($mysql_dir) {
  unless (-d $mysql_dir) {
    die qq{"$mysql_dir" does not exist};
  }
  $mysqladmin = File::Spec->catfile($mysql_dir, 'bin', 'mysqladmin.exe');
  unless (-f $mysqladmin) {
    die qq{"bin/mysqladmin.exe" not found under "$mysql_dir"};
  }
}

my (@path_ext);
unless ($mysqladmin) {
  path_ext();
  $mysqladmin = which('mysqladmin');
  unless ($mysqladmin) {
    die << 'DEATH';

mysqladmin.exe was not found in your PATH.
Please either add the directory containing mysqladmin.exe
to your PATH environment variable, or rerun this script
with the --with-mysql=C:\Path\to\Mysql option, giving the
root directory of your MySQL installation.
DEATH
  }
  ($mysql_dir = dirname($mysqladmin)) =~ s{[/\\]bin[/\\]?$}{};
}

my $basedir = Win32::GetShortPathName($mysql_dir);
my $ldata = File::Spec->catdir($basedir, 'data');
my $execdir = File::Spec->catdir($basedir, 'bin');
my $bindir = File::Spec->catdir($basedir, 'bin');
my $pkglibdir= File::Spec->catdir($basedir, 'lib', 'opt');
my $pkgincludedir = File::Spec->catdir($basedir, 'include');
my $ldflags = '';
my $client_libs = '-lzlib';

$mysqladmin = Win32::GetShortPathName($mysqladmin);
my $v = qx($mysqladmin version);
unless ($v) {
  print STDERR "Problem running $mysqladmin - aborting ...\n";
  exit(1);
}

my ($version, $port);
if ($v =~ /Server version\s+(.*?)\n/m) {
  $version = $1;
}
if ($v =~ /TCP port\s+(.*?)\n/m) {
  $port = $1;
}

my $libs = qq{$ldflags -L"$pkglibdir" -lmysqlclient $client_libs};
my $cflags = qq{-I"$pkgincludedir"};
my $embedded_libs = qq{$ldflags -L"$pkglibdir"};

my $license = <<'EOL';
# Copyright (C) 2005 MySQL AB & MySQL Finland AB & TCX DataKonsult AB
# 
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

# This script reports various configuration settings that may be needed
# when using the MySQL client library.
EOL

my $mysql_config = File::Spec->catfile($basedir, 'bin', 'mysql_config.pl');
open(my $fh, '>', $mysql_config)
  or die "Cannot open $mysql_config for writing: $!";

print $fh <<"EOL";
#!$^X
use strict;
use warnings;
use Getopt::Long;

$license

my \$basedir = q{$basedir};
my \$ldata = q{$ldata};
my \$execdir = q{$execdir};
my \$bindir = q{$bindir};
my \$pkglibdir = q{$pkglibdir};
my \$pkgincludedir = q{$pkgincludedir};
my \$ldflags = q{$ldflags};
my \$client_libs = q{$client_libs};
my \$version = q{$version};
my \$port = q{$port};
my \$libs = q{$libs};
my \$cflags = q{$cflags};
my \$embedded_libs = q{$embedded_libs};
EOL

while (<DATA>) {
  print $fh $_;
}
close $fh;
my @args = ('pl2bat', $mysql_config);
system(@args) == 0 or die "System @args failed: $?";

print << "END";

mysql_config.pl, and an associated bat file, has been 
successfully created under
   $basedir\\bin

END
exit(0);

sub usage {
  print << 'EOU';

Install a Win32 version of mysql_config.

Usage: mysql_config_win32.pl [ --with-mysql=C:\Path\to\MySQL ]

The command-line argument --with-mysql specifies the root 
directory of your MySQL installation. This is optional if your 
PATH environment variable includes your MySQL bin directory.

EOU
  exit(1);
}

sub path_ext {
  if ($ENV{PATHEXT}) {
    push @path_ext, split ';', $ENV{PATHEXT};
    for my $ext (@path_ext) {
      $ext =~ s/^\.*(.+)$/$1/;
    }
  }
  else {
    #Win9X: doesn't have PATHEXT
    push @path_ext, qw(com exe bat);
  }
}

sub which {
  my $program = shift;
  return unless $program;
  my @a = map {File::Spec->catfile($_, $program) } File::Spec->path();
  for my $base(@a) {
    return $base if -x $base;
    for my $ext (@path_ext) {
      return "$base.$ext" if -x "$base.$ext";
    }
  }
  return;
}

__DATA__

my %opts = ();
GetOptions(\%opts,
           'cflags',
           'libs',
           'port',
           'version',
           'libmysqld-libs',
           'embedded',
           'embedded-libs',
           'help',
          ) or usage();

usage() if ($opts{help} or not %opts);

SWITCH : {
  local $\ = "\n";
  $opts{cflags} and do {
    print $cflags;
    last SWITCH;
  };
  $opts{libs} and do {
    print $libs;
    last SWITCH;
  };
  $opts{port} and do {
    print $port;
    last SWITCH;
  };
  $opts{version} and do {
    print $version;
    last SWITCH;
  };
  ($opts{'libmysqld-libs'} or $opts{embedded} or $opts{'libmysqld-libs'} )
    and do {
      print $embedded_libs;
      last SWITCH;
    };
  usage();
}

exit(0);

sub usage {
  print << "EOU";
Usage: $0 [OPTIONS]

Options:
        --cflags         [$cflags]
        --libs           [$libs]
        --port           [$port]
        --version        [$version]
	--libmysqld-libs [$embedded_libs]
EOU
    exit(1);
}

__END__

=head1 NAME

mysql_config_win32.pl - create a mysql_config script for Win32 

=head1 DESCRIPTION

On unix, MySQL comes with a shell script, F<mysql_config>, which
can be used to return information about the MySQL installation.
F<mysql_config_win32.pl> can be used to create a similar utility for Win32.

Installation proceeds as

  perl mysql_config_win32.pl [ --with-mysql=C:\Path\to\MySQL ]

The command-line argument C<--with-mysql> specifies the root 
directory of your MySQL installation. This is optional if your 
PATH environment variable includes your MySQL bin directory.
This will place a F<mysql_config.pl>, and accompanying
F<mysql_config.bat> file, in your MySQL C<bin> directory.

After installation, the utility may be used as

  C:\Path\to\MySQL\bin\mysql_config.bat  [OPTIONS]

with the available options being

  --help           # returns a summary of options
  --cflags         # returns, eg, -I"C:\MySQL\include"
  --libs           # returns, eg, -L"C:\MySQL\lib\opt" -lmysqlclient -lzlib
  --port           # returns, eg, 3306
  --version        # returns, eg, 4.1.10-nt
  --libmysqld-libs # returns, eg, -L"C:\MySQL\lib\opt"

=head1 PREREQUISITES

This script requires C<Getopt::Long>, C<File::Basename>, and
C<File::Spec>, which are standard modules in recent ActivePerl
installations. It also requires a MySQL installation.

=head1 AUTHOR

Copyright (C), 2005, by Randy Kobes E<lt>r.kobes@uwinnipeg.caE<gt>.
This is free software; you can redistribute it and/or modify 
it under the same terms as Perl itself.

=begin CPAN

=head1 README

On unix, MySQL comes with a shell script, F<mysql_config>, which
can be used to return information about the MySQL installation.
This script is used to create a similar utility for Win32.

=pod OSNAMES

MSWin32

=pod SCRIPT CATEGORIES

Win32/Utilities

=end CPAN

=cut

