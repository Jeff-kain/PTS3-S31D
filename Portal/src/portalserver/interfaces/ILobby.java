package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by tverv on 14-Dec-15.
 */
public interface ILobby extends Remote{

    IPlayer joinGame(String username, String password) throws RemoteException;

    String getName() throws RemoteException;
}
