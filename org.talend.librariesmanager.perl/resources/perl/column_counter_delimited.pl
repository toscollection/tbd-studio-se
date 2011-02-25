#!/usr/bin/perl

use strict;
use warnings;
use Carp;
use List::Util qw/min/;

use Getopt::Long;
my %opt = ();
GetOptions(
    \%opt,
    (
        'conf=s',
    )
);

our %conf;

if (-e $opt{conf}) {
    require $opt{conf};
}

$conf{field_separator} ||= ';';
$conf{row_separator} ||= "\n";
$conf{limit} ||= 50;
$conf{header} ||= 0;
$conf{footer} ||= 0;
$conf{type} ||= 'delimited';
$conf{escape_char} ||= '"';
$conf{text_enclosure} = '"';

if ($conf{type} eq 'CSV') {
    use Text::CSV_XS;
}

my $total_line = tFileRowCount(
    filename => $conf{filename},
    rowseparator => $conf{row_separator},
    buffersize => 4096,
);

my $limit = $conf{limit};
if ($limit > 50) {
    $limit = 50;
}

my ($first_row, $last_row) = getFirstAndLastRowNumber(
    header  => $conf{header},
    footer  => $conf{footer},
    limit   => $limit,
    total   => $total_line,
);

# printf("from %u to %u\n", $first_row, $last_row);
my $csv;
if ($conf{type} eq 'CSV') {
    $csv = Text::CSV_XS->new({
        sep_char     => $conf{field_separator},
        escape_char  => $conf{escape_char},
        quote_char   => $conf{text_enclosure},
        binary       => 1,
    });
}

$/ = $conf{row_separator};

open(my $input_FH, '<', $conf{filename})
    or die 'cannot open file "' . $conf{filename} . '"';

my $line_number = 0;
my $max_nb_columns = 0;

while (<$input_FH>) {
    $line_number++;
    if ($line_number < $first_row) {
        next;
    }
    if ($line_number > $last_row) {
        last;
    }

    chomp;

    my $nb_columns;

    if ($conf{type} eq 'CSV') {
        if ($csv->parse($_)) {
            $nb_columns = scalar $csv->fields();
        }
        else {
            $nb_columns = 0;
        }
    }
    else {
        my @fields = split $conf{field_separator}, $_;
        $nb_columns = scalar @fields;
    }

    if ($nb_columns > $max_nb_columns) {
        $max_nb_columns = $nb_columns;
    }
}

close($input_FH);

open(my $output_fh, '>'.$conf{result_file})
    or die 'cannot open result file';
print {$output_fh} $max_nb_columns;
close($output_fh);

#
# subs
#
sub getFirstAndLastRowNumber {
    # header, footer, limit, total
    my %params = @_;

    my ($first, $last) = ();

    $first = 1 + $params{header};

    if (defined $params{limit}) {
        $last =
            $params{header}
            + min(
                $params{limit},
                $params{total} - $params{header} - $params{footer}
            );
    }
    else {
        $last = $params{total} - $params{footer};
    }

    return ($first, $last);
}

sub tFileRowCount {
    my (%param) = @_;

    open(FH, $param{filename})
        or croak 'Cannot open ', $param{filename}, "\n";

    local $/ = $param{rowseparator};
    1 while <FH>;
    my $RowCount = $.;
    close FH;

    return $RowCount;
}
