=head1 NAME

Lingua::EN::AddressParse - manipulate geographical addresses

=head1 SYNOPSIS

    use Lingua::EN::AddressParse;

    my %args =
    (
      country     => 'Australia',
      auto_clean  => 1,
      force_case  => 1,
      abbreviate_subcountry => 0,
      abbreviated_subcountry_only => 1
    );

    my $address = new Lingua::EN::AddressParse(%args);
    $error = $address->parse(
       "UNIT 2A 14-16 OLD SOUTH HEAD ST ST JOHNS WOOD NSW 2021 AUSTRALIA : HOLD MAIL");

    %my_address = $address->components;
    print $my_address{sub_property_identifier}; # UNIT 2A
    print $my_address{property_identifier};     # 14-16

    print $my_address{suburb};                  # ST JOHNS WOOD

    %my_formatted_address = $address->case_components;
    print $my_formatted_address{street};        # Old South Head
    print $my_formatted_address{street_type};   # St

    %address_properties = $address->properties;
    print $address_properties{type};            # suburban
    print $address_properties{non_matching};    # : HOLD MAIL

    $correct_casing = $address->case_all;


=head1 DESCRIPTION

This module takes as input a suburban, rural or postal address in free format
text such as,

    2A LOW ST KEW NSW 2123
    12/3-5 AUBREY ST MOUNT VICTORIA VICTORIA 3133
    "OLD REGRET" WENTWORTH FALLS NSW 2782 AUSTRALIA
    GPO Box K318, HAYMARKET, NSW 2000

    12 1st Avenue N Suite # 2 Somewhere CA 12345 USA

and attempts to parse it. If successful, the address is broken
down into it's components and useful functions can be performed such as :

    converting upper or lower case values to title case (2A Low St Kew NSW 2123)
    extracting the addresses individual components      (2A,Low,St,KEW,NSW,2123)
    determining the type of format the address is in    ('suburban')


If the address cannot be parsed you have the option of cleaning the address
of bad characters, or extracting any portion that was parsed and the portion
that failed.

This module can be used for analysing and improving the quality of
lists of postal addresses.

=head1 DEFINITIONS

The following terms are used by AddressParse to define
the components that can make up an address.

    Sub property identifier : Level 1A Unit 2, Apartment B, Lot 12, Suite # 12 ...
    Property Identifier : 12/66A, 24-34, 2A, 23B/12C, 12/42-44

    Property name : "Old Regret"
    Post Box      : GP0 Box K123, LPO 2345, RMS 23 ...
    Road Box      : RMB 24A, RMS 234 ...
    Street name   : O'Hare, New South Head, The Causeway
    Street type   : Road, Rd., St, Lane, Highway, Crescent, Circuit ...
    Suburb        : Dee Why, St. John's Wood ...
    Sub country   : NSW, New South Wales, ACT, NY, AZ ...
    Post code     : 2062, 34532, SG12A 9ET
    Country       : Australia, UK, US or Canada

Refer to the component grammar defined in the AddressGrammar module for a
complete list of combinations.


The following address formats are currently supported.
A ? means the component is optional:

    'suburban' : sub_property_identifier(?) property_identifier(?) street street_type suburb subcountry post_code country(?)
    OR for the USA
    'suburban' : property_identifier(?) street street_type sub_property_identifier(?) suburb subcountry post_code country(?)

    'rural'    : property_name suburb subcountry post_code country(?)
    'post_box' : post_box suburb subcountry post_code country(?)
    'road_box' : road_box street street_type suburb subcountry post_code country(?)
    'road_box' : road_box suburb subcountry post_code country(?)


=head1 METHODS

=head2 new

The C<new> method creates an instance of an address object and sets up
the grammar used to parse addresses. This must be called before any of the
following methods are invoked. Note that the object only needs to be
created once, and can be reused with new input data.

Various setup options may be defined in a hash that is passed as an
optional argument to the C<new> method.

    my %args =
    (
      country     => 'Australia',
      auto_clean  => 1,
      force_case  => 1,
      abbreviate_subcountry => 1,
      abbreviated_subcountry_only => 1
    );

    my $address = new Lingua::EN::AddressParse(%args);

