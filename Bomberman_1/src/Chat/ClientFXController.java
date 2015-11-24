///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package chat_client;
//
//
//import Chat.GameFXMLController;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.ResourceBundle;
//import javafx.event.Event;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//
///**
// *
// * @author Rob
// */
//public class ClientFXController implements Initializable {
//
//    @FXML TextField tfSend, tfUsername;
//    @FXML Button btnSend;
//    @FXML Label TextUsername;
//    @FXML TextArea taChat;
//    
//    //145.93.52.224
//    //145.93.73.45
//
//    String username, address = "localhost";
//    ArrayList<String> users;
//    Boolean isConnected;
//    int port;
//    
//
//
//    Socket sock;
//    BufferedReader reader;
//    PrintWriter writer;
//    InetAddress addr;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        users = new ArrayList();
//        isConnected = false;
//        port = 2222;
//        initComponents();
//        initConnect();
//    }
//    
//    public void initComponents(){taChat.setEditable(false);}
//    
//    public void initConnect() { 
//    try {
//    if (isConnected == false) {
//        //if(tfUsername.getText().equals("")) {
//
////            Random generator = new Random();
////            int i = generator.nextInt(999) + 1;
////            username = "bomberman".concat(String.valueOf(i));
//            
//            //tfUsername.setText(username);
//            //tfUsername.setEditable(false);
//        //} 
//        //else {
//            //username = tfUsername.getText();
//            //tfUsername.setEditable(false);
//        //}
//        if(GameFXMLController.Name == 0)
//        {
//            username = "Player" + 1;
//            TextUsername.setText( username + " :");
//            GameFXMLController.Name++;
//        }
//        
//        username = "Player" + GameFXMLController.Name++;
//        TextUsername.setText( username + " :");
//
//        try {
//            
//            addr = InetAddress.getByName("145.93.72.124");
//            sock = new Socket(addr, port);
//            InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
//            reader = new BufferedReader(streamreader);
//            writer = new PrintWriter(sock.getOutputStream());
//            writer.println(username + ":has connected.:Connect");
//            writer.flush();
//            
//            isConnected = true;
//            
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            Display("Cannot Connect! Try Again. \n");
//            //tfUsername.setEditable(true);
//            }
//
//            ListenThread();
//
//        } else if (isConnected == true) {
//            Display("You are already connected. \n");
//        }
//    } catch(Exception e) {}
//    }
//    
//    public void Display(String Input) {
//        try {
//        taChat.appendText(Input);
//        } catch(Exception e) {}
//    }
//    
//    public void EndChat() {
//        taChat.end();
//    }
//    
//    public void btSend(Event evt) {
//    try {
//    if ((tfSend.getText()).equals("")) {
//        tfSend.setText("");
//        tfSend.requestFocus();
//    } else {
//        try {
//            writer.println(username + ":" + tfSend.getText() + ":" + "Chat");
//            writer.flush();
//            
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            Display("Message was not sent. \n");
//        }
//        tfSend.setText("");
//        tfSend.requestFocus();
//        }
//    } catch(Exception e) {}
//    }
//    
////    public void btDisconnect(Event evt) {
////        try {
////        sendDisconnect();
////        Disconnect();
////        taChat.setText("");
////        } catch(Exception e) {}
////    }
//    
//    public void ListenThread() {
//        try {
//        Thread IncomingReader = new Thread(new IncomingReader());
//        IncomingReader.start();
//        } catch(Exception e) {}
//    }
//
//    public void userAddClient(String data) {
//        users.add(data);
//    }
//
//    public void userRemoveClient(String data) {
//        Display(data + " is now offline.\n");
//    }
//
//    public void writeUsers() {
//        try {
//        String[] tempList = new String[(users.size())];
//        users.toArray(tempList);
//        for (String token : tempList) {
//            Display(token + "\n");
//        }
//        } catch(Exception e) {}
//    }
//
//    public void sendDisconnect() {
//        try {
//            writer.println(username + ": :Disconnect");
//            writer.flush();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            Display("Could not send Disconnect message.\n");
//        }
//    }
//
//    public void Disconnect() {
//        try {
//            Display("Disconnected.\n");
//            sock.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            Display("Failed to disconnect. \n");
//        }
//        isConnected = false;
//        tfUsername.setEditable(true);
//    }
//
//    public class IncomingReader implements Runnable {
//
//    @Override
//    public void run() {
//        String[] data;
//        String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
//
//        try {
//            while ((stream = reader.readLine()) != null) {
//                data = stream.split(":");
//
//                if (data[2].equals(chat)) {
//                    Display(data[0] + ": " + data[1] + "\n");
//                    EndChat();
//                    
//                } else if (data[2].equals(connect)) {
//
//                    userAddClient(data[0]);
//                    
//                } else if (data[2].equals(disconnect)) {
//                    userRemoveClient(data[0]);
//                    
//                } else if (data[2].equals(done)) {
//                    writeUsers();
//                    users.clear();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//      }
//   }
//}
//
