package portalserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PortalServer {

    private static final int portNumber = 1099;
    private static final String bindingName = "portal";

    private Registry registry;
    private Portal portal;
    
    private ArrayList clientOutputStreams;
    private ArrayList<String> users;  
    private BufferedReader reader;
    private boolean isStopped;
    private Socket sock;
    private PrintWriter client;

    public PortalServer() throws UnknownHostException {
        try {
            // Creating a new instance of the Portal.
            portal = new Portal();
            System.out.println("Portal created \n");
        } catch (RemoteException ex) {
            System.out.println("Cannot create portal");
            System.out.println("RemoteException: " + ex.getMessage());
        }

        try {
            // Creating registry
            System.out.println("Creating the registry...");
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("IP Address: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Cannot create registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
        }

        try {
            // Binding the portal
            registry.rebind(bindingName, portal);
        } catch (RemoteException e) {
            System.out.println("Server: Cannot bind Portal");
            System.out.println("Server: RemoteException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Portnumber: " + portNumber);
        System.out.println("BindingName: " + bindingName);

        System.out.println("Server started.\n");

        PortalServer server = null;
        try {
            server = new PortalServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        server.Start();
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

                    Thread listener = new Thread(new PortalServer.ClientHandler(clientSock, writer));
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
