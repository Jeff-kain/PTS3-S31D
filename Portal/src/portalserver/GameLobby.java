package portalserver;

import com.sun.org.apache.xpath.internal.operations.Bool;
import database.DatabaseConnection;
import portal.Models.Game;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    private Boolean gameStarted;
    private DatabaseConnection databaseConnection;

    private String hostIp;

    public GameLobby(String username, String password, Game game, String lobbyname, String lobbypassword, String hostIp) throws RemoteException {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.game = game;
        this.name = lobbyname;
        this.lobbypassword = lobbypassword;
        this.hostIp = hostIp;
        this.gameStarted = false;
        host = databaseConnection.getUser(username, password);

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

    public String getHostIp() {
        return hostIp;
    }

    public Boolean getGameStarted() throws RemoteException {
        return gameStarted;
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
        }
    }

    @Override
    public void leaveGame(String username, String password) throws RemoteException {
        try {
            databaseConnection = DatabaseConnection.getInstance();
            User player = databaseConnection.getUser(username, password);
            System.out.println("Player " + player.getName() + " left lobby: " + name + ".");

            System.out.println(players.size());
            players.remove(player);
            System.out.println(players.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean startGame() throws RemoteException {
        if(players.size() == 2) {
            gameStarted = true;
        }

        System.out.println(host.getName() + " started the game in lobby " + name);

        return gameStarted;
    }

    @Override
    public List<String> getPlayers() throws RemoteException {
        List<String> playerNames = new ArrayList<>();

        for (User player: players) {
            playerNames.add(player.getName());
        }

        return playerNames;
    }
}
