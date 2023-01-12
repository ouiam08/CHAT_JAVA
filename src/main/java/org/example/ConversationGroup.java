package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConversationGroup extends JFrame {
    private JLabel grpName;
    private JLabel creatorName;
    private JPanel convGrp;
    private JButton SENDButton;
    private JTextArea cnv;
    private JTextField text;
    DatagramSocket grpcnvsocket;
    String t = "";

    public ConversationGroup(DatagramSocket socket, String pseudo, String gName) throws Exception {
        setTitle("CONERSATION GROUP");
        setContentPane(convGrp);
        setMinimumSize(new Dimension(600, 429));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        grpcnvsocket = socket;
        MessageReceiver r =  new MessageReceiver(grpcnvsocket,this);
        Thread thread = new Thread(r);
        thread.start();

        grpName.setText(gName);




        InetAddress adress = InetAddress.getLocalHost();
//demander le nom de createur du groupe
        String d2= "creator//"+gName;
        byte[] b2 = d2.getBytes();


        DatagramPacket pa2=new DatagramPacket(b2,b2.length,adress,2010);
        socket.send(pa2);
        byte[] re2= new byte[65024];
        pa2=new DatagramPacket(re2,re2.length);

        socket.receive(pa2);
        String creator = new String(pa2.getData(),0,pa2.getLength());

        if(creator.split("//")[0].trim().equals("creator")){
            creatorName.setText(creator.split("//")[1].trim());
        }


        ////

//traitement pour envoyer le message au groupe
        SENDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t = text.getText();


                if(!t.isEmpty()){
                    cnv.append("Me:  "+t + "\n");
                    text.setText("");

                    //send to server
                    try {


                        String data = "sendMessageToGroup//" + gName + "//" + t + "//" + pseudo;
                        byte[] buffer = data.getBytes();


                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, 2010);
                        grpcnvsocket.send(packet);



                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }}

            }
        });

    }
    public void ajouterMsg(String msg){
//affiche le msg recu dans la conversation
        String pSender =  msg.split("//")[2].trim();
        String contenu = msg.split("//")[1].trim();
        cnv.append(pSender +" : "+contenu+"\n");
    }
}




