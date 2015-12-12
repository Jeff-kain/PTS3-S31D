package portal.Models.RMI;

import portalserver.interfaces.ILogin;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by tverv on 12-Dec-15.
 */
public class RMIClient {
    private Registry registry;
    private ILogin login;

    private static String bindingName = "portal";
    private final String ipAddress = "127.0.0.1";
    private int portNumber = 1099;

    public ILogin setUp() {
        System.out.println("IP Address: " + ipAddress);
        System.out.println("Port number " + portNumber);

        // Locate registry
        try {
            registry = LocateRegistry.getRegistry(ipAddress,portNumber);
            System.out.println("Firstname: " + registry.list()[0]);
        } catch (RemoteException ex) {
            System.out.println("Cannot locate registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result of locating the registry
        if (registry != null) {
            System.out.println("Registry located");
        } else {
            System.out.println("Cannot locate registry");
            System.out.println("Registry is null pointer");
        }

        // Bind portal using registry
        try {
            if (registry != null) {
                login = (ILogin)registry.lookup(bindingName);
            }
        } catch (RemoteException ex) {
            System.out.println("Cannot bind effectenbeurs");
            System.out.println("RemoteException: " + ex.getMessage());
            login = null;
        } catch (NotBoundException ex) {
            System.out.println("Cannot bind effectenbeurs");
            System.out.println("NotBoundException: " + ex.getMessage());
            login = null;
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException: " + ex.getMessage());
            login = null;
        }

        // Print result of binding the portal
        if (login != null) {
            System.out.println("Portal bound");
        } else {
            System.out.println("Portal is a null pointer");
        }

        // Test RMI connection
        if (login != null) {
            return login;
        } else {
            return null;
        }
    }
}
