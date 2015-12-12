/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.IGameObject;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author jeffrey
 */
public class HostServer extends UnicastRemoteObject implements IRemoteHost {

    String hostservice;
    Registry registry;
    IRemoteClient service = null;
    private ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private BasicPublisher publisher;

    public HostServer(int registryPort, String servicename) throws RemoteException, UnknownHostException {
        publisher = new BasicPublisher(new String[]{servicename});
        publishHost(registryPort, servicename);
        Thread serverThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(hostservice);
            }
        });
        serverThread.start();
    }

    public void publishHost(int registryPort, String servicename)
            throws RemoteException, UnknownHostException {

        try {
            if (getRegistry() != null) {
                UnicastRemoteObject.unexportObject(getRegistry(), true);
            }
            String ip = InetAddress.getLocalHost().getHostAddress();
            // LocateRegistry.getRegistry(registryPort);
            Registry registry = LocateRegistry.createRegistry(registryPort);
            setRegistry(registry);

            String str = "rmi://" + ip + ":" + registryPort + "/" + servicename;

            Naming.rebind(str, this);
            // System.out.println("ip host:" + ip);
            hostservice = str;
            System.out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retrieveClientService(String strService) {
        // System.out.println("fetch:    rmi://" + IRemoteClient.clientname +
        // ":"
        // + IRemoteClient.registryPort + "/" + IRemoteClient.servicename);
        try {

            // besser ist folgendes:
            // service = (IRemoteClient) LocateRegistry
            // .getRegistry("hostip", 1099).lookup(strService);
            service = (IRemoteClient) Naming.lookup(strService);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Client did not yet publish.");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    @Override
    public void joingame(String strService) throws RemoteException {
        retrieveClientService(strService);
    }

    @Override
    public void movep2c(int direction) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

}
