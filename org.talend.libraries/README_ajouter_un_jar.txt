Pour ajouter un jar :
 - Aller dans le "properties" > "build path", ajouter le jar au projet
 - Aller dans le "properties" > "Order and export", cocher le jar dans la liste et remontez le éventuellement au-dessus du JRE.
 - Dans "MANIFEST.MF" > "Runtime" Exported Pakage, ajouter les packages pour les exporter
 - Dans "MANIFEST.MF" > "Runtime" Classpath, ajouter le nom du jar
 - Dans "MANIFEST.MF" > "Build" > "Binary build", cocher le jar

Voir http://wiki.improve.fr/wiki/moni/articles/thirdpartylib