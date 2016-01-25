package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 14-Dec-15.
 */
public interface ILobby extends Remote{

    IPlayer joinGame(String username, String password) throws RemoteException;

    IPlayer joinGame(String username, String password, String clientIp) throws RemoteException;

    ISpectator spectateGame(String username, String password) throws RemoteException;

    void leaveGame(String username, String password) throws RemoteException;

    String getName() throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    List<String> getSpectators() throws RemoteException;

    Boolean getGameStarted() throws RemoteException;

    String getHostIp() throws RemoteException;
}
