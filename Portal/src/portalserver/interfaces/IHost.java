package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IHost extends Remote {
    String getName() throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    List<String> getSpectators() throws RemoteException;

    void leaveGame(String username, String password) throws  RemoteException;

    Boolean startGame() throws RemoteException;
}
