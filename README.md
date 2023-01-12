# CHAT_JAVA
CHAT USING multithread UDP



Il s'agit d'une application de chat qui implemente les fonctionnalités suivantes:
-s'identifier et s'authentifier 
-lancer plusieur utilisateur au meme temps
-envoyer un message dans une conversation entre deux amis connectés
-envoyer un message dans une conversation de groupe (diffusion de message entre plusieurs amis connecter)
-créer un nouveau groupe
-ajouter un nouveau ami
-supprimer un amis
-voir la listes des amis connectés; la liste de tout les amis;la liste des groupes


Comment cela fonctionne?
pour lancer l'application, il faut:
-lancer le serveur, executer la class Server
-lancer un ou plusieurs user, executer la class User

Une foix lancer,il faut soit s'identifier ou s'authentifier une foix c'est fait,la page Home est affiché vous pouver voir la liste de vos amis , la liste de vos groupes et la liste des amis connectés il faut actualiser la page de temps en temps pour voir si un nouvel ami est connecter.

Pour lancer une conversation choisissez un ami connecter de la liste, une foix vous cliquer la conversation vas se lancer et vous pouver lui envoyer un message, de meme pour envoyer un message à un groupe d'amis il faut choisir le groupe de la liste des groupes une foix vous cliquer la conversation du groupe va se lancer.Si vous recever un message, il vas s'afficher dans la conversation.Et il faut que la conversation soit ouverte pour recevoir le message.

Pour ajouter un ami il faut inserer le pseudo dans le champs convenable et cliquer sur ADD friend puis actualiser la page pour voir que l'ami est ajouter,de meme pour ajout de groupe,une foix vous inserer un nom de groupe un JFrame sera afficher et vous pouvez choisir les amis à ajouterau groupe, puis cliquer sur create pour créer le groupe, cliquer sur actualiser pour que le groupe créer s'affiche dans la liste des groupes.



Un petit beug : si en passe vers une nouvelle interface la page Home est bloquer, il faut relancer application à nouveau.

--le fichier qui contient la Base de donnée est dans le dossier BD/chat_app.sql

