use Exporter;

use vars qw(@EXPORT @ISA);

@ISA = qw(Exporter);
@EXPORT = qw(
    sequence
);

##
# return an incremented numeric id
#
# {talendTypes} int
# {param} string('s1') sequence identifier
# {param} int(1) start value
# {param} int(1) step
#
# {example} sequence('s1', 1, 1) # 1, 2, 3, ...
# {example} sequence('s2', 100, -2) # 100, 98, 96, ...
sub sequence {
    my ($id, $start, $step) = @_;

    our %sequence;

    if (exists $sequence{$id}) {
        $sequence{$id}+= $step;
    }
    else {
        $sequence{$id} = $start;
    }

    return $sequence{$id};
}

1;
