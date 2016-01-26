package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 25-Jan-16.
 */
public interface ISpectator extends Remote {
    
    /**
     * @return Lobby Name
     * @throws RemoteException 
     */
    String getName() throws RemoteException;

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

    /**
     * verlaat de spectator game mits je gejoind had aan de lobby
     * @param username 
     * @param password 
     * @throws RemoteException
     */
    void leaveGame(String username, String password) throws RemoteException;

    /**
     * 
     * @return true als de game gestart is, false als de game nog niet gestart is bij de host
     * @throws RemoteException 
     */
    Boolean getGameStarted() throws RemoteException;

    /**
     * 
     * @return Ip van de Host van de lobby
     * @throws RemoteException 
     */
    String getHostIp() throws RemoteException;

    /**
     * 
     * @return Ip van de Client van de lobby
     * @throws RemoteException 
     */
    String getClientIp() throws RemoteException;
}
