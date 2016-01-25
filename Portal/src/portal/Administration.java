package portal;

import portal.Models.Game;
import portalserver.interfaces.*;

/**
 * Created by tverv on 12-Dec-15.
 */
public class Administration {
    private static Administration instance;

    private String username;
    private String password;
    private ILogin login;
    private IPortal portal;
    private Game selectedGame;
    private Game selectedGameLeaderboard;
    private ILobby selectedLobby;
    private IHost hostedLobby;
    private ISpectator spectatingLobby;

    private Administration() {

    }

    public static Administration getInstance() {
        return instance == null ? instance = new Administration() : instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ILogin getLogin() {
        return login;
    }

    public void setLogin(ILogin login) {
        this.login = login;
    }

    public IPortal getPortal() {
        return portal;
    }

    public void setPortal(IPortal portal) {
        this.portal = portal;
    }

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGameID(Game selectedGame) {
        this.selectedGame = selectedGame;
    }
    
    public Game getSelectedGameLeaderboard() {
        return selectedGameLeaderboard;
    }

    public void setSelectedGameLeaderboardID(Game selectedGame) {
        this.selectedGameLeaderboard = selectedGame;
    }

    public ILobby getSelectedLobby() {
        return selectedLobby;
    }

    public void setSelectedLobby(ILobby selectedLobby) {
        this.selectedLobby = selectedLobby;
    }

    public IHost getHostedLobby() { return hostedLobby;  }

    public void setHostedLobby(IHost hostedLobby) { this.hostedLobby = hostedLobby; }

    public ISpectator getSpectatingLobby() { return spectatingLobby; }

    public void setSpectatingLobby(ISpectator spectatingLobby) { this.spectatingLobby = spectatingLobby; }
}
