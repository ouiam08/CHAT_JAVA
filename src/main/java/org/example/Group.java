package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Group extends JFrame {
    private JButton createButton;
    private JList list1;
    private JPanel grp;

    DatagramSocket grpsocket ;
    String addgrp ="";

    public String getAddgrp() {
        return addgrp;
    }

    String members;
    public Group(DatagramSocket socket, String pseudo,String grpName,String friends) throws Exception {
        setTitle("ADD GROUP");
        setContentPane(grp);
        setMinimumSize(new Dimension(600, 429));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        grpsocket = socket;
        InetAddress adress = InetAddress.getLocalHost();
         members=pseudo+"//";
        DefaultListModel<String> l1 = new DefaultListModel<>();


        //demander la liste des amis pour que le user puisse choisir la liste d'ami a ajouter dans un groupe
        if(friends.split("//")[0].equals("friends")) {
            l1.clear();
            for (int i = 1; i < friends.split("//").length; i++) {
                l1.addElement(friends.split("//")[i].trim());
            }
            list1.setModel(l1);
        }


        //cree le groupe
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (list1.getSelectedValuesList() != null) {

                        List<String> selectedValues = list1.getSelectedValuesList();
                        list1.getSelectedValuesList().clear();
                        System.out.println(selectedValues);
                        for (String item : selectedValues) {
//                                System.out.println(item);
                            members += item + "//";
                        }


                    }


                    System.out.println(members);

                    String data2 = "Addgrp//" + pseudo+"//"+grpName + "//-" + members;
                    byte[] buffer2 = data2.getBytes();


                    DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adress, 2010);
                    socket.send(packet2);
                    byte[] result2 = new byte[65024];
                    packet2 = new DatagramPacket(result2, result2.length);

                    socket.receive(packet2);
                     addgrp = new String(packet2.getData(), 0, packet2.getLength());
                    System.out.println(addgrp);

                    //si le groupe existe afficher une pane d erreur
                    if(addgrp.equals("group already existe")){
                        JOptionPane.showMessageDialog(Group.this, "group already existe", "Try again", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }else{

                        //si le groupe est cree afficher une panne pour confirmer
                        JOptionPane.showMessageDialog(Group.this, "group created!", "ok", JOptionPane.INFORMATION_MESSAGE);

                        dispose();
                    }




                } catch (Exception e3) {
                    e3.printStackTrace();
                }

            }

        });

    }


}