/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalserver.interfacesTest;

import database.DatabaseConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import portal.Administration;
import portal.Models.RMI.RMIClient;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

/**
 *
 * @author Rob
 */
public class ILoginTest {

    RMIClient client;
    ILogin inlog;
    DatabaseConnection dc;
    Administration admin;

    public ILoginTest() {
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
    }

    @After
    public void tearDown() {
    }

    public void loginTest() throws RemoteException, IOException {

        //Testen van een bestaand inlog naam in de database
        IPortal portal = inlog.login("Rob", "Rob");
        assertNotNull(portal);

        //Hier gebruik ik foute inlog gegevens
        portal = inlog.login("Rob", "Kees");
        assertNull(portal);

        portal = inlog.login("", "");
        assertNull(portal);
    }

    public void RegisterTest() throws RemoteException {

        
        Random rdm = new Random();
        String username = "test" + rdm.nextInt(9999);
        
        IPortal portal = inlog.Register(username, username);
        assertNotNull(portal);
        
        portal = inlog.Register("Rob", "Rob");
        assertNull(portal);

        portal = inlog.Register("", "");
        assertNull(portal);

        portal = inlog.Register("Kees", "");
        assertNull(portal);
        
        portal = inlog.Register("", "Kees");
        assertNull(portal);
        
    }
}
