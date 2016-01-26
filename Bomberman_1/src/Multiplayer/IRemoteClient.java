/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jeffrey
 */
public interface IRemoteClient extends Remote {

    public static final int UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, BOMB = 5;

    /**
     *
     * @return the name of the hostplayer
     * @throws RemoteException
     */
    public String getHostName() throws RemoteException;

    /**
     * Move the clientplayer in a direction by (x,y) float
     *
     * @param direction
     * @param x
     * @param y
     * @throws RemoteException
     */
    public void movep2h(int direction, float x, float y) throws RemoteException;

    /**
     * Connect a spectator to a clientplayer
     * @param strService
     * @throws RemoteException
     */
    public void spectategame(String strService) throws RemoteException;

}
