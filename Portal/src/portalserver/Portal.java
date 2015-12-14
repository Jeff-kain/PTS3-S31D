package portalserver;

import database.DatabaseConnection;
import portal.Models.Game;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                GameLobby lobby = new GameLobby(game, lobbyName, lobbyPassword);
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
        try {
            if(databaseConnection.CheckLogin(username,password)) {
                return true;
            }

            System.out.println("Username or password incorrect");

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
