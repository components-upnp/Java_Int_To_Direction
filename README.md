# Java_Int_To_Direction
Composant UPnP qui prend en entrée une valeur de 0 à 100 et la convertit en une Direction (GAUCHE ou DROITE)

<strong>Description : </strong>

Ce composant permet d'adapter un composant qui enverrait une valeur de 0 à 100 en une direction GAUCHE (pour des valeurs
inférieures à 50) et Droite (pour les valeurs supérieures à 50).

Il est utilisé notamment pour adapter le Slider Android au bureau de vote dans le cadre du cas d'utilisation du vote itneractif.

<strong>Lancement de l'applciation :</strong>

Pour lancer l'application, il faut lancer le lanceur.bat ou le lanceur.sh respectivement si elle doit être lancée sur 
Windows ou Linux.

Cette application se lance dans un terminal et n'offre pas d'interface graphique.

<strong>Spécifications UPnP :</strong>

Ce composant offre les services suivants :

  1) IntToConvertService
  2) DirectionService
  
  IntToConvertService offre l'accès à la méthode :
  
    1. SetCommandInt(String NewCommandIntValue) : reçoit une commande XML contenant la valeur entière comprise en 0 et 100
    à convertir.
    
  DirectionService offre l'accès à la méthode :
  
    1. SetCommande(String NewCommandValue) : prend en entrée la commande XML à envoyer contenant la direction obtenue à partir 
    d'une valeur entière reçue au préalable.
    
    DirectionService envoie un évènement UPnP du nom de Commande_Event.
    
  Voici le schéma représentant le composant :
  
  ![alt tag](https://github.com/components-upnp/Java_Int_To_Direction/blob/master/Int_To_Direction_Adapter.png)
  
  
  <strong>Maintenance :</strong>
  
  C'est un projet Maven. Effectuer les modifications à faire, ajouter une configuration d'éxecution Maven avec la phase "package"
  et "install". Des archives zip et tar.gz seront créées contenant respectivement les lanceurs .bat et .sh, ainsi que le .jar.
  
