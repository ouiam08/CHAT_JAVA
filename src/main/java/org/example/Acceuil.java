package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Acceuil extends JFrame{
    private JPanel acceuil;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnCancel;

    DatagramSocket acceuilsocket;

    public Acceuil(DatagramSocket socket) {
        setTitle("Acceuil");
        setContentPane(acceuil);
        setMinimumSize(new Dimension(600,429));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        acceuilsocket = socket;

        //for login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login(Acceuil.this,acceuilsocket);
                } catch (SocketException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //for register
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration registration=new Registration(Acceuil.this,acceuilsocket);

            }
        });


        //pour sortir
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



}
