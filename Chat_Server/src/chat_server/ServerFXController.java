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
    boolean isStopped;
    Socket sock;
    PrintWriter client;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();
        isStopped = true;
    }
    
    public void initComponents(){taChat.setEditable(false);}
    
    public void Display(String Input) {
        taChat.appendText(Input);
    }
    
    public void btStart (Event evt) {
        try {
            isStopped = false;
            Thread starter = new Thread(new ServerStart());
            starter.start();
            Display("Server started...\n");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void btEnd (Event evt) {
        
    tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
    Display("Server stopping... \n");
    Display("");
    isStopped = true;
    
    }          
    
    public void btOnline (Event evt) {
        Display("\n Online users : \n");
        for (String current_user : users)
        {
            Display(current_user);
            Display("\n");
        }   
    }

    public void btClear (Event evt) {
        taChat.setText("");
    }  
    
    public class ServerStart implements Runnable 
    {
        @Override
        public synchronized void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();
            InetAddress addr;

            try {
                //System.out.println(addr.toString());
                //145.93.73.45
                ServerSocket serverSock = new ServerSocket(2222);
                
                while (!isStopped) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    Display("Got a connection. \n");
                }
                
                try {
                    sock.close();

                }
                catch(IOException e) {
                    
                }
            }
            catch (IOException e) {
                Display("Error making a connection. \n");
            }
        }
    }
        
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        Display("Before " + name + " added. \n");
        users.add(name);
        Display("After " + name + " added. \n");
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
		Display("Sending: " + message + "\n");
                writer.flush();

            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
		Display("Error telling everyone. \n");
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
                Display("Unexpected error... \n");
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
                    Display("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        Display(token + "\n");
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
                        tellEveryone(message);
                    } 
                    else 
                    {
                        Display("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception e) 
             {
                Display("Lost a connection. \n");
                clientOutputStreams.remove(client);
             } 
	} 
    }
}