=over 4

=item country

The country argument must be specified. It determines the possible list of
valid sub countries (states, counties etc, defined in the Locale::SubCountry
module) and post code formats. Either the full name or abbreviation may be
specified. The currently suppoted country names and codes are:

    AU or Australia
    CA or Canada
    GB or United Kingdom
    US or United States

All forms of upper/lower case are acceptable in the country's spelling. If a
country name is supplied that the module doesn't recognise, it will die.

=item force_case (optional)

This option only applies to the e C<case_all> method, see below.

=item auto_clean  (optional)

When this option is set to a positive value, any call to the C<parse> method
that fails will attempt to 'clean' the address and then reparse it. See the
C<clean> method in Lingua::EN::Nameparse for details. This is useful for
dirty data with embedded unprintable or non alphabetic characters.

=item abbreviate_subcountry (optional)

When this option is set to a positive value, the sub country is forced to it's
abbreviated form, so "New South Wales" becomes "NSW". If the sub country is
already abbreviated then it's value is not altered.

=item abbreviated_subcountry_only (optional)

When this option is set to a positive value, only the abbreviated form
of sub country is allowed, such as "NSW" and not "New South Wales". This
will make parsing quicker and ensure that addresses comply with postal
standards that normally permit ony abbrviated sub countries .

=back

=head2 parse

    $error = $address->parse("12/3-5 AUBREY ST VERMONT VIC 3133");

The C<parse> method takes a single parameter of a text string containing a
address. It attempts to parse the address and break it down into the components
described below. If the address is parsed successfully, a 0 is returned,
otherwise a 1. 

Note that you can successfully parse all the components of an address and still
have an error returned. This occurs when you have non matching data following
a valid address. To check if the data is unusable, you also need to use the 
C<properties> method to check the address type is 'unknown'

This method is a prerequisite for all the following methods.

=head2 components

    %address = $address->components;
    $surburb = $address{suburb};

The C<components> method returns all the address componets in a hash. The 
following keys are used for each component-

    post_box
    road_box
    sub_property_identifier
    property_identifier
    property_name
    street
    street_type
    street_direction (US only)
    suburb
    subcountry
    post_code
    country
 
If a component has no matching data for a given address, it's values will be
set to the empty string.


=head2 case_components

   %my_address = $address->case_components;
   $cased_suburb = $my_address{suburb};

The C<case_components> method does the same thing as the C<components> method,
but each component is converted to title case, meaning the first letter of each 
component is set to capitals and the remainder to lower case. 

Proper name capitalisations such as MacNay and O'Brien are observed

The following components are not converted to title case:

    post_box
    road_box
    subcountry
    post_code
    country

=head2 case_all

    $correct_casing = $address->case_all;

The C<case_all> method does the same thing as the C<case_components> method except 
the entire address is returned as a title cased text string.

If the force_case option was set in the C<new> method above, address case the 
entire input string, including any unmatched sections after a recognzable address 
that failed parsing. This option is useful when you know you have invalid data,
but you still want to title case what you have.

=head2 properties

The C<properties> method return several properties of the address as a hash.
The  following keys are used for each property -

    type - either suburban ,rural,post_box,road_box,unknown

    non_matching (any non matching portion of the input string)


=head2 report

Create a formatted text report 

    the input string 
    the name and value of each defined component 
    the addrsess type
    if any parsing errors occured
    any non matching component

Returns a string containing a multi line formatted text report

=head1 DEPENDANCIES

L<Lingua::EN::NameParse>, L<Locale::SubCountry>, L<Parse::RecDescent>

=head1 BUGS

=head1 LIMITATIONS

Streets such as 'The Esplanade' will return a street of 'The Esplanade' and a
street type of null string.

For US addresses, an ambiguity arises between a street directional suffix and 
a suburb directional prefix, such as '12 Main St S Springfield CA 92345'. Is it South
Main St, or South Springfield. The parser assumes that 'S' belongs to the street
description.

The huge number of character combinations that can form a valid address makes
it is impossible to correctly identify them all.

