
//# formatString return the input string formatted as requested. This function
//# takes a hash as input. We have 4 different cases:
//#
//# 1. "asis => 1", the string is returned "as is"
//#
//# 2. the string is shorter than the requested size, "align" parameter can be
//# set to 'R', 'L' or 'C' (center).
//#
//# 3. the string is longer than the requested size, "keep" parameter can be
//# 'All', 'Right', 'Left' or 'Middle', depending on you want to keep all
//# letters, only right letters, only left letters or only middle letters.
//#
//# 4. the length of the string is the requested size
//#
//# You can change the padding char with the "padding_char" parameter.
//#
//# Examples:
//# formatString(string => 'foobar', size => 10, align => 'L');
//# formatString(string => 'foobar', size => 3, keep => 'M');
//# formatString(string => 'foobar', size => 20, padding_char => '0');


//sub getRandomString {
//    my ($length, $letters) = @_;
//
//    my $string = '';
//    for (1..$length) {
//        $string.= $letters->[int rand scalar @$letters];
//    }
//
//    return $string;
//}

//sub getAsciiRandomString {
//    my ($length) = @_;
//
//    return getRandomString(
//        $length,
//        ['a'..'z', 'A'..'Z', 0..9]
//    );
//}

//sub getHexRandomString {
//    my ($length) = @_;
//
//    return getRandomString(
//        $length,
//        ['a'..'f', 0..9]
//    );
//}

//##
//# return a random english firstname
//#
//# {talendTypes} String
//# {param} list(undef, 'uppercase', 'lowercase', 'uppercase first') transformation : processing
//#
//# {example} firstName(undef) # Hugh, Andrew, John
//# {example} firstName('uppercase') # MIKE, ANDREW, HUGH
