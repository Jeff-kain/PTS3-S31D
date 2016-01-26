package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 14-Dec-15.
 */
public interface ILobby extends Remote{

    /**
     * Joinen van een ILobby die terug word gegeven door de portal als client
     * @param username
     * @param password
     * @return IPlayer object voor de clients aan de host
     * @throws RemoteException 
     */
    IPlayer joinGame(String username, String password) throws RemoteException;

    /**
     * Joinen van een ILobby die terug word gegeven door de portal als client
     * @param username
     * @param password
     * @param clientIp
     * @return IPlayer object voor de clients aan de host
     * @throws RemoteException 
     */
    IPlayer joinGame(String username, String password, String clientIp) throws RemoteException;

    /**
     * Joinen van een ILobby die terug word gegeven door de portal als spectator
     * @param username
     * @param password
     * @return ISpectator object voor de spectator aan de game
     * @throws RemoteException 
     */
    ISpectator spectateGame(String username, String password) throws RemoteException;

    /**
     * verlaat de game voor de user
     * @param username
     * @param password
     * @throws RemoteException 
     */
    void leaveGame(String username, String password) throws RemoteException;

    /**
     * 
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
}
