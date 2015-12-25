package portalserver;

import database.DatabaseConnection;
import portal.Models.Game;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.PlatformLoggingMXBean;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public class GameLobby extends UnicastRemoteObject implements IHost, IPlayer, ILobby, Serializable {

    private User host;
    private List<User> players;
    private Game game;
    private String name;
    private String lobbypassword;
    private DatabaseConnection databaseConnection;

    public GameLobby(String username, String password, Game game, String lobbyname, String lobbypassword) throws RemoteException {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.game = game;
        this.name = lobbyname;
        this.lobbypassword = lobbypassword;
        try {
            System.out.println(username);
            System.out.println(password);
            host = databaseConnection.getUser(username, password);
            System.out.println(host.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        players = new ArrayList<>();

        joinGame(username, password);
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    public String getLobbypassword() {
        return lobbypassword;
    }

    @Override
    public IPlayer joinGame(String username, String password) {
        System.out.println("Player " + username + " joined lobby " + name);

        try {
            databaseConnection = DatabaseConnection.getInstance();
            User player = databaseConnection.getUser(username, password);

            if(players.size() == 0) {
                System.out.println("First player");
                players.add(player);
                System.out.println(players.size());
                return this;
            } else {
                for(User p: players) {
                    if(p.getName().equals(username)) {
                        System.out.println("Player exists");
                        return this;
                    } else {
                        if(players.size() < 2) {
                            players.add(player);
                            return this;
                        } else {
                            System.out.println("Lobby full");
                            return null;
                        }
                    }
                }

                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void leaveGame(String username, String password) throws RemoteException {
        try {
            databaseConnection = DatabaseConnection.getInstance();
            User player = databaseConnection.getUser(username, password);

            for(User p: players) {
                if(p.getName() == username) {
                    players.remove(p);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getPlayers() throws RemoteException {
        System.out.println("Host: " + host.getName());
        System.out.println("getPlayers() " + players.size());
        List<String> playerNames = new ArrayList<>();

        for (User player: players) {
            System.out.println("Player " + player.getName());
            playerNames.add(player.getName());
        }

        return playerNames;
    }
}
