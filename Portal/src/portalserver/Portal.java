package portalserver;

import database.DatabaseConnection;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

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

        if(correctLogin(username,password)) {
            return this;
        }

        return null;
    }

    @Override
    public List<String> getGames(String username, String password) {

        if(correctLogin(username, password)) {
            return databaseConnection.getGames();
        }

        return null;
    }

    private Boolean correctLogin(String username, String password) {
        try {
            if(databaseConnection.CheckLogin(username,password)) {
                return true;
            }

            System.out.println("Username or password incorrect");

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
