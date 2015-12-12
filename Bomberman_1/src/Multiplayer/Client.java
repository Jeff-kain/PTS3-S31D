/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author jeffrey
 */
public class Client extends UnicastRemoteObject implements IRemoteClient {

    IRemoteHost service;

    Manager manager = Manager.getManager();

    public Client(int regport, String servicename, String hostservice) throws RemoteException {
        String strService = publishClient(regport, servicename);
        service = retrieveService(hostservice);
        System.out.println(hostservice);
        manager.setRemotehost(service);
        service.joingame(strService);
        
    }

    public String publishClient(int registryPort, String servicename)
            throws RemoteException {
        String str = "client not yet published";
        try {

            if (manager.getRegistry() != null) {
                UnicastRemoteObject.unexportObject(manager.getRegistry(), true);
            }

            String ip = InetAddress.getLocalHost().getHostAddress();
            // LocateRegistry.createRegistry(registryPort);

            Registry registry = LocateRegistry.createRegistry(registryPort);
            manager.setRegistry(registry);

            str = "rmi://" + ip + ":" + registryPort + "/" + servicename;
            Naming.rebind(str, this);
            // System.out.println("ip host:" + ip);
            System.out.println(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return str;
    }

    public IRemoteHost retrieveService(String hostservice) {
        IRemoteHost service_ = null;
        try {
            service_ = (IRemoteHost) Naming.lookup(hostservice);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return service_;
    }

    @Override
    public void hostKeyUpdate(int playerindex, int keycode, boolean pressed) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
