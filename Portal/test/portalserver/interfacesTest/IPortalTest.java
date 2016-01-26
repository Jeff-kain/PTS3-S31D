/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalserver.interfacesTest;

import database.DatabaseConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import portal.Administration;
import portal.Models.Game;
import portal.Models.RMI.RMIClient;
import portal.Models.Score;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

/**
 *
 * @author Rob
 */
public class IPortalTest {

    RMIClient client;
    ILogin inlog;
    IPortal portal;
    DatabaseConnection dc;
    Administration admin;
    String login = "Rob";

    public IPortalTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        admin = Administration.getInstance();
        dc = DatabaseConnection.getInstance();
        client = new RMIClient();
        inlog = client.setUp();
        
        portal = inlog.login("Rob", "Rob");
    }

    @After
    public void tearDown() {
    }

    public void getGames() throws RemoteException {
        List<Game> games = portal.getGames(login, login);
        assertNotNull(games);
        
        games = portal.getGames(login, "");
        assertNotNull(games);
        
        games = portal.getGames("", login);
        assertNotNull(games);
        
        games = portal.getGames("", "");
        assertNotNull(games);
    }

    public void createLobby() throws RemoteException {

    }

    public void getLobbies() throws RemoteException {

    }

    public void getLeaderboard() throws RemoteException {
        List<Score> scores = portal.getLeaderboard(login, login, "Bomberman");
        assertNotNull(scores);
        
        scores = portal.getLeaderboard(login, login, "");
        assertNull(scores);
    }

    public void getScoresPlayer() throws RemoteException {
        
        //Score getScoresPlayer(String username, String password, String enteredName, String game)
        Score score = portal.getScoresPlayer(login, login, login, "Bomberman");
        assertNotNull(score);
        
        score = portal.getScoresPlayer(login, login, login, "");
        assertNull(score);
        
        score = portal.getScoresPlayer(login, login, "", "Bomberman");
        assertNull(score);
        
        score = portal.getScoresPlayer("", "", login, "Bomberman");
        assertNull(score);
    }
}
