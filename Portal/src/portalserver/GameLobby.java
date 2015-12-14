package portalserver;

import portal.Models.Game;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by tverv on 12-Dec-15.
 */
public class GameLobby extends UnicastRemoteObject implements IHost, IPlayer, ILobby, Serializable {

    private Game game;
    private String name;
    private String password;

    public GameLobby(Game game, String name, String password) throws RemoteException {
        this.game = game;
        this.name = name;
        this.password = password;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    //@Override
    public String toString() {
        return name;
    }

}
