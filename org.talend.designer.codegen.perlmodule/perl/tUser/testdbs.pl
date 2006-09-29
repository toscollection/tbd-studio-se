
%tDBInput_1_DD = (
 type     => 'input',
 dbi      => 'DBI:mysql',
 database => $Database,
 host     => 'localhost',
 username => 'root',
 password => 'lapins');

my %OutputDB = (
		 tDBInput_1_DD => \%tDBInput_2_DD,
                  #_order  => []
                 );

1;
