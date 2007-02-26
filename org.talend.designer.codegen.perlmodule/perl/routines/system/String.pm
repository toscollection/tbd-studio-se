use Exporter;

use vars qw(@EXPORT @ISA);
@ISA = qw(Exporter);
@EXPORT = qw(
    formatString
    getAsciiRandomString
    getRandomString
    getHexRandomString
    firstName
);

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

##
# return a random english firstname
#
# {talendTypes} String
# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
#
# {example} firstName(undef) # Hugh, Andrew, John
# {example} firstName('uppercase') # MIKE, ANDREW, HUGH
sub firstName {
    my ($transformation) = @_;

    my @firstnames = qw/
        Abraham
        Andrew
        Benjamin
        Bill
        Calvin
        Chester
        Dwight
        Franklin
        George
        Gerald
        Grover
        Harry
        Herbert
        James
        Jimmy
        John
        Lyndon
        Martin
        Millard
        Richard
        Ronald
        Rutherford
        Theodore
        Thomas
        Ulysses
        Warren
        William
        Woodrow
        Zachary
    /;


    return getRandomStringFromList(\@firstnames, $transformation) ;
}

##
# return a random english lastname
#
# {talendTypes} String
# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
#
# {example} lastName(undef) # Ford, Truman, Roosevelt
# {example} lastName('uppercase') # FORD, TRUMAN, ROOSEVELT
sub lastName {
    my ($transformation) = @_;

    my @lastnames = qw/
        Lincoln
        Jackson
        Johnson
        Harrison
        Clinton
        Coolidge
        Arthur
        Eisenhower
        Roosevelt
        Pierce
        Washington
        Ford
        Cleveland
        Truman
        Hoover
        Garfield
        Buchanan
        Polk
        Madison
        Monroe
        Carter
        Adams
        Kennedy
        Quincy
        Adams
        Tyler
        Johnson
        Van Buren
        Fillmore
        Nixon
        Reagan
        Hayes
        Roosevelt
        Jefferson
        Grant
        Harding
        Harrison
        Taft
        McKinley
        Wilson
        Taylor
    /;


    return getRandomStringFromList(\@lastnames, $transformation) ;
}

##
# return a random street name
#
# {talendTypes} String
# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
#
# {example} street(undef) # Bailard Avenue, Monroe Street, Fairview Avenue
# {example} street('uppercase') # BAILARD AVENUE, MONROE STREET, FAIRVIEW AVENUE
sub street {
    my ($transformation) = @_;

    my @streets = (
        'Apalachee Parkway',
        'Bailard Avenue',
        'Bayshore Freeway',
        'Bowles Avenue',
        'Burnett Road',
        'Cabrillo Highway',
        'Calle Real',
        'Camelback Rd',
        'Carpinteria Avenue',
        'Carpinteria North',
        'Carpinteria South',
        'Castillo Drive',
        'Cerrillos Road',
        'Cleveland Ave.',
        'Corona Del Mar',
        'E Fowler Avenue',
        'East 1st Street',
        'East Calle Primera',
        'East Fry Blvd.',
        'East Main Street',
        'El Camino Real',
        'Erringer Road',
        'Fairview Avenue',
        'Fontaine Road',
        'French Camp Turnpike Road',
        'Grandview Drive',
        'Greenwood Road',
        'Harbor Dr',
        'Harry S Truman Blvd',
        'Hutchinson Rd',
        'Jean de la Fontaine',
        'Jones Road',
        'Katella Avenue',
        'Lake Tahoe Blvd.',
        'Lawrenceville Suwanee',
        'Lindbergh Blvd',
        'Milpas Street',
        'Monroe Street',
        'Moreno Drive',
        'N Harrison St',
        'N Kentwood',
        'Newbury Road',
        'North Atherton Street',
        'North Broadway Street',
        'North Erringer Road',
        'North Preisker Lane',
        'North Ventu Park Road',
        'Pacific Hwy S',
        'Padre Boulevard',
        'Redwood Highway',
        'Richmond Hill',
        'S Rustle St',
        'San Diego Freeway',
        'San Luis Obispo North',
        'San Marcos',
        'San Simeon',
        'San Ysidro Blvd',
        'Santa Ana Freeway',
        'Santa Monica Road',
        'Santa Rosa North',
        'Santa Rosa South',
        'South Highway',
        'South Roosevelt Drive',
        'Steele Lane',
        'Tanger Blvd',
        'Timberlane Drive',
        'Tully Road East',
        'Via Real',
        'W. Russell St.',
        'Westside Freeway',
        'Woodson Rd.',
    );


    return getRandomStringFromList(\@streets, $transformation) ;
}

