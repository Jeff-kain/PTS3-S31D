package portalserver.interfaces;

import javafx.collections.ObservableList;
import portal.Models.*;
import portalserver.GameLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface IPortal extends Remote {

    /**
     * Haalt de lijst van games op voor de portal
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @return Lijst van alle games die er zijn of null als username en password combinatie
     * niet klopt
     * @throws RemoteException 
     */
    List<Game> getGames(String username, String password) throws RemoteException;

    /**
     * Aanmaken van een lobby
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @param game game waarvoor lobby word aangemaakt
     * @param lobbyName naam van de lobby
     * @param lobbyPassword 
     * @param hostIp 
     * @return IHost als username en password klopt en null als het aanmaken van een 
     * lobby is mislukt
     * @throws RemoteException 
     */
    IHost createLobby(String username, String password, Game game, String lobbyName, String lobbyPassword, String hostIp) throws RemoteException;

    /**
     * beindigen van een lobby
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @param lobby Lobby object van host
     * Closed de lobby voor alle players en spectators door de host
     * @throws RemoteException 
     */
    void closeLobby(String username, String password, IHost lobby) throws RemoteException;

    /**
     * Op halen van alle lobbies voor een game
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @param game gekozen game voor de lobbies
     * @return lijst met ILobbies
     * @throws RemoteException 
     */
    List<ILobby> getLobbies(String username, String password, Game game) throws RemoteException;
    
    /**
     * ophalen van de score voor een gekozen game
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @param game gekozen game voor de leaderboard
     * @return lijst met score objects
     * @throws RemoteException 
     */
    List<Score> getLeaderboard(String username, String password, String game) throws RemoteException;
    
    /**
     * ophalen van score voor specefieke spelers bij een game
     * @param username gebruikersnaam na het inloggen
     * @param password wachtwoord van de ingelogd gebruiker
     * @param enteredName naam van de speler
     * @param game gekozen game voor de leaderboard
     * @return score van de gekozen player
     * @throws RemoteException 
     */
    Score getScoresPlayer(String username, String password, String enteredName, String game) throws RemoteException;
}
