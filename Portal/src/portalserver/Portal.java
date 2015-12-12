package portalserver;

import database.DatabaseConnection;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

/**
 * Created by tverv on 12-Dec-15.
 */
public class Portal extends UnicastRemoteObject implements ILogin, IPortal {

    private DatabaseConnection databaseConnection;

    protected Portal() throws RemoteException {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IPortal login(String username, String password) {
        System.out.println("Foo");

        try {
            if(databaseConnection.CheckLogin(username,password)) {
                return this;
            }

            System.out.println("Username or password incorrect");

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
