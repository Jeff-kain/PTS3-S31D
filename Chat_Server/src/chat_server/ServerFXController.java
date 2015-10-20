/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Rob
 */
public class ServerFXController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextArea taChat;
    ArrayList clientOutputStreams;
    ArrayList<String> users;  
    
    BufferedReader reader;
    Socket sock;
    PrintWriter client;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();
    }
    
    public void initComponents(){taChat.setEditable(false);}
    
    public void btStart (Event evt) {
        try {
            Thread starter = new Thread(new ServerStart());
            starter.start();
            taChat.appendText("Server started...\n");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void btEnd (Event evt) {
        
    tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
    taChat.appendText("Server stopping... \n");
    taChat.appendText("");
    }          
    
    public void btOnline (Event evt) {
        taChat.appendText("\n Online users : \n");
        for (String current_user : users)
        {
            taChat.appendText(current_user);
            taChat.appendText("\n");
        }   
    }

    public void btClear (Event evt) {
        taChat.setText("");
    }  
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();
            //InetAddress addr;

            try {
                //addr = InetAddress.getByName("192.168.179.1");
                //System.out.println(addr.toString());
                ServerSocket serverSock = new ServerSocket(2222);
                
                while (true) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    taChat.appendText("Got a connection. \n");
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                taChat.appendText("Error making a connection. \n");
            }
        }
    }
        
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        taChat.appendText("Before " + name + " added. \n");
        users.add(name);
        taChat.appendText("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            //message = (token + add);
            //tellEveryone(message);
        }
        
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            //message = (token + add);
            //tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		taChat.appendText("Sending: " + message + "\n");
                writer.flush();

            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
		taChat.appendText("Error telling everyone. \n");
            }
        } 
    }  
    
    public class ClientHandler implements Runnable	
    {

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                taChat.appendText("Unexpected error... \n");
            }
       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try {
                while ((message = reader.readLine()) != null) 
                {
                    taChat.appendText("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        taChat.appendText(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        System.out.println(message);
                        tellEveryone(message);
                    } 
                    else 
                    {
                        taChat.appendText("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception e) 
             {
                System.out.println(e.getMessage());
                taChat.appendText("Lost a connection. \n");
                clientOutputStreams.remove(client);
             } 
	} 
    }
}