##
# return a random us city name
#
# {talendTypes} String
# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
#
# {example} city(undef) # Frankfort, Raleigh, Sacramento
# {example} city('uppercase') # FRANKFORT, RALEIGH, SACRAMENTO
sub city {
    my ($transformation) = @_;

    my @cities = (
        'Montgomery',
        'Juneau',
        'Phoenix',
        'Little Rock',
        'Sacramento',
        'Raleigh',
        'Columbia',
        'Denver',
        'Hartford',
        'Bismarck',
        'Pierre',
        'Dover',
        'Tallahassee',
        'Atlanta',
        'Honolulu',
        'Boise',
        'Springfield',
        'Indianapolis',
        'Des Moines',
        'Topeka',
        'Frankfort',
        'Baton Rouge',
        'Augusta',
        'Annapolis',
        'Boston',
        'Lansing',
        'Saint Paul',
        'Jackson',
        'Jefferson City',
        'Helena',
        'Lincoln',
        'Carson City',
        'Concord',
        'Trenton',
        'Santa Fe',
        'Albany',
        'Columbus',
        'Oklahoma City',
        'Salem',
        'Harrisburg',
        'Providence',
        'Nashville',
        'Austin',
        'Salt Lake City',
        'Montpelier',
        'Richmond',
        'Charleston',
        'Olympia',
        'Madison',
        'Cheyenne',
    );


    return getRandomStringFromList(\@cities, $transformation) ;
}

##
# return a random us state name
#
# {talendTypes} String
# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
#
# {example} state(undef) # Alabama, Illinois, Ohio
# {example} state('uppercase') # ALABAMA, ILLINOIS, OHIO
sub state {
    my ($transformation) = @_;

    my @states = (
        'Alabama',
        'Alaska',
        'Arizona',
        'Arkansas',
        'California',
        'North Carolina',
        'South Carolina',
        'Colorado',
        'Connecticut',
        'North Dakota',
        'South Dakota',
        'Delaware',
        'Florida',
        'Georgia',
        'Hawaii',
        'Idaho',
        'Illinois',
        'Indiana',
        'Iowa',
        'Kansas',
        'Kentucky',
        'Louisiana',
        'Maine',
        'Maryland',
        'Massachusetts',
        'Michigan',
        'Minnesota',
        'Mississippi',
        'Missouri',
        'Montana',
        'Nebraska',
        'Nevada',
        'New Hampshire',
        'New Jersey',
        'New Mexico',
        'New York',
        'Ohio',
        'Oklahoma',
        'Oregon',
        'Pennsylvania',
        'Rhode Island',
        'Tennessee',
        'Texas',
        'Utah',
        'Vermont',
        'Virginia',
        'West Virginia',
        'Washington',
        'Wisconsin',
        'Wyoming', 
    );


    return getRandomStringFromList(\@states, $transformation) ;
}

sub stateAbbreviation {

    my @stateAbbreviations = qw/
        AL
        AK
        AZ
        AR
        CA
        NC
        SC
        CO
        CT
        ND
        SD
        DE
        FL
        GA
        HI
        ID
        IL
        IN
        IA
        KS
        KY
        LA
        ME
        MD
        MA
        MI
        MN
        MS
        MO
        MT
        NE
        NV
        NH
        NJ
        NM
        NY
        OH
        OK
        OR
        PA
        RI
        TN
        TX
        UT
        VT
        VA
        WV
        WA
        WI
        WY
    /;


    return getRandomStringFromList(\@stateAbbreviations, undef) ;
}

sub getRandomStringFromList {
	my ( $ref_strings, $transformation ) = @_ ;

	my $string = $ref_strings->[ int rand scalar @$ref_strings ] ;

    if (defined $transformation) {
        if ($transformation eq 'uppercase') {
            $string = uc $string;
        }
        elsif ($transformation eq 'lowercase') {
            $string = lc $string;
        }
        elsif ($transformation eq 'uppercase first') {
            $string = ucfirst lc $string;
        }
    }

    return $string;
}

1;
