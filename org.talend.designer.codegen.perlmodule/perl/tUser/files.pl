 # User defined file descriptors
 
 
  %
iA_FD = (
 type           => 'input',
 filename       => '/home/pierrick/dev/bench/data/input/separated.head',
 filehandle     => iA_FH,
 rowseparator   => "\n",
 fieldseparator => ";",
 includeheader  => 0,
 startatline    => 0,
 debug          => 0);

  %
oB_FD = (
 type           => 'output',
 filename       => '/home/pierrick/dev/bench/data/output/talend/test.out',
 filehandle     => oB_FH,
 rowseparator   => "\n",
 fieldseparator => '|',
 includeheader  => 0,
 startatline    => __STARTATLINE__,
 debug          => 0);
 


 %ExecReport = (
                   type            => 'output',
                   filename        => 'report\execreport.html',
                   filehandle      => 'ExecReport',
                   rowseparator    => "\n",
                   fieldseparator  => ';',
                );


 %OutputFile = (
              		iA_FD => \%iA_FD,
oB_FD => \%oB_FD,
 
                  ExecReport         => \%ExecReport,
                  
                  _order        => ['']
                 );   

1;
