package tUSER::dbs;

use strict;

use vars qw( @EXPORT
	     @ISA
	     %OutputDB
	     %OutputDB1
	     
	     %ConnectionDB1
	     );

@ISA = qw(Exporter);

@EXPORT = qw(
              %ISStatVar
              %OutputDB
              %OutputDB1
            );

 
  %ConnectionDB1 = (
                      dbi      => 'DBI:mysql',  
                      database => 'test',
                      host     => 'localhost',
                      username => 'root',
                      password => 'lapins',
                      # handle : $dbh
                    ); 


  %OutputDB1 = (
                   type        => 'output',
                   table       => 'output3',
                   connection  => \%ConnectionDB1,
                   clear_before_insert => 1
                   # handle : $sth
                );


  %OutputDB = (
                  OutputDB1 => \%OutputDB1,
                  _order        => ['']
                 );  


1;