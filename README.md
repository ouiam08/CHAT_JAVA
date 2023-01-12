# CHAT_JAVA
CHAT USING multithread UDP



<h3>Il s'agit d'une application de chat qui implemente les fonctionnalités suivantes:</h3></br>
-s'identifier et s'authentifier </br>
-lancer plusieur utilisateur au meme temps</br>
-envoyer un message dans une conversation entre deux amis connectés</br>
-envoyer un message dans une conversation de groupe (diffusion de message entre plusieurs amis connecter)</br>
-créer un nouveau groupe</br>
-ajouter un nouveau ami</br>
-supprimer un amis</br>
-voir la listes des amis connectés; la liste de tout les amis;la liste des groupes</br>


<h3>Comment cela fonctionne?</h3></br>
<h4>Pour lancer l'application, il faut:</h4></br>
-lancer le serveur, executer la class Server</br>
-lancer un ou plusieurs user, executer la class User</br></br>

Une foix lancer,il faut soit s'identifier ou s'authentifier une foix c'est fait,la page Home est affiché vous pouver voir la liste de vos amis , la liste de vos groupes et la liste des amis connectés il faut actualiser la page de temps en temps pour voir si un nouvel ami est connecter.</br>

Pour lancer une conversation choisissez un ami connecter de la liste, une foix vous cliquer la conversation vas se lancer et vous pouver lui envoyer un message, de meme pour envoyer un message à un groupe d'amis il faut choisir le groupe de la liste des groupes une foix vous cliquer la conversation du groupe va se lancer.Si vous recever un message, il vas s'afficher dans la conversation.Et il faut que la conversation soit ouverte pour recevoir le message.</br>

Pour ajouter un ami il faut inserer le pseudo dans le champs convenable et cliquer sur ADD friend puis actualiser la page pour voir que l'ami est ajouter,de meme pour ajout de groupe,une foix vous inserer un nom de groupe un JFrame sera afficher et vous pouvez choisir les amis à ajouterau groupe, puis cliquer sur create pour créer le groupe, cliquer sur actualiser pour que le groupe créer s'affiche dans la liste des groupes.</br>



<h3>Un petit beug :</h3> si en passe vers une nouvelle interface la page Home est bloquer, il faut relancer application à nouveau.</br>

--le fichier qui contient la Base de donnée est dans le dossier BD/chat_app.sql</br>