Valid addresses must contain a suburb, subcountry (state) and post code,
in that order. This format is widely accepted in Australia and the US. UK
addresses will often include suburb, town, city and county, formats that
are very difficult to parse.

Property names must be enclosed in sinlle or double quotes like "Old Regret"

Because of the large combination of possible addresses defined in the grammar,
the program is not very fast.



=head1 REFERENCES

"The Wordsworth Dictionary of Abbreviations & Acronyms" (1997)

Australian Standard AS4212-1994 "Geographic Information Systems -
Data Dictionary for transfer of street addressing information"

ISO 3166-2:1998, Codes for the representation of names of countries
and their subdivisions. Also released as AS/NZS 2632.2:1999


=head1 SEE ALSO

AddressParse is designed to identify properties, which have a unique physical 
location. L<Geo::StreetAddress::US> will also parse addresses for the USA, and can handle
locations defined by street intersections, such as: "Hollywood & Vine, Los Angeles, CA" 
"Mission Street at Valencia Street, San Francisco, CA"


    L<Lingua::EN::NameParse>
    L<Geo::StreetAddress::US>
    L<Parse::RecDescent>
    L<Locale::SubCountry>

See L<http://www.upu.int/post_code/en/postal_addressing_systems_member_countries.shtml>
for a list of different addressing formats from around the world. And also
L<http://www.bitboost.com/ref/international-address-formats.html>

=head1 TO DO

Define grammar for other languages. Hopefully, all that would be needed is
to specify a new module with its own grammar, and inherit all the existing
methods. I don't have the knowledge of the naming conventions for non-english
languages.

=head1 AUTHOR

AddressParse was written by Kim Ryan <kimryan at cpan d o t org>

=head1 COPYRIGHT AND LICENSE

Copyright (c) 2005 Kim Ryan. All rights reserved.

This library is free software; you can redistribute it and/or modify
it under the same terms as Perl itself, either Perl version 5.8.4 or,
at your option, any later version of Perl 5 you may have available.


=cut

#------------------------------------------------------------------------------

package Lingua::EN::AddressParse;

use strict;
use Lingua::EN::AddressGrammar;
use Lingua::EN::NameParse;
use Parse::RecDescent;

our $VERSION = '1.15';

#------------------------------------------------------------------------------
# Create a new instance of an address parsing object. This step is time
# consuming and should normally only be called once in your program.

sub new
{
    my $class = shift;
    my %args = @_;

    my $address = {};
    bless($address,$class);

    # Add error checking for invalid keys?
    foreach my $curr_key (keys %args)
    {
        $address->{$curr_key} = $args{$curr_key};
    }

    my $grammar = &Lingua::EN::AddressGrammar::_create($address);

    $address->{parse} = new Parse::RecDescent($grammar);

    return ($address);
}
#------------------------------------------------------------------------------
sub parse
{
    my $address = shift;
    my ($input_string) = @_;

    $address->{input_string} = $input_string;

    chomp($address->{input_string});
    # Replace commas (which can be used to chunk sections of addresses) with space
    $address->{input_string} =~ s/,/ /g;

    $address = _assemble($address);
    _validate($address);

    # If errors occurred on initial parse, clean input data (if that option was
    # selected by the user) and try again
    if ( $address->{error} and $address->{auto_clean} )
    {
        $address->{input_string} = _clean($address->{input_string});
        $address = _assemble($address);
        _validate($address);
    }

    return($address,$address->{error});
}
#------------------------------------------------------------------------------
sub components
{
    my $address = shift;
    return(%{ $address->{components} });
}
#------------------------------------------------------------------------------
# Apply correct capitalisation to each component of an address

sub case_components
{
    my $address = shift;

    my %orig_components = $address->components;

    my (%cased_components);
    foreach my $curr_key ( keys %orig_components )
    {
        my $cased_value;

        if ( $curr_key =~ /street|street_type|suburb|property_name|sub_property_identifier/ )
        {
            # Surnames can be used for street's or suburbs so this method
            # will give correct capitalisation for most cases
            $cased_value = &Lingua::EN::NameParse::case_surname($orig_components{$curr_key});
        }
        # retain street_direction,sub country and countries capitalisation, usually uppercase
        else
        {
            $cased_value = uc($orig_components{$curr_key});
        }
        $cased_components{$curr_key} = $cased_value;
    }
    return(%cased_components);
}
#------------------------------------------------------------------------------
# Apply correct capitalisation to an entire address

