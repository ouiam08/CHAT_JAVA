package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Registration extends JDialog{
    private JTextField tfPseudo;
    private JTextField tfPassword;
    private JTextField tfConfirmPass;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel pseudo;
    private JLabel password;
    private JLabel confirmPassword;
    private JPanel registerPanel;

    DatagramSocket registerSocket;

    public Registration(JFrame parent ,DatagramSocket socket ){
        super(parent);
        setTitle("Create a new user account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(750,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerSocket = socket;


        btnRegister.addActionListener(new ActionListener() {
            @Override
            //lancer la methode register users en cliquant sur register
            public void actionPerformed(ActionEvent e) {
                registerUser();
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


    //traitement de register
    private void registerUser() {
        String pseudo = tfPseudo.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfConfirmPass.getText();
        String data = "registration//pseudo//"+ pseudo + "//password//" + password+"//confirmPassword//"+confirmPassword;
//les champ de mdp deoivent etre identique, et les champs ne doivent pas etre vide sinon afficher une erreur
        if (pseudo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords does not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }else{


        try {

            byte[] buffer= new byte[256];
            buffer=data.getBytes();
            InetAddress adress = InetAddress.getLocalHost();
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length,adress,2010);
            registerSocket.send(packet);

            packet=new DatagramPacket(buffer,buffer.length);
            registerSocket.receive(packet);

            String recieved = new String(packet.getData(),0,packet.getLength()).trim();
//            System.out.println(recieved);
            if(recieved.equals(pseudo)){
                System.out.println("sortir");
                dispose();


                Login g = new Login(null,registerSocket);
            }else if(recieved.equals("erreur")){

                JOptionPane.showMessageDialog(Registration.this, "Pseudo or Password invalid!", "Try again", JOptionPane.ERROR_MESSAGE);
            }else if(recieved.equals("existe")){
                JOptionPane.showMessageDialog(Registration.this, "User Already existe!", "Try again", JOptionPane.ERROR_MESSAGE);
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }}

    }
}
