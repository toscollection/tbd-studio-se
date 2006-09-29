package tUSER::routines;
# user define

use Exporter;
use talend::stat;

use vars qw( @EXPORT @ISA ) ;    

@ISA = qw(Exporter);
                                  
                             
@EXPORT = qw(
              $max
              rule1
              ereplace
              npad
              myf
              mextract
              
              getdate
            );


$max = 4000;

sub rule1
{                                      
 my ($addr) = (@_);

 my $extr = mextract($addr);

 if ($extr > $max) { return 1 } else { return 0 }
}


sub myf
{
  my ($reg) = (@_);
  $reg =~ m/(\d+)-(\d+)-(\d+)/;         
  
  return "$3:$2:$1";
}

sub mextract
{
 my ($addr) = (@_);

 $addr =~ m/(\d+)/;                   
 return $1;
}


sub npad
{
  my ($num, $len) = (@_);

  return sprintf("%0" . $len . "d", $num);
}


sub ereplace
{
  my ($pstring, $psubstring, $preplacement, $pnumber, $pbegin) = (@_);
  my $mresult;

  $mresult = $pstring;

  $mresult =~ s/$psubstring/$preplacement/g;

  return $mresult;
}



sub getdate  # date du jour en fonction du format
{
  my ($DateFormat) = (@_);

# Formats acceptés :
#  DDMMYYYY
#  DDMMYYYYHHMMSS
#  DD/MM/YYYY HH:MM:SS
#  YYYY-MM-DD HH:MM:SS
#  YYYYMMDD
#  YYYY-MM-DD 00:00:00.000
#  Si pas de format correspondant, la date au format suivant est renvoyée
#   MM/DD/YYYY  

  my ($sec, $min, $heure, $mjour, $mois, $annee) = localtime(time);

  $annee = 1900 + $annee;
  $mois = $mois + 1;
  if ($mois < 10)  { $mois = "0".$mois; }
  if ($mjour < 10) { $mjour = "0".$mjour; }
  if ($heure < 10) { $heure = "0".$heure }
  if ($min < 10)   { $min = "0".$min }
  if ($sec < 10)   { $sec = "0".$sec }

  if ($DateFormat eq "DDMMYYYY")
    { return($mjour.$mois.$annee); }

  elsif ($DateFormat eq "DDMMYYYYHHMMSS")
    { return($mjour.$mois.$annee.$heure.$min.$sec); }

  elsif ($DateFormat eq "DD/MM/YYYY HH:MM:SS")
    { return($mjour."/".$mois."/".$annee." ".$heure.":".$min.":".$sec); }    
    
  elsif ($DateFormat eq "YYYY-MM-DD HH:MM:SS")
    { return($annee."-".$mois."-".$mjour." ".$heure.":".$min.":".$sec); }    
    
  elsif ($DateFormat eq "YYYYMMDD")
    { return($annee.$mois.$mjour); }
    
  elsif ($DateFormat eq "YYYY-MM-DD 00:00:00.000")
    { return($annee."-".$mois."-".$mjour." 00:00:00.000"); }
    
  else
    { return($mois."/".$mjour."/".$annee); }
}


1;
1;
