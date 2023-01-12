package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.sql.*;

public class Login extends JDialog {
    private JTextField tfPseudo;
    private JPasswordField tfPassword;
    private JButton btnDone;
    private JButton btnCancel;
    private JPanel login;

    DatagramSocket loginsocket;


    public Login(JFrame parent,DatagramSocket socket) throws SocketException {
        super(parent);
        setTitle("Login");
        setContentPane(login);
        setMinimumSize(new Dimension(850, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         loginsocket = socket;

         //envoyer les information au seveur et voir si le user existe ou non
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pseudo =  tfPseudo.getText();
                String password = String.valueOf(tfPassword.getPassword());
                String data = "login//pseudo//"+ pseudo + "//password//" + password;
//si les champs sont vides =>afficher erreur
                if (pseudo.isEmpty() || password.isEmpty() ) {
                    JOptionPane.showMessageDialog(Login.this, "Please fill all the fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{


                        try {

                            byte[] buffer= new byte[256];
                            buffer=data.getBytes();
                            InetAddress adress = InetAddress.getLocalHost();
                            DatagramPacket packet=new DatagramPacket(buffer,buffer.length,adress,2010);
                            loginsocket.send(packet);

                            packet=new DatagramPacket(buffer,buffer.length);
                            loginsocket.receive(packet);

                            String recieved = new String(packet.getData(),0,packet.getLength()).trim();
                            System.out.println(recieved);
                            if(recieved.equals(pseudo)){
                                System.out.println("sortir");
                                dispose();
                                try {
                                    Home H = new Home(loginsocket);
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                            }else if(recieved.equals("erreur")){
//si les info inserer sont fausse afficher erreur
                                JOptionPane.showMessageDialog(Login.this, "Pseudo or Password invalid!", "Try again", JOptionPane.ERROR_MESSAGE);
                            }


                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }}

            }
        });


        //pour sortir
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);


    }



}


