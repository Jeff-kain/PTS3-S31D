/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalserver.interfacesTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import portal.Models.Game;
import portal.Models.RMI.RMIClient;
import portalserver.interfaces.IHost;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPlayer;
import portalserver.interfaces.IPortal;

/**
 *
 * @author Rob
 */
public class ILobbyTest {

    RMIClient client;
    ILogin inlog;
    IPortal portal;
    IHost host;
    ILobby lobby;
    String login = "Rob";
    String hostName = "testLobby";

    public ILobbyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws RemoteException, UnknownHostException {
        client = new RMIClient();
        inlog = client.setUp();
        portal = inlog.login("Rob", "Rob");
        host = portal.createLobby("rob", "rob", new Game(1, "Bomberman", "Bomberman"), "testLobby", "", InetAddress.getLocalHost().getHostAddress());
        List<ILobby> lobbies = portal.getLobbies("rob", "rob", new Game(1, "Bomberman", "Bomberman"));
        lobby = lobbies.get(0);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void joinGameTest() throws RemoteException {
        IPlayer player = lobby.joinGame("tim", "tim");
        List<String> list = player.getPlayers();
        assertTrue(list.contains("Tim"));
    }

    @Test
    public void leaveGameTest() throws RemoteException {
        lobby.leaveGame("tim", "tim");
        List<String> list = host.getPlayers();
        assertFalse(list.contains("Tim"));
    }

    @Test
    public void getNameTest() throws RemoteException {
        String name = lobby.getName();
        assertEquals(name, hostName);
    }

    @Test
    public void getPlayersTest() throws RemoteException {
        List<String> list = lobby.getPlayers();
        assertTrue(list.contains("Rob"));   
    }
    
    @Test
    public void getSpectatorsTest() throws RemoteException {
        List<String> list = lobby.getSpectators();
        assertFalse(list.contains("Rob"));  
    }
}
