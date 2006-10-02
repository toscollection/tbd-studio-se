#!/usr/bin/perl

use strict;
use warnings;

use Getopt::Long;

my %opt = ();
GetOptions(\%opt, 'module=s@');

my @module_names = @{ $opt{module} };

foreach my $module_name (sort @module_names) {
    print $module_name, ' => ';
    if (eval "require $module_name") {
        print 'OK';
    }
    else {
        print 'KO';
    }
    print "\n";
}
print "\n";
