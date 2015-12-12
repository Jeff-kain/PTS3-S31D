/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Rob
 */
public class Chat_Server_Console {

    /**
     * 
     * @param args the command line arguments
     */
    ArrayList clientOutputStreams;
    ArrayList<String> users;  
    
    BufferedReader reader;
    boolean isStopped;
    Socket sock;
    PrintWriter client;
    
    public static void main(String[] args) {
        // TODO code application logic here
        Chat_Server_Console Chat = new Chat_Server_Console();
        Chat.Start();
    }

    public void Start() {
        isStopped = true;
        StartServer();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String str = scanner.nextLine();
            if("online".equalsIgnoreCase(str)) {
                Online();
            }
            if ("stop".equalsIgnoreCase(str)) {
                System.out.println("Shutting down server");
                EndServer();
                System.exit(0);
                break;
            }
        }
    }
    
    public void Display(String Input) {
        try {
        System.out.println(Input);
        } catch(Exception e) {}
    }
    
    public void StartServer () {
        try {
            isStopped = false;
            Thread starter = new Thread(new ServerStart());
            starter.start();
            Display("Server started...\n");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void EndServer () {
        try {
            tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
            Display("Server stopping... \n");
            Display("");
            isStopped = true;
        } catch(Exception e) {}
    
    }          
    
    public void Online () {
        try {
        Display("\n Online users : \n");
        for (String current_user : users)
        {
            Display(current_user);
            Display("\n");
        }   
        } catch(Exception e) {}
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
                System.out.println(e.getMessage());
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
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        try {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        //for (String token:tempList) 
        //{
            //message = (token + add);
            //tellEveryone(message);
        //}
        tellEveryone(done);
        } catch(Exception e) {}
    }
    
    public synchronized void tellEveryone(String message) 
    {
        try {
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
		Display("Error telling everyone. \n");
            }
        } 
                } catch(Exception e) {}
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
                    
                    for (String token:data) {
                        Display(token + "\n");
                    }

                    if (data[2].equals(connect)) {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) {
                        tellEveryone(message);
                    } 
                    else {
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