sub case_all
{
    my $address = shift;

    my @cased_address;

    unless ( $address->{properties}{type} eq 'unknown' )
    {

        # Hash of of lists, indicating the order that address components are assembled in.
        # Each list element is itself the name of the key value in an address object.

        my %component_order=
        (
           'rural'   => [ 'property_name','suburb','subcountry','post_code','country'],
           'post_box'=> [ 'post_box','suburb','subcountry','post_code','country' ],
           'road_box'=> [ 'road_box','street','street_type','suburb','subcountry','post_code','country' ]

        );
        if ( $address->{country} eq 'US' )
        {
           $component_order{'suburban'} =  [ 'property_identifier','street','street_type','street_direction','sub_property_identifier','suburb','subcountry','post_code','country'],
        }
        else
        {
           $component_order{'suburban'} =  [ 'sub_property_identifier','property_identifier','street','street_type','suburb','subcountry','post_code','country'],
        }

        my %component_vals = $address->case_components;
        my @order = @{ $component_order{$address->{properties}{type} } };

        foreach my $component ( @order )
        {
            # As some components such as property name are optional, they will appear
            # in the order array but may or may not have have a value, so check
            # for undefined values
            if ( $component_vals{$component} )
            {
                push(@cased_address,$component_vals{$component});
            }
        }
    }

    if ( $address->{error} and $address->{force_case} )
    {
        # Despite errors, try to name case non-matching section. As the format
        # of this section is unknown, surname case will provide the best
        # approximation
        push(@cased_address,&Lingua::EN::NameParse::case_surname($address->{properties}{non_matching}));
    }

    return(join(' ',@cased_address));
}
#------------------------------------------------------------------------------
sub properties
{
    my $address = shift;
    return(%{ $address->{properties} });
}

#------------------------------------------------------------------------------
# Create a text report to standard output listing 
# - the input string, 
# - the name of each defined component 
# - any non matching component

sub report
{
    my $address = shift;

    my $report;

    $report .= sprintf("%-23.23s : %s\n","Input",$address->{input_string});
    my %comps = $address->case_components;
    foreach my $comp ( sort keys %comps)
    {
        if ($comps{$comp}  )
        {
            $report .= sprintf("%-23.23s : %s\n",$comp,$comps{$comp});
        }
    }
    my %props = $address->properties;
    if ( $props{type} )
    {
        $report .= sprintf("%-23.23s : %s\n","Address type",$props{type});
    }

    if ( $props{non_matching} )
    {
        $report .= sprintf("%-23.23s : %s\n","Parsing Error","Yes");
        $report .= sprintf("%-23.23s : >>>%s<<<\n","Non matching part",$props{non_matching});
    }
    return($report);
}

#------------------------------------------------------------------------------

# PRIVATE METHODS

#------------------------------------------------------------------------------
# Parse the address
# Assemble all the components of the address into an object
# Apply some data conditioning

