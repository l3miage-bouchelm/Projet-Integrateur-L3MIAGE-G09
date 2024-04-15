Ce dépot fourni quelques éléments fonctionnels concernant le projet
CyberCommandes. L'intégralité de la spécification fonctionelle contient 
quelques fichiers en complément.

CyberCommandes est disponible sous la forme d'un document Coda spécifique
à chaque groupe (le document doit être complété par chaque groupe).

Seuls quelques compléments sont fournis ici sous forme de fichiers. Il serait 
possible de les adjoindre à Coda, mais il a été préféré de les déposer dans
ce dépot pour bénéficier des fonctionalités de versionnement de git.

Actuellement ce dépot contient :

* un répertoire `CSV/` contenant pour chaque concept un fichier `.csv` généré par Coda.
  Coda étant basé sur un modèle entité association (comme UML) ce fichier n'est pas
  "normalisé" (au sens du modèle relationnel) : les champs de type "Relation" 
  peuvent contenir plusieurs références dans le cas de cardinalités multiples. 
  Dans ce cas les multiples références sont sous forme de chaînes entourées 
  par des guillemets et séparées par des virgules. Par exemple "ref1,ref2,ref3".

* un répertoire 'STARUML' contenant le fichier StarUML correspondant à la version 
  "étudiant", c'est-à-dire la version à completer.

Note : Les données se trouvant dans les fichiers .csv proviennent du document
coda [l3miage-2324-pi-21-no-codecd](https://coda.io/d/l3miage-2324-pi-21-no-code_ddNd7PRiZai/A-PROPOS_suYsR#_luVyc). Ce document peut être utile pour explorer les données.