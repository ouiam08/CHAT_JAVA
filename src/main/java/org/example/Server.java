package org.example;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import  java.sql.*;
public class Server {

    public final static int PORT = 2010;
    private final static int BUFFER = 65024;

    private DatagramSocket socket;
    private ArrayList<InetAddress> client_addresses;
    private ArrayList<Integer> client_ports;
    private HashSet<String> existing_clients;

    public ArrayList<User> sessionUsers ;

    final String DB_URL ="jdbc:mysql://localhost:3306/chat_app";
    final String USERNAME = "root";
    final String PASSWORD = "";


    public Server() throws IOException {
        socket = new DatagramSocket(PORT);
        System.out.println("Server started ...");
        client_addresses = new ArrayList();
        client_ports = new ArrayList();
        existing_clients = new HashSet();
        sessionUsers =  new ArrayList();

    }

    public boolean deleteFriendship(int id1,int id2) throws SQLException {

        //methode qui permet de supprimer un ami de la base de donnée

        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement stmt = conn.createStatement();

        // delete the row with id = 1 from the table "users"
        String sql = "DELETE FROM friendship WHERE id_p1="+id1+" and id_p2="+id2+";";
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();


        return true;
    }

    public boolean addFriendship(int id1,int id2){

        //methode qui permet de d'ajouter un ami a la base de donnée

        boolean result =  false;



//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO friendship (id_fr, id_p1, id_p2)"+" VALUES (NULL, ?, ?) ;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id1);
            ps.setInt(2,id2);

            int addedRows = ps.executeUpdate();
            if(addedRows > 0){
                result =  true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public boolean userRegistration(String mess){
        //methode qui permet de d'ajouter un user a la base de donnée

        boolean result =  false;
        String typeOfOperation=mess.split("//")[0].trim();
        String pseudo = mess.split("//")[2].trim();
        String password = mess.split("//")[4].trim();
//        String confirmPassword = mess.split("//")[5].trim();

                try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users ( pseudo_user, password_user) "+"VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,pseudo);
            ps.setString(2,password);

            int addedRows = ps.executeUpdate();
            if(addedRows > 0){
                result =  true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public boolean userExist(String mess){

        //methode qui permet de tester si le user existe dans la BD

        boolean result = false;

        String pseudo = mess.split("//")[2].trim();
        String password = mess.split("//")[4].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from users where pseudo_user = ? and password_user=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,pseudo);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
             result = true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean friendExist(int id1,int id2){
        //methode qui permet de tester si l amitié existe a la base de donnée

        boolean result = false;



        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from friendship where id_p1 = ? and id_p2=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id1);
            ps.setInt(2,id2);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public  String getPseudoFromUsers(int id){
        //methode qui permet de retourner le pseudo de id donnee en argument

        String res = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT pseudo_user FROM users WHERE id_user="+id+";";
            PreparedStatement ps = conn.prepareStatement(sql);



            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                res=es.getString("pseudo_user") ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public  String getMembersFromNamegrp(String name){
        //methode qui permet de retourner les membres de nom de group donnee en argument

        String res = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT pseudos FROM groupe WHERE nom=\""+name+"\";";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                res=es.getString("pseudos") ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public  int getcreator(String name){
        //methode qui permet de retourner le createur du groupe  de nom de group donnee en argument

        int res = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT creator FROM groupe WHERE nom=\""+name+"\";";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                res=es.getInt("creator") ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public  int getIdFromPseudo(String pseudo){
        //methode qui permet de retourner le id de pseudo donnee en argument

        int res = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_user FROM users WHERE pseudo_user=\""+pseudo+"\";";
            PreparedStatement ps = conn.prepareStatement(sql);



            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                res=es.getInt("id_user") ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public  ArrayList<Integer> getIdsFriends1(String pseudo){
        //methode qui permet de retourner la liste des amis de pseudo donnee en argument

        ArrayList<Integer> result = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_p1 FROM friendship WHERE id_p2=(SELECT id_user FROM users WHERE pseudo_user= '"+pseudo+"')";
            PreparedStatement ps = conn.prepareStatement(sql);



            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                result.add(es.getInt("id_p1")) ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public  ArrayList<Integer> getIdsFriends2(String pseudo){
        //methode qui permet de retourner la liste des amis de pseudo donnee en argument

        ArrayList<Integer> result = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_p2 FROM friendship WHERE id_p1=(SELECT id_user FROM users WHERE pseudo_user= '"+pseudo+"')";
            PreparedStatement ps = conn.prepareStatement(sql);



            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                result.add(es.getInt("id_p2")) ;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public  String getFriends(String pseudo){
        //methode permet de combiner les resultat des deuxmethodes getIdsFriends1 et getIdsFriends2 car
        //des foix id_user1 et celui de user voulu des foix c'est id_user2
        //les deux resultat sont trasfomer dans cette methode dans un string retourner a la fin de la methode


        String resultatFinal="";
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> result2 = new ArrayList<>();
        ArrayList<String> result1 = new ArrayList<>();
        result = getIdsFriends1(pseudo);
        result2 = getIdsFriends2(pseudo);

//rassembler les deux liste d ami dans une seul liste
        for(int i : result){
            result1.add(getPseudoFromUsers(i));
        }
        for(int i : result2){
            result1.add(getPseudoFromUsers(i));
        }


        //ecrire la liste dans un string en inserantentre chaque pseudo d'ami //
        for(String i : result1){
            resultatFinal += i + "//";
        }
        return  resultatFinal;
    }

    public boolean addGroup(int creator,String nom,String pseudos){
        //methode qui permet d'ajouter un groupe a la base de donnee donnee en argument

        boolean result =  false;



//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO groupe (id, nom, pseudos, creator)"+" VALUES (NULL, ?, ?,?) ;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,nom);
            ps.setString(2,pseudos);
            ps.setInt(3,creator);

            int addedRows = ps.executeUpdate();
            if(addedRows > 0){
                result =  true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean grpExist(String nom){
        //methode qui permet de voir si le groupe existe dans la base de donner
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from groupe where nom = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,nom);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = true;
            }
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public  ArrayList<String> getgrps(String pseudo){

        //methode qui permet de retourner la liste des groupe d'une personne d'apres son pseudo


        ArrayList<String> result = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom FROM groupe WHERE pseudos LIKE '%"+pseudo+"%';";
            PreparedStatement ps = conn.prepareStatement(sql);



            ResultSet es =  stmt.executeQuery(sql);

            while(es.next()){
                result.add(es.getString("nom")) ;
            }
            stmt.close();
            conn.close();


        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public void run() {
        byte[] buffer = new byte[BUFFER];
        while (true) {
            try {
//le serveur recoit un seul packet a chaque foix et verifie selon c qui a recu et il envoie une reponse
                Arrays.fill(buffer, (byte) 0);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(buffer, 0, buffer.length).trim();

                InetAddress clientAddress = packet.getAddress();
                int client_port = packet.getPort();


                //avant de s'identifier les port et adresse des users sont stocker dans les array list suivant
                String id = clientAddress.toString() + "|" + client_port;
                if (!existing_clients.contains(id)) {
                    existing_clients.add(id);
                    client_ports.add(client_port);
                    client_addresses.add(clientAddress);
                }


                        //traitement si request recu commence par login=>pour le login
                if(message.split("//")[0].trim().equals("login")){
                if(userExist(message)){

                    String pseudo = message.split("//")[2].trim();
                    String password = message.split("//")[4].trim();


                    //instancier un user
                    //ajouter le user a la liste des sessionUsers
                    User user = new User(pseudo,password,client_port,clientAddress);
                    sessionUsers.add(user);

                    // si user existe envoyer le pseudo
                    System.out.println(pseudo+" : user existe");

                    byte[] response = pseudo.getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);
                }else {
                    String pseudo = message.split("//")[2].trim();

                    System.out.println(pseudo+" : user n existe pas");
                    // si user n'existe pas  envoyer une erreur

                    byte[] response = "erreur".getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);
                }}

                //traitement si request recu commence par registration=>pour le registration

                if(message.split("//")[0].trim().equals("registration")){
                    String pseudo = message.split("//")[2].trim();
                    if(userExist(message)){

                        System.out.println(pseudo+" : user already existe");
                        //si user existe envoyer existe
                        byte[] response = "existe".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }
                  else if(userRegistration(message)){

                    String password = message.split("//")[4].trim();

                    System.out.println(pseudo+" : user registred");
                        //si user n'existe pas inserer le nouveau user et envoyer le pseudo
                    byte[] response = pseudo.getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);


                }else if(!userRegistration(message)) {


                    System.out.println(pseudo+" : error of registration");
                    //si il ya un probleme de resistration envoyer erreur
                    byte[] response = "erreur".getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);
                }
               }

                        //traitement si on accede a la page home
                    if(message.equals("home")){
                        //retourner le dernier user (la dernier personne qui a fait le login)
                    User last_user= sessionUsers.get(sessionUsers.size()-1);

                    //envoyer son pseudo
                    byte[] response = last_user.pseudo.getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);}

                    //traitement pour enviyer la liste des amis
                if(message.split("//")[0].trim().equals("friends")){
                    String pseudo = message.split("//")[1].trim();
                    String res="friends//"+ getFriends(pseudo);
                    System.out.println(pseudo+ "friends"+res);
                    byte[] res1 = new byte[65024];
                     res1 = res.getBytes();
                    DatagramPacket packet1 = new DatagramPacket(res1, res1.length, clientAddress, client_port);
                    socket.send(packet1);
                }


                //traitement pour envoyer la listes des amis connecter
                if(message.split("//")[0].trim().equals("ConnectedFriends")){
                    String pseudo = message.split("//")[1].trim();
                    String res= getFriends(pseudo);
                    String connFriend ="";
                    for(User i : sessionUsers){
                        for(int j=0;j<res.split("//").length;j++){
                        if (i.pseudo.equals(res.split("//")[j].trim())) {
                            connFriend += i.pseudo+"//";

                        }}
                        if(connFriend.equals("")){
                            connFriend = "NO ONE";
                        }
                    }
                    System.out.println(pseudo+ " ConnectedFriends "+connFriend);
                    byte[] res1 = new byte[65024];
                    res1 = connFriend.getBytes();
                    DatagramPacket packet1 = new DatagramPacket(res1, res1.length, clientAddress, client_port);
                    socket.send(packet1);
                }



                //traitement pour logout , enlever la personne de la session
                if(message.split("//")[0].trim().equals("logout")){
                    String pseudo = message.split("//")[1].trim();
                    User u = new User();

                    for(User i : sessionUsers){

                            if (i.pseudo.equals(pseudo)) {
                              u = i;
                            }}
                            sessionUsers.remove(u);
                    System.out.println(pseudo+ " removed from session ");
                }


                //traitement pour boutton actualiser
                if(message.split("//")[0].trim().equals("Actualiser")){
                    String pseudo = message.split("//")[1].trim();
                    String res= getFriends(pseudo);//mettre la liste des pseudo des amis  separer par //
                    String connFriend ="";//mettre les pseudo des amis connecter separer par //
                    for(User i : sessionUsers){
                        for(int j=0;j<res.split("//").length;j++){
                            if (i.pseudo.equals(res.split("//")[j].trim())) {
                                connFriend += i.pseudo+"//";

                            }}

                    }
                    if(connFriend.equals("")){
                        connFriend = "NO ONE";
                    }
//                    System.out.println(pseudo+ " ConnectedFriends "+connFriend);
//                    System.out.println(pseudo+ " Friends "+res);
                    String resultatFinal = "";
                    ArrayList<String> grp = new ArrayList<>();
                    grp = getgrps(pseudo);
                    for (String i : grp){
                        resultatFinal += i + "//";
                    }
//                    System.out.println(resultatFinal);
                    String msg = "grps//"+resultatFinal;
                    String rr =  res + "-" + connFriend+"-"+msg;// metrre liste des amis et liste des amis connecter separer par -

                    byte[] res1 = new byte[65024];
                    res1 = rr.getBytes();//envoyer les deux listes
                    DatagramPacket packet1 = new DatagramPacket(res1, res1.length, clientAddress, client_port);
                    socket.send(packet1);
                }


                //traitement poour envoyer un msg entre deux personne
                if(message.split("//")[0].trim().equals("sendMessageTo")){

                    String pseudo = message.split("//")[1].trim();
                    String senderPseudo =  message.split("//")[3].trim();
                    String msg = "newMess//"+message.split("//")[2].trim() + "//"+senderPseudo+"//"+pseudo;
                    System.out.println("msg "+msg);
                    System.out.println("sender"+senderPseudo);
                    System.out.println("pseudo"+pseudo);
                    InetAddress adr = null;
                    int port = 0;

                    //trouver adresse et portt d'amis pour le contacter
                    for(User i : sessionUsers ){
                        if(i.pseudo.equals(pseudo)){
                            adr = i.address;
                            port = i.port;
                        }
                    }

                    byte[] res1 = new byte[65024];
                    res1 = msg.getBytes();
                    DatagramPacket packet1 = new DatagramPacket(res1, res1.length, adr, port);
                    socket.send(packet1);
                    System.out.println(msg+ "is sent msg to usr");

                }


                //traitement pour ajouter un nouveau ami
                if(message.split("//")[0].trim().equals("AddFriend")){
                    String pseudo1 = message.split("//")[1].trim();
                    String pseudo2 = message.split("//")[2].trim();
                    int id1= getIdFromPseudo(pseudo1);
                    int id2= getIdFromPseudo(pseudo2);
//                    System.out.println("id1  "+id1+"id2  "+id2);
                    if(!(pseudo1.equals(pseudo2))){
                    if(friendExist(id1,id2)||friendExist(id2,id1)){

                        System.out.println(pseudo1+" et "+pseudo2+"deja amis");
                        byte[] response = "friendship existe".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }else if(id1==0 || id2==0){
                        System.out.println("ami n'existe pas");
                        byte[] response = "ami n'existe pas".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }
                    else {
                        addFriendship(id1,id2);


                        String msg="amitié ajouter";
                        System.out.println(pseudo1+" et "+pseudo2+"ont devenu amis");

                        byte[] response = msg.getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);


                    }}else if(pseudo1.equals(pseudo2)){

                        String msg="meme pseudo";
                        System.out.println("meme pseudo");

                        byte[] response = msg.getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }
                }


                //traitement pour supprimer un amis de la liste des amis
                if(message.split("//")[0].trim().equals("DeleteFriend")){
                    String pseudo1 = message.split("//")[1].trim();
                    String pseudo2 = message.split("//")[2].trim();
                    int id1= getIdFromPseudo(pseudo1);
                    int id2= getIdFromPseudo(pseudo2);
                    System.out.println("id1  "+id1+"id2  "+id2);
                    if(friendExist(id1,id2)){
                        deleteFriendship(id1,id2);
                        System.out.println("deleted");
                        byte[] response = "deleted".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }else if(id1==0 || id2==0){
                        System.out.println("error");
                        byte[] response = "error".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);
                    }
                    else if(friendExist(id2,id1)){
                        deleteFriendship(id2,id1);
                        System.out.println("deleted");
                        byte[] response = "deleted".getBytes();
                        packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                        socket.send(packet);


                    }
                }


                //traitement pour ajouter un nouveau groupe
                if(message.split("//")[0].trim().equals("Addgrp")){
                    String creator = message.split("//")[1].trim();
                   int idCreator = getIdFromPseudo(creator);
                   String members = message.split("-")[1].trim();
                   String grpName = message.split("//")[2].trim();

                   if(grpExist(grpName)&& members != ""){
                       byte[] response = "group already existe".getBytes();
                       packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                       socket.send(packet);
                   }else {
                      if( addGroup(idCreator,grpName,members)){
                          String msg= "group "+grpName+" created!";
                          byte[] response = msg.getBytes();
                          packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                          socket.send(packet);
                      }

                   }
                }

                //traitement pour envoyer tout les groupe dont un user appartient
                if(message.split("//")[0].trim().equals("groupes")){
                    String grps = message.split("//")[0].trim();
                    String pseudo =message.split("//")[1].trim();
                    String resultatFinal = "";
                    ArrayList<String> grp = new ArrayList<>();
                    grp = getgrps(pseudo);
                    for (String i : grp){
                        resultatFinal += i + "//";
                    }
                    System.out.println(resultatFinal);
                    String msg = "grps//"+resultatFinal;
                    byte[] response = msg.getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);

                    }

                //traitement pour envoyer un message a un groupe
                if(message.split("//")[0].trim().equals("sendMessageToGroup")){

                    String gName = message.split("//")[1].trim();
                    String senderPseudo =  message.split("//")[3].trim();
                    String msg = "newGRPMess//"+message.split("//")[2].trim() + "//"+senderPseudo+"//"+gName;
                    InetAddress adr = null;
                    int port = 0;
                    String members = getMembersFromNamegrp(gName);
                    for(int j =0;j<members.split("//").length;j++){
                        for(User i : sessionUsers ){

                            //envoyer a tout les membre sauf emetteur
                            if(i.pseudo.equals(members.split("//")[j].trim())&&!(members.split("//")[j].trim().equals(senderPseudo))){
                                adr = i.address;
                                port = i.port;
                                byte[] res1 = new byte[65024];
                                res1 = msg.getBytes();
                                DatagramPacket packet1 = new DatagramPacket(res1, res1.length, adr, port);
                                socket.send(packet1);
                                System.out.println(msg+ "is sent msg to usr");
                            }
                        }
                    }

                }


                //traitement pour envoyer le createur du groupe
                if(message.split("//")[0].trim().equals("creator")){
                    String gName = message.split("//")[1].trim();

                    int idC = getcreator(gName);
                    if(idC !=0){
                    String resultatFinal = getPseudoFromUsers(idC);


                    System.out.println(resultatFinal);
                    String msg = "creator//"+resultatFinal;
                    byte[] response = msg.getBytes();
                    packet = new DatagramPacket(response,response.length,clientAddress,client_port);
                    socket.send(packet);}

                }



                } catch (Exception e) {
                System.err.println(e);
            }
        }
       }

//pour lancer le serveur
    public static void main(String args[]) throws Exception {
        Server server = new Server();
        server.run();
    }


}
