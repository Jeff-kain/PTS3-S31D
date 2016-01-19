package portalserver.interfaces;

import javafx.collections.ObservableList;
import portal.Models.*;
import portalserver.GameLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IPortal extends Remote {

    List<Game> getGames(String username, String password) throws RemoteException;

    IHost createLobby(String username, String password, Game game, String lobbyName, String lobbyPassword, String hostIp) throws RemoteException;

    List<ILobby> getLobbies(String username, String password, Game game) throws RemoteException;
    
    List<Score> getLeaderboard(String username, String password, String game) throws RemoteException;
    
    Score getScoresPlayer(String username, String password, String enteredName, String game) throws RemoteException;
}
