package portalserver;

import database.DatabaseConnection;
import portal.Models.Game;
import portal.Models.Score;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public class Portal extends UnicastRemoteObject implements ILogin, IPortal {

    private List<GameLobby> lobbies;
    private DatabaseConnection databaseConnection;

    protected Portal() throws RemoteException {
        try {
            lobbies = new ArrayList<>();
            databaseConnection = DatabaseConnection.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IPortal login(String username, String password) throws RemoteException  {

        if(correctLogin(username,password)) {
            return this;
        }

        return null;
    }

    @Override
    public List<Game> getGames(String username, String password) throws RemoteException  {

        if(correctLogin(username, password)) {
            return databaseConnection.getGames();
        }

        return null;
    }

    @Override
    public IHost createLobby(String username, String password, Game game, String lobbyName, String lobbyPassword) throws RemoteException  {
        if(correctLogin(username, password)) {
            try {
                GameLobby lobby = new GameLobby(username, password, game, lobbyName, lobbyPassword);
                lobbies.add(lobby);
                System.out.println("Added lobby for the game " + game.getName() + " with the name " + lobbyName);
                return lobby;
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public List<ILobby> getLobbies(String username, String password, Game game) throws RemoteException {
        System.out.println("Foo");

        if(correctLogin(username, password)) {
            List<ILobby> gameLobbies = new ArrayList<>();

            for(GameLobby lobby: lobbies) {
                if(lobby.getGame().getId() == game.getId()) {
                    System.out.println(lobby.toString());
                    gameLobbies.add(lobby);
                }
            }

            return gameLobbies;
        }

        return null;
    }

    private Boolean correctLogin(String username, String password) throws RemoteException  {
        if (databaseConnection.CheckLogin(username, password)) {
            return true;
        }

        System.out.println("Username or password incorrect");
        return false;
    }

    @Override
    public List<Score> getLeaderboard(String username, String password, String game) throws RemoteException {
        if(databaseConnection.CheckLogin(username,password)) {
            List<Score> leaderboard = databaseConnection.getLeaderboard(game);
            Collections.sort(leaderboard, Score.ScoreComparator.reversed());
            return leaderboard;
        }

        System.out.println("Username or password incorrect");
        return null;
    }
    
    @Override
    public Score getScoresPlayer(String username, String password, String enteredName, String game) throws RemoteException {
        if (databaseConnection.CheckLogin(username, password)) {
            return databaseConnection.getScoresPlayer(enteredName, game);
        }

        System.out.println("Username or password incorrect");
        return null;
    }
}
