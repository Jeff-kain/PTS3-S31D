package portalserver;

import database.DatabaseConnection;
import portal.Models.Game;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public class GameLobby extends UnicastRemoteObject implements IHost, IPlayer, ILobby, Serializable {

    private List<User> players;
    private Game game;
    private String name;
    private String password;
    private DatabaseConnection databaseConnection;

    public GameLobby(Game game, String name, String password) throws RemoteException {
        this.game = game;
        this.name = name;
        this.password = password;

        players = new ArrayList<>();
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

    @Override
    public IPlayer joinGame(String username, String password) {
        try {
            databaseConnection = DatabaseConnection.getInstance();
            if(players.size() < 2) {
                players.add(databaseConnection.getUser(username, password));
                return this;
            }

            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
