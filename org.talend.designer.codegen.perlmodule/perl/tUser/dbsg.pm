package tUSER::dbs;

use Exporter;

use DBI();

use vars qw(
             @EXPORT
      	     @ISA
             %%DBLIST%%
             %OutputDB
      	   );

@ISA = qw(Exporter);

@EXPORT = qw(
             %%DBLIST%%
             %OutputDB
            );


 %%DBDESCRIPTION%%


%OutputDB = (
		 %%DBLIST2%%
                  #_order  => []
                 );   

1;
