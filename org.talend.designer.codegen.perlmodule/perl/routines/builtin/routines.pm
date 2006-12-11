# user define

use Exporter;

use vars qw( @EXPORT @ISA ) ;    

@ISA = qw(Exporter);
                                  
                             
@EXPORT = qw(
              $max
              rule1
              ereplace
              npad
              myf
              mextract
              
              getDate
              formatString
              datestringISOtoFr
              datestringISOtoUS
              getRandomDate
            );


$max = 4000;

sub rule1
{                                      
 my ($addr) = (@_);

 my $extr = mextract($addr);

 if ($extr > $max) { return 1 } else { return 0 }
}


sub myf
{
  my ($reg) = (@_);
  $reg =~ m/(\d+)-(\d+)-(\d+)/;         
  
  return "$3:$2:$1";
}

sub mextract
{
 my ($addr) = (@_);

 $addr =~ m/(\d+)/;                   
 return $1;
}


sub npad
{
  my ($num, $len) = (@_);

  return sprintf("%0" . $len . "d", $num);
}


sub ereplace
{
  my ($pstring, $psubstring, $preplacement, $pnumber, $pbegin) = (@_);
  my $mresult;

  $mresult = $pstring;

  $mresult =~ s/$psubstring/$preplacement/g;

  return $mresult;
}

# getDate : return the current datetime with the given display format
#
# format : (optional) string representing the wished format of the
#          date. This string contains fixed strings and variables related
#          to the date. By default, the format string is DD/MM/CCYY. Here
#          is the list of date variables:
#
#    + CC for century
#    + YY for year
#    + MM for month
#    + DD for day
#    + hh for hour
#    + mm for minute
#    + ss for second
sub getDate {
    my ($format) = @_;
    $format = 'DD/MM/CCYY' if not defined $format;

    my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) =
        localtime(time);

    my %fields = (
        CC => int(($year + 1900) / 100),
        YY => $year % 100,
        MM => $mon + 1,
        DD => $mday,
        hh => $hour,
        mm => $min,
        ss => $sec
    );

    %fields = map {$_ => sprintf('%02u', $fields{$_})} keys %fields;

    foreach my $field (keys %fields) {
        $format =~ s/$field/$fields{$field}/g;
    }

    return $format;
}

# formatString return the input string formatted as requested. This function
# takes a hash as input. We have 4 different cases:
#
# 1. "asis => 1", the string is returned "as is"
#
# 2. the string is shorter than the requested size, "align" parameter can be
# set to 'R', 'L' or 'C' (center).
#
# 3. the string is longer than the requested size, "keep" parameter can be
# 'All', 'Right', 'Left' or 'Middle', depending on you want to keep all
# letters, only right letters, only left letters or only middle letters.
#
# 4. the length of the string is the requested size
#
# You can change the padding char with the "padding_char" parameter.
#
# Examples:
# formatString(string => 'foobar', size => 10, align => 'L');
# formatString(string => 'foobar', size => 3, keep => 'M');
# formatString(string => 'foobar', size => 20, padding_char => '0');
sub formatString {
    my %params = @_;

    my %default = (
        keep         => 'A',
        aligne       => 'L',
        padding_char => ' ',
    );

    foreach my $key (keys %default) {
        $params{$key}  = $default{$key} if not defined $params{$key};
    }

    for (qw/keep align/) {
        $params{$_} = uc substr($params{$_}, 0, 1);
    }

    my $len = length $params{string};

    if (defined $params{asis} and $params{asis}) {
        return $params{string};
    }

    if ($len < $params{size}) {
        if ($params{align} eq 'R') {
            return
                ($params{padding_char} x ($params{size} - $len))
                .$params{string}
            ;
        }

        if ($params{align} eq 'L' ) {
            return
                $params{string}
                .($params{padding_char} x ($params{size} - $len))
            ;
        }

        if ($params{align} eq 'C' ) {
            my $space = int(($params{size} - $len) / 2);

            return
                ($params{padding_char} x $space)
                .$params{string}
                .($params{padding_char} x ($params{size} - ($space + $len)))
            ;
        }
    }

    if ($len > $params{size}) {
        if ($params{keep} eq 'A') {
            return $params{string};
        }

        if ($params{keep} eq 'R') {
            return substr(
                $params{string},
                $len - $params{size},
                $params{size}
            );
        }

        if ($params{keep} eq 'L') {
            return substr($params{string}, 0, $params{size});
        }

        if ($params{keep} eq 'M') {
            my $start = int(($len - $params{size}) / 2);
            return substr($params{string}, $start, $params{size});
        }
    }

    return $params{string};
}

use Time::Local;

sub getRandomDate {
    my %params = @_;

    my ($year, $month, $day);

    my $regex = '(\d{4})-(\d{2})-(\d{2})';

    ($year, $month, $day) = ($params{min} =~ m{$regex});
    my $min = timelocal(0, 0, 0, $day, $month-1, $year);

    ($year, $month, $day) = ($params{max} =~ m{$regex});
    my $max = timelocal(0, 0, 0, $day, $month-1, $year);

    my $diff = $max - $min;

    {
        my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) =
            localtime($min + int rand $diff);

        return sprintf(
            '%4u-%02u-%02u',
            $year + 1900,
            $mon + 1,
            $mday,
        );
    }
}

sub datestringISOtoFr {
    my ($datestring) = @_;

    my ($year, $month, $day) = ($datestring =~ m/(\d{4})-(\d{2})-(\d{2})/);

    return $day.'/'.$month.'/'.$year;
}

sub datestringISOtoUS {
    my ($datestring) = @_;

    my ($year, $month, $day) = ($datestring =~ m/(\d{4})-(\d{2})-(\d{2})/);

    return $month.'/'.$day.'/'.$year;
}

1;