sub _assemble
{

    my $address = shift;

    # Parse the address according to the rules defined in the AddressGrammar module,
    # using Parse::RecDescent as the parsing engine
    my $parsed_address = $address->{parse}->full_address($address->{input_string});

    # Place components into a separate hash, so they can be easily returned to
    # for the user to inspect and modify
    $address->{components} = ();

    # For correct matching, the grammar of each component must include the
    # trailing space that separates it from any following word. This should
    # now be removed from each component
    
    $address->{components}{post_box} = '';
    if ( $parsed_address->{post_box} )
    {
        $address->{components}{post_box} = _trim_space($parsed_address->{post_box});
    }

    $address->{components}{road_box} = '';
    if ( $parsed_address->{road_box} )
    {
        $address->{components}{road_box} = _trim_space($parsed_address->{road_box});
    }

    $address->{components}{property_name} = '';
    if ( $parsed_address->{property_name} )
    {
        $address->{components}{property_name} = _trim_space($parsed_address->{property_name});
    }

    $address->{components}{sub_property_identifier} = '';
    if ( $parsed_address->{sub_property_identifier} )
    {
        $address->{components}{sub_property_identifier} = _trim_space($parsed_address->{sub_property_identifier});
    }

    $address->{components}{property_identifier} = '';
    if ( $parsed_address->{property_identifier} )
    {
        $address->{components}{property_identifier} = _trim_space($parsed_address->{property_identifier});
    }

    $address->{components}{street} = '';
    if ( $parsed_address->{street} )
    {
        $address->{components}{street} = _trim_space($parsed_address->{street});
    }

    $address->{components}{street_type} = '';
    if ( $parsed_address->{street_type} )
    {
        $address->{components}{street_type} =  _trim_space($parsed_address->{street_type});
    }
    $address->{components}{street_direction} = '';
    if ( $parsed_address->{street_direction} )
    {
        $address->{components}{street_direction} =  _trim_space($parsed_address->{street_direction});
    }

    $address->{components}{suburb} = '';
    if ( $parsed_address->{suburb} )
    {
        $address->{components}{suburb} =  _trim_space($parsed_address->{suburb});
    }

    $address->{components}{subcountry} = '';
    if ( $parsed_address->{subcountry} )
    {
        my $sub_country = _trim_space($parsed_address->{subcountry});

        # Force sub country to abbreviated form, South Australia becomes SA
        if ($address->{abbreviate_subcountry})
        {
            my $country = new Locale::SubCountry($address->{country});
            my $code = $country->code($sub_country);
            if ( $code ne 'unknown' )
            {
                $address->{components}{subcountry} = $code;            
            }
            # sub country already abbreviated
            else 
            {
                $address->{components}{subcountry} = $sub_country; 
            }
        }
        else 
        {
           $address->{components}{subcountry} = $sub_country;   
        }
    }

    $address->{components}{post_code} = '';
    if ( $parsed_address->{post_code} )
    {
        $address->{components}{post_code} = _trim_space($parsed_address->{post_code});
    }

    $address->{components}{country} = '';
    if ( $parsed_address->{country} )
    {
        $address->{components}{country} = _trim_space($parsed_address->{country});
    }

    $address->{properties} = ();

    $address->{properties}{non_matching} = '';
    if ( $parsed_address->{non_matching} )
    {
        $address->{properties}{non_matching} = $parsed_address->{non_matching};
    }
    $address->{properties}{type} = $parsed_address->{type};


    return($address);
}
#------------------------------------------------------------------------------
# Check for several different types of syntax errors

sub _validate
{
    my $address = shift;

    if ( $address->{properties}{non_matching} )
    {
        $address->{error} = 1;
    }
    # illegal characters found, note a # can appear as an abbreviation for number in USA addresses
    elsif ( $address->{input_string} =~ /[^"A-Za-z0-9\-\'\.,&#\/ ]/ )
    {
        $address->{error} = 1;
    }
    # street name must have a vowel sound, or a digit for ordinal street type
    elsif ( $address->{components}{street} !~ /[AEIOUYaeiouy0-9]/ )
    {
        $address->{error} = 1;
    }
    elsif ( $address->{components}{suburb} !~ /[AEIOUYaeiouy]/ )
    {
        $address->{error} = 1;
    }
    else
    {
        $address->{error} = 0;
    }
}
#-------------------------------------------------------------------------------
# Purge  the input string of illegal or redundant characters. Note that quotes 
# can occur as property name delimiters

sub _clean
{
    my ($input_string) = @_;

    # remove illegal characters
    $input_string =~ s/[^A-Za-z0-9&\/#.'" -]//go;

    # remove repeating spaces
    $input_string =~ s/  +/ /go ;

    # remove any remaining leading or trailing space
    $input_string =~ s/^ //;
    $input_string =~ s/ $//;

    return($input_string);
}
#------------------------------------------------------------------------------
# Remove any trailing spaces

sub _trim_space
{
    my ($string) = @_;
    $string =~ s/ $//;
    return($string);
}


#------------------------------------------------------------------------------
return(1);
