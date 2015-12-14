package portalserver.interfaces;

import javafx.collections.ObservableList;
import portal.Models.Game;
import portalserver.GameLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IPortal extends Remote {

    List<Game> getGames(String username, String password) throws RemoteException;

    IHost createLobby(String username, String password, Game game, String lobbyName, String lobbyPassword) throws RemoteException;

    List<String> getLobbies(String username, String password, Game game) throws RemoteException;

}
