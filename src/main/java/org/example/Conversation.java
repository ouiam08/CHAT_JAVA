package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.FileInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class Conversation extends JFrame {
    private JLabel Fname;
    private JButton SENDButton;
    private JTextArea text;
    private JPanel conv ;
    private JTextArea cnv;
    DatagramSocket covSocket;







    byte buffer[] = new byte[65024];
String t;


    public String getT() {
        return t;
    }

    public Conversation(DatagramSocket socket, String pseudo, String pseudoFriend) throws Exception {


        setTitle("CONVERSATION");
        setContentPane(conv);
        setMinimumSize(new Dimension(500, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        covSocket = socket;
        MessageReceiver r =  new MessageReceiver(covSocket,this);
        Thread thread = new Thread(r);
        thread.start();




        Fname.setText(pseudoFriend);
        InetAddress adress = InetAddress.getLocalHost();




///traitement pour envoyer le message
        SENDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 t = text.getText();


                 if(!t.isEmpty()){
                 cnv.append("Me:  "+t + "\n");
                 text.setText("");

                //send to server
                try {


                    String data = "sendMessageTo//" + pseudoFriend + "//" + t + "//" + pseudo;
                    byte[] buffer = data.getBytes();


                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, 2010);
                    covSocket.send(packet);



                } catch (Exception e1) {
                    e1.printStackTrace();
                }}

    }
});


    }
    public void AfficherMsg(String msg){

//afficher le msg dans la conversation
            String pSender =  msg.split("//")[2].trim();
            String contenu = msg.split("//")[1].trim();
            cnv.append(pSender +" : "+contenu+"\n");

}


}








