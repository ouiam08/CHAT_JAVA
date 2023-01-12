package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class Home extends JFrame{
    private JPanel home;
    private JLabel lbUser;
    private JButton logoutButton;
    private JList UL;
    private JList lConnected;
    private JButton actualiserButton;
    private JPanel conPanel;
    private JTextArea testmsg;
    private JButton SENDButton;
    private JLabel friendNmeLabel;
    private JTable table;
    private JTextField newFriend;
    private JButton ADDButton;
    private JLabel resultField;
    private JLabel DELETE;
    private JLabel delInfo;

    private JTextField tfgrp;
    private JLabel resgrp;
    private JButton ADDGroupButton;
    private JList grplist;
    private JTextArea msgesArea;
    DatagramSocket homesocket;



    public Home(DatagramSocket socket) throws Exception{
        setTitle("Home");
        setContentPane(home);
        setMinimumSize(new Dimension(500,429));
        setSize(1200,700);
        setDefaultCloseOperation(Home.DISPOSE_ON_CLOSE);
        setVisible(true);
        homesocket = socket;
        InetAddress adress = InetAddress.getLocalHost();
        DefaultListModel<String> l3 = new DefaultListModel<>();

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.addColumn("Col1");





//pour recevoir le pseudo et afficher
        String data= "home";


                    byte[] buffer= new byte[65024];
                    buffer=data.getBytes();


                    DatagramPacket packet=new DatagramPacket(buffer,buffer.length,adress,2010);
                    socket.send(packet);
                    byte[] res= new byte[65024];
                    packet=new DatagramPacket(res,res.length);

                    socket.receive(packet);


                    String recieved = new String(packet.getData(),0,packet.getLength());
                    lbUser.setText("hello "+recieved);


                    ///////////////////////////////


//avoir la liste des amis
        String data1= "friends//"+recieved;

                    byte[] buffer1 = data1.getBytes();


                    DatagramPacket packet1=new DatagramPacket(buffer1,buffer1.length,adress,2010);
                    socket.send(packet1);
                    byte[] result= new byte[65024];
                    packet1=new DatagramPacket(result,result.length);

                    socket.receive(packet1);


                    String friends = new String(packet1.getData(),0,packet1.getLength());
//                    System.out.println("friends "+friends);

/////////////////////////////////////////




//avoir liste des amis conneter




                    String data2= "ConnectedFriends//"+recieved;
                    byte[] buffer2 = data2.getBytes();


                    DatagramPacket packet2=new DatagramPacket(buffer2,buffer2.length,adress,2010);
                    socket.send(packet2);
                    byte[] result2= new byte[65024];
                    packet2=new DatagramPacket(result2,result2.length);

                    socket.receive(packet2);
                    String connectedFriends = new String(packet2.getData(),0,packet2.getLength());
//                    System.out.println("ConnectedFriends "+connectedFriends);

////////////



        //afficher les amis connecter dans une  liste
                        DefaultListModel<String> l2 = new DefaultListModel<>();
                        l2.clear();
                        for(int i=0;i<connectedFriends.split("//").length;i++){
                            if(!connectedFriends.split("//")[i].trim().isEmpty()){
                                l2.addElement(connectedFriends.split("//")[i].trim());}
                            else {
                                l2.addElement("NO ONE");
                            }
                        }
                        lConnected.setModel(l2);





//afficher la listes des amis dans une liste
        DefaultListModel<String> l1 = new DefaultListModel<>();
                    if(friends.split("//")[0].equals("friends")){

                    l1.clear();
                    model.setRowCount(0);
                                        for(int i=1;i<friends.split("//").length;i++){
                                            l1.addElement(friends.split("//")[i].trim());
                                            model.addRow(new Object[]{friends.split("//")[i].trim()});
                    }

                    UL.setModel(l1);}

////////////////////////



///pour se deconecter en sortant
                    addWindowListener(new WindowAdapter()
                    {
                        @Override
                        public void windowClosing(WindowEvent e)
                        {


                            try{
                                String msg = "logout//"+ recieved;
                                dispose();
                                byte[] buffer= new byte[65024];
                                buffer=msg.getBytes();
                                DatagramPacket packet=new DatagramPacket(buffer,buffer.length,adress,2010);
                                socket.send(packet);}
                            catch (Exception e1){
                                e1.printStackTrace();
                            }

                        }
                    });


//se deconnecter en cliquant su le boutton logout

                    logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try{
                String msg = "logout//"+ recieved;
                byte[] buffer= new byte[65024];
                buffer=msg.getBytes();
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length,adress,2010);
                socket.send(packet);}
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
//////////


        //traitement pour actualiser la page
                    actualiserButton.addActionListener(new ActionListener() {




                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tfgrp.setText("");
                            lConnected.clearSelection();
                            resgrp.setText("");
                            resultField.setText("");
                            delInfo.setText("");
                            try {
                                String data = "Actualiser//" + recieved;
                                byte[] buffer = data.getBytes();


                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, 2010);
                                socket.send(packet);
                                byte[] result = new byte[65024];
                                packet = new DatagramPacket(result, result.length);

                                socket.receive(packet);
                                String res = new String(packet.getData(), 0, packet.getLength());
//                                System.out.println("res " + res);

                                    if(res.split("-").length>1){
                                if (!(res.split("-")[1].trim().equals(""))) {
                                    String connFriends = res.split("-")[1].trim();
//                                System.out.println("connFriends"+connFriends);



                                    //reaficher la liste des amis connecter
                                    l2.clear();
                                    for (int i = 0; i < connFriends.split("//").length; i++) {
                                        if (!(connFriends.split("//")[i].trim()).isEmpty()) {
                                            l2.addElement(connFriends.split("//")[i].trim());
//                                            System.out.println("element connected friend"+connFriends.split("//")[i]);

                                        } else {
                                            l2.addElement("NO ONE");
                                        }
                                    }
                                } else {
                                    l2.clear();
                                    l2.addElement("NO ONE");
                                }
                                lConnected.setModel(l2);}

                                if (!res.split("-")[0].trim().isEmpty()) {
                                    String friends = res.split("-")[0].trim();

//                                            System.out.println("friends" + friends);



                                    if(friends.split("//")[0].equals("friends")){

                                        //reafficher la liste des amis


                                        l1.clear();
                                        model.setRowCount(0);
                                    for (int i = 1; i < friends.split("//").length; i++) {
                                        l1.addElement(friends.split("//")[i].trim());
//                                                System.out.println("element friend" + friends.split("//")[i]);
                                        model.addRow(new Object[]{friends.split("//")[i].trim()});
                                    }

                                    UL.setModel(l1);}


                                } else {
                                    l1.clear();
                                    l1.addElement("NO Friends");
                                    lConnected.setModel(l1);
                                }
                            lConnected.clearSelection();





                                //reafficher la liste de groupe

                                String grps = res.split("-")[2].trim();
                                if(grps.split("//")[0].equals("grps")){
                                    l3.clear();
                                    for(int i=1;i<grps.split("//").length;i++){
                                        if(!grps.split("//")[i].trim().isEmpty()){
                                            l3.addElement(grps.split("//")[i].trim());}
                                        else {
                                            l3.clear();
                                            l3.addElement("NO ONE");
                                        }}
                                }

                                grplist.setModel(l3);
                                grplist.clearSelection();


                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });////fin actualiser



        //lancer une conversation en cliquant sur un element de la liste des amis connecter
                    lConnected.addListSelectionListener(new ListSelectionListener(){
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (!e.getValueIsAdjusting()) {
                                try {
                                    if (lConnected.getSelectedValue() != null) {
                                        if (lConnected.getSelectedValue().toString() != null) {
                                            String pseudo = lConnected.getSelectedValue().toString();

                                            Conversation c = new Conversation(homesocket, recieved, pseudo);
                                            lConnected.clearSelection();
                                        }
                                    }

                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                    });






//ajouter un amis
        ADDButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e){
                try {
                    String pseudpNewFriends = newFriend.getText();
                    if(!(pseudpNewFriends.isEmpty())) {
                        String data2 = "AddFriend//" + pseudpNewFriends + "//" + recieved;
                        byte[] buffer2 = data2.getBytes();


                        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adress, 2010);
                        socket.send(packet2);
                        byte[] result2 = new byte[65024];
                        packet2 = new DatagramPacket(result2, result2.length);

                        socket.receive(packet2);
                        String addFriends = new String(packet2.getData(), 0, packet2.getLength());

                        if(addFriends.equals("friendship existe")){
                            resultField.setText("amitié existe deja");
                            newFriend.setText("");
                        }else if(addFriends.equals("amitié ajouter")){
                            resultField.setText("ami ajouté");
                            newFriend.setText("");
                        }else if(addFriends.equals("ami n'existe pas")){
                            resultField.setText("ami n'existe pas");
                            newFriend.setText("");
                        } else if (addFriends.equals("meme pseudo")) {
                            resultField.setText("Impossible!");
                            newFriend.setText("");

                        }



                    }

                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });//fin traitement ajouter un ami

//cliquer sur un amis de la liste d'ami pour ajouter
        UL.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (UL.getSelectedValue() != null) {

                        String pseudo = UL.getSelectedValue().toString();
                        UL.clearSelection();
                                String data2 = "DeleteFriend//" + pseudo + "//" + recieved;
                                byte[] buffer2 = data2.getBytes();


                                DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adress, 2010);
                                try {
                                    socket.send(packet2);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                byte[] result2 = new byte[65024];
                                packet2 = new DatagramPacket(result2, result2.length);

                                try {
                                    socket.receive(packet2);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                String deleteFriends = new String(packet2.getData(), 0, packet2.getLength());

                                if (deleteFriends.equals("deleted")) {
                                   System.out.println(pseudo+"deleted");
                                    delInfo.setText(pseudo+" deleted!");
                                } else if (deleteFriends.equals("error")) {
                                    delInfo.setText("error");
                                }


                    }
                }
            }
        });//fin traitement de supprimer amis

        //ajouter un groupe
        ADDGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newgrp = tfgrp.getText();
                if(!newgrp.isEmpty()){
                try {
                    //lancer la page de group cpour cree un groupe
                    Group g = new Group(homesocket,recieved,newgrp,friends);
                        tfgrp.setText("");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }}
                else{
                    resgrp.setText("Please provide a groupe name!");
                }

            }
        });//fin ajouter groupe


        //traitement pour avoir la liste de group de utilisateur
        String d2= "groupes//"+recieved;
        byte[] b2 = d2.getBytes();


        DatagramPacket pa2=new DatagramPacket(b2,b2.length,adress,2010);
        socket.send(pa2);
        byte[] re2= new byte[65024];
        pa2=new DatagramPacket(re2,re2.length);

        socket.receive(pa2);
        String grps = new String(pa2.getData(),0,pa2.getLength());

//afficher la liste des groupes
        if(grps.split("//")[0].equals("grps")){
            l3.clear();
            for(int i=1;i<grps.split("//").length;i++){
                if(!grps.split("//")[i].trim().isEmpty()){
                    l3.addElement(grps.split("//")[i].trim());}
                else {
                    l3.addElement("NO ONE");
                }}
        }

        grplist.setModel(l3);
        grplist.clearSelection();//fin affichage de groupe


//en cliquant sur un element de la liste grp lancer une conversation de groupe
        grplist.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try {
                        if (grplist.getSelectedValue() != null) {
                            if (grplist.getSelectedValue().toString() != null) {
                                String nomgrp = grplist.getSelectedValue().toString();

                                ConversationGroup c = new ConversationGroup(homesocket, recieved, nomgrp);
                                grplist.clearSelection();
                            }
                        }

                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        });



    }
}