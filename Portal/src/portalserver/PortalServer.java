package portalserver;

import portalserver.interfaces.ILogin;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PortalServer {

    private static final int portNumber = 1099;
    private static final String bindingName = "portal";

    private Registry registry;
    private Portal portal;

    public PortalServer() {
        try {
            // Creating a new instance of the Portal.
            portal = new Portal();
            System.out.println("Portal created \n");
        } catch (RemoteException ex) {
            System.out.println("Cannot create portal");
            System.out.println("RemoteException: " + ex.getMessage());
        }

        try {
            // Creating registry
            System.out.println("Creating the registry...");
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Cannot create registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
        }

        try {
            // Binding the portal
            registry.rebind(bindingName, portal);
        } catch (RemoteException e) {
            System.out.println("Server: Cannot bind MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Portnumber: " + portNumber);
        System.out.println("BindingName: " + bindingName);

        System.out.println("Server started.\n");

        PortalServer server = new PortalServer();
    }
}
