package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 14-Dec-15.
 */
public interface IPlayer extends Remote{
    /**
     * ophalen van alle gejoinde spelers
     * @return Alle spelers in de lobby
     * @throws RemoteException 
     */
    List<String> getPlayers() throws RemoteException;

    /**
     * ophalen van alle gejoinde spectators
     * @return Alle spectators in de lobby
     * @throws RemoteException 
     */
    List<String> getSpectators() throws RemoteException;
}
