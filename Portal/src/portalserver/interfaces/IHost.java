package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IHost extends Remote {
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
     * leaven van de game lobby mits je gejoind bent aan de Lobby
     * @param username
     * @param password
     * @throws RemoteException 
     */
    void leaveGame(String username, String password) throws  RemoteException;

    /**
     * 
     * @return true als de game gestart is, false als de game nog niet gestart is bij de host
     * @throws RemoteException 
     */
    Boolean startGame() throws RemoteException;
}
