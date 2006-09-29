package talend::misc;

use Exporter;
use Carp;

use vars qw(@EXPORT @ISA);

@ISA = qw(Exporter);

@EXPORT = qw(
    getBooleanFromString
    getAsciiRandomString
    getRandomString
    getHexRandomString
    filemaskToRegex
);

sub getBooleanFromString {
    my ($string) = @_;

    if (lc($string) eq 'true') {
        return 1;
    }
    else {
        return 0;
    }
}

sub getRandomString {
    my ($length, $letters) = @_;

    my $string = '';
    for (1..$length) {
        $string.= $letters->[int rand scalar @$letters];
    }

    return $string;
}

sub getAsciiRandomString {
    my ($length) = @_;

    return getRandomString(
        $length,
        ['a'..'z', 'A'..'Z', 0..9]
    );
}

sub getHexRandomString {
    my ($length) = @_;

    return getRandomString(
        $length,
        ['a'..'f', 0..9]
    );
}

sub filemaskToRegex {
    my ($filemask) = @_;

    my $pattern = $filemask;

    # *.log will be used as ^.*\.log$
    $pattern =~ s{\.}{\\.}g;
    $pattern =~ s{\*}{.*}g;
    $pattern =~ s{\?}{.?}g;
    $pattern = '^'.$pattern.'$';

    return $pattern;
}

1;
