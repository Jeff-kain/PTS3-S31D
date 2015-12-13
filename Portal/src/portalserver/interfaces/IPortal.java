package portalserver.interfaces;

import javafx.collections.ObservableList;
import portal.Models.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IPortal extends Remote {

    List<String> getGames(String username, String password) throws RemoteException;

}
