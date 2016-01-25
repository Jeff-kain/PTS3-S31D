package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 14-Dec-15.
 */
public interface IPlayer extends Remote{
    List<String> getPlayers() throws RemoteException;

    List<String> getSpectators() throws RemoteException;
}
