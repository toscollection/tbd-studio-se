package talend::runstat;

use strict;
use Exporter;
use vars qw(@EXPORT @ISA);

use IO::Socket::INET;

@ISA = qw(Exporter);

@EXPORT = qw(
    StartStat
    SendStat
);

my $__InternalStatSocket;
my $connection_number = 0;

sub StartStat {
    my ($port, $host) = @_;

    $| = 1;

    print '[stat] connecting to socket on port ', $port, " ...\n";

    while (not $__InternalStatSocket) {
        $__InternalStatSocket = IO::Socket::INET->new(
            PeerAddr => $host,
            PeerPort => $port,
            Proto => 'tcp',
        )
            or StatConnectionFailed();
    }

    print '[stat] connected', "\n";
}

sub SendStat {
  if (!$__InternalStatSocket) {
     return;
  }

  my $message =  join('|', @_).chr(13).chr(10);
  $__InternalStatSocket->send($message);
}

sub StatConnectionFailed {
    printf("[stat] connection failed #%u\n", ++$connection_number);
    if ($connection_number > 4) {
        die "[stat] connection failed, too many retries\n";
    }
    sleep 1;
}

1;
