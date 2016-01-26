/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Direction;
import Game.Game;
import Game.Keyset;
import Game.Player;
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

    IRemoteHost service = null;
    ISpectate spectator = null;

    Manager manager = Manager.getManager();

    public Client(int regport, String servicename, String hostservice) throws RemoteException {
        String strService = publishClient(regport, servicename);
        while(service == null) {
            service = retrieveService(hostservice);
        }
        // service = retrieveService("rmi://" + "145.93.64.173" + ":" + 1090 + "/host");
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
            manager.setNamePlayer1(service_.getClientName());

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
    public void movep2h(int direction, float x, float y) throws RemoteException {
        direction = translate(direction);
        ((Player) (Game.getInstance().getAllPlayers().get(0))).moveremote(direction, x, y);
    }

    public int translate(int direction) {
        System.out.println("client: translate: " + direction);

        if (direction == IRemoteClient.UP) {
            return Keyset.REMUP;
        } else if (direction == IRemoteClient.LEFT) {
            return Keyset.REMLEFT;
        } else if (direction == IRemoteClient.DOWN) {
            return Keyset.REMDOWN;
        } else if (direction == IRemoteClient.RIGHT) {
            return Keyset.REMRIGHT;
        } else if (direction == IRemoteClient.BOMB) {
            return Keyset.REMBOMB;
        }
        System.out.println("false translate");
        return 0;
    }

    @Override
    public String getHostName() throws RemoteException {
        return manager.getNamePlayer2();
    }

    public void retrieveSpectateService(String strService) {
        // System.out.println("fetch:    rmi://" + IRemoteClient.clientname +
        // ":"
        // + IRemoteClient.registryPort + "/" + IRemoteClient.servicename);
        try {

            // besser ist folgendes:
            // service = (IRemoteClient) LocateRegistry
            // .getRegistry("hostip", 1099).lookup(strService);
            spectator = (ISpectate) Naming.lookup(strService);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Client did not yet publish.");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spectategame(String strService) throws RemoteException {
        retrieveSpectateService(strService);
        manager.setRemoteSpectate(spectator);
        System.out.println(service + " is spectating");

    }

}
