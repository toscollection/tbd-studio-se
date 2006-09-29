package tUSER::filesg;

use Exporter;

use vars qw(
             @EXPORT
      	     @ISA

%tFileInput_1_FD
	     %OutputFile
             %ExecReport
      	   );

@ISA = qw(Exporter);

@EXPORT = qw(
             %ExecReport

%tFileInput_1_FD
	     %OutputFile
            );



%tFileInput_1_FD = (
 type           => 'input',
 filename       => 'c:\talendStudio\input\bcpdemo.csv',
 filehandle     => tFileInput_1_FH,
 rowseparator   => "\n",
 fieldseparator => ";",
 includeheader  => 0,
 startatline    => 0,
 debug          => 0);



 %ExecReport = (
                   type            => 'output',
                   filename        => 'report\execreport.html',
                   filehandle      => 'ExecReport',
                   rowseparator    => "\n",
                   fieldseparator  => ';',
                );


 %OutputFile = (
		                   ExecReport         => \%ExecReport,

                  _order        => ['']
                 );

1;
