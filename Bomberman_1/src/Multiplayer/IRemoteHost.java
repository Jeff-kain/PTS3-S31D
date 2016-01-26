/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Direction;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jeffrey
 */
public interface IRemoteHost extends Remote {

    public static final int UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, BOMB = 5;

    /**
     *
     * @return the name of the Clientplayer
     * @throws RemoteException
     */
    public String getClientName() throws RemoteException;

    /**
     * Connect a client to the hostplayer
     *
     * @param strService
     * @throws RemoteException
     */
    public void joingame(String strService) throws RemoteException;

    /**
     * Move the hostplayer in a direction by (x,y) float
     * @param direction
     * @param x
     * @param y
     * @throws RemoteException
     */
    public void movep2c(int direction, float x, float y) throws RemoteException;

    /**
     * connect a spectator to the hostplayer
     *
     * @param strService
     * @throws RemoteException
     */
    public void spectategame(String strService) throws RemoteException;

}
