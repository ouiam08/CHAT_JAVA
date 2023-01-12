package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


class MessageReceiver implements Runnable {

    //cette classe permet de recevoir les messages envoyer par le serveur
    DatagramSocket socket;
    byte buffer[];

    Conversation conversation;//pour afficher le resultat dans la conversation entre deux personne
    ConversationGroup conversationGroup;//pour afficher le resultat dans la conversation entre un groupe de personne

    MessageReceiver(DatagramSocket sock, Conversation c) {
        //constructeur dans le cas de conversation entre deux personnes
        socket = sock;
        buffer = new byte[1024];
        conversation = c;
    }

    MessageReceiver(DatagramSocket sock, ConversationGroup cG) {
        //constructeur dans le cas de conversation entre un groupe de personne
        socket = sock;
        buffer = new byte[1024];
        conversationGroup = cG;
    }

    public void run() {
        while (true) {
            try {
                //cet partie de code permet de recevoir le message

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println(received);


                //si le msg recu commence par newMess c'est un msg entre deux personnes
                if(received.split("//")[0].trim().equals("newMess")) {
                    //afficher message dans la conversation a l'aide de la methode definie dans la classe conversation AfficherMsg
                    conversation.AfficherMsg(received);
                }                 //si le msg recu commence par newGRPMess c'est un msg entre deux personnes

                else if (received.split("//")[0].trim().equals("newGRPMess")) {
                    //afficher message dans la conversation du groupe a l'aide de la methode definie dans la classe conversationGroup ajouterMsg
                    conversationGroup.ajouterMsg(received);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}