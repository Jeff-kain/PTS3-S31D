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
public class IPlayerTest {

    RMIClient client;
    ILogin inlog;
    IPortal portal;
    IHost host;
    ILobby lobby;
    IPlayer player;
    String login = "Rob";
    String hostName = "testLobby";

    public IPlayerTest() {
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
        player = lobby.joinGame("tim", "tim");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getSpectatorsTest() throws RemoteException {
        List<String> list = player.getSpectators();
        assertFalse(list.contains("Rob"));
    }
}
