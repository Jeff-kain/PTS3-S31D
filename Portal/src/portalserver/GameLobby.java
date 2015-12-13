package portalserver;

import portalserver.interfaces.IHost;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by tverv on 12-Dec-15.
 */
public class GameLobby extends UnicastRemoteObject implements IHost {
    protected GameLobby() throws RemoteException {
    }
}
