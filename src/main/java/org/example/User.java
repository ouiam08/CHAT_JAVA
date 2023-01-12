package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class User implements Runnable{


    String pseudo;
  String password;
    DatagramSocket socket = new DatagramSocket();
  int port;

  InetAddress address;
    public User(String pseudo, String password, int port, InetAddress address) throws SocketException {

        this.pseudo = pseudo;
        this.password = password;
        this.port = port;
        this.address = address;
    }

    public User() throws SocketException {
        this.pseudo = "";
        this.password = "";
        this.port = 0;
        this.address = null;
    }

    public void run(){
//on lance jiste la 1er page
            Acceuil a = new Acceuil(socket);




   }
//lancer le user
    public static void main(String[] args) throws SocketException {
            User u = new User();
            u.run();

    }
}
