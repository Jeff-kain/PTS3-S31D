package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 25-Jan-16.
 */
public interface ISpectator extends Remote {
    String getName() throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    List<String> getSpectators() throws RemoteException;

    void leaveGame(String username, String password) throws RemoteException;

    Boolean getGameStarted() throws RemoteException;

    String getHostIp() throws RemoteException;

    String getClientIp() throws RemoteException;
}
