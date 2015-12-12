package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface ILogin extends Remote{
    IPortal login(String username, String password) throws RemoteException;
}
