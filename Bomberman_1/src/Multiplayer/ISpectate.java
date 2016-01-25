/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.IGameObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author jeffrey
 */
public interface ISpectate extends Remote {

    public static final int UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, BOMB = 5;

    public String getHostName() throws RemoteException;

    public void hostKeyUpdate(int playerindex, int keycode, boolean pressed)
            throws RemoteException;

    public void tick() throws RemoteException;

    public void movep2h(int direction, float x, float y) throws RemoteException;

    public void movep2c(int direction, float x, float y) throws RemoteException;
    
    public void setCurrentObjectState(List<IGameObject> list) throws RemoteException;

}
