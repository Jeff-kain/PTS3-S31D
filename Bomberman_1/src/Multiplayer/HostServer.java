/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Direction;
import Game.Game;
import Game.IGameObject;
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
    ISpectate spectator = null;
    private ArrayList<IGameObject> gameObjects = new ArrayList<>();
    Manager manager = Manager.getManager();

    public HostServer(int registryPort, String servicename) throws RemoteException, UnknownHostException {
        publishHost(registryPort, servicename);
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
            manager.setNamePlayer2(service.getHostName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Client did not yet publish.");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    @Override
    public void joingame(String strService) throws RemoteException {
        retrieveClientService(strService);
        manager.setRemoteclient(service);
        System.out.println(service + " has joined");
    }

    @Override
    public void movep2c(int direction, float x, float y) throws RemoteException {
        direction = translate(direction);
        ((Player) (Game.getInstance().getAllPlayers().get(1))).moveremote(direction, x, y);

//        Game.getInstance().getPlayer1().get(0).moveremote(direction, x, y);
//        Game.getInstance().getPlayer2().get(0).moveremote(direction, x, y);
    }

    public int translate(int direction) {
        System.out.println("host: translate: " + direction);

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
    public String getClientName() throws RemoteException {
        return manager.getNamePlayer1();
    }

    @Override
    public void spectategame(String strService) throws RemoteException {
        retrieveSpectateService(strService);
        manager.setRemoteSpectate(spectator);
        System.out.println(service + " is spectating");

    }
}
