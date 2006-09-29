package tUSER::dbs;

use Exporter;

use DBI();

use vars qw(
             @EXPORT
      	     @ISA
              
             %OutputDB
      	   );

@ISA = qw(Exporter);

@EXPORT = qw(
              
             %OutputDB
            );


  


%OutputDB = (
		  
                  #_order  => []
                 );   

1;
