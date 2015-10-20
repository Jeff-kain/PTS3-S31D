/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Rob
 */
public class ClientFXController implements Initializable {

    @FXML TextField tfUsername,tfSend;
    @FXML Button btnConnect, btnDisconnect, btnSend;
    @FXML TextArea taChat;

    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    Boolean isConnected = false;
    int port = 2222;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    InetAddress addr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }
    
    public void initComponents(){taChat.setEditable(false);}
    
    public void btSend(Event evt) {
    if ((tfSend.getText()).equals("")) {
        tfSend.setText("");
        tfSend.requestFocus();
    } else {
        try {
            writer.println(username + ":" + tfSend.getText() + ":" + "Chat");
            writer.flush();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            taChat.appendText("Message was not sent. \n");
        }
        tfSend.setText("");
        tfSend.requestFocus();
        }
    }
    
    public void btConnect(Event evt) {
    if (isConnected == false) {
        if(tfUsername.getText().equals("")) {

            Random generator = new Random();
            int i = generator.nextInt(999) + 1;
            username = "anon".concat(String.valueOf(i));
            
            tfUsername.setText(username);
            tfUsername.setEditable(false);
        } 
        else {
            username = tfUsername.getText();
            tfUsername.setEditable(false);
        }

        try {
            
            //addr = InetAddress.getByName("192.168.179.1");
            sock = new Socket(address, port);
            InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamreader);
            writer = new PrintWriter(sock.getOutputStream());
            writer.println(username + ":has connected.:Connect");
            writer.flush();
            
            isConnected = true;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            taChat.appendText("Cannot Connect! Try Again. \n");
            tfUsername.setEditable(true);
            }

            ListenThread();

        } else if (isConnected == true) {
            taChat.appendText("You are already connected. \n");
        }
    }
    
    public void btDisconnect(Event evt) {
        sendDisconnect();
        Disconnect();
        taChat.setText("");
    }
    
    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        users.add(data);
    }

    public void userRemove(String data) {
        taChat.appendText(data + " is now offline.\n");
    }

    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token : tempList) {
            taChat.appendText(token + "\n");
        }
    }

    public void sendDisconnect() {
        try {
            writer.println(username + ": :Disconnect");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            taChat.appendText("Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {
        try {
            taChat.appendText("Disconnected.\n");
            sock.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            taChat.appendText("Failed to disconnect. \n");
        }
        isConnected = false;
        tfUsername.setEditable(true);
}

    public class IncomingReader implements Runnable {

    @Override
    public void run() {
        String[] data;
        String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

        try {
            while ((stream = reader.readLine()) != null) {
                data = stream.split(":");

                if (data[2].equals(chat)) {
                    taChat.appendText(data[0] + ": " + data[1] + "\n");
                    taChat.end(); 
                    
                } else if (data[2].equals(connect)) {
                    //taChat.setText("");
                    userAdd(data[0]);
                    
                } else if (data[2].equals(disconnect)) {
                    userRemove(data[0]);
                    
                } else if (data[2].equals(done)) {
                    writeUsers();
                    users.clear();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      }
   }
}

