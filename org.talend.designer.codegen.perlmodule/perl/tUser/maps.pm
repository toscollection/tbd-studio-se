

%o1_map = (
id   =>  '$i1{id}',
name   =>  '$i1{name}',
addr1   =>  '$i1{addr1}',
state   =>  '$i1{state}',

_order => [ 'id', 'name', 'addr1', 'state'],
_name  =>  'o1_map',
_debug =>  0);


%map1 = ( 
o1_map  => \%o1_map, 
_order => ['o1_map'],
_files => [$OutputFile1{filename}]);

1;