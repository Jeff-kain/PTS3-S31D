package portalserver.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by tverv on 12-Dec-15.
 */
public interface ILogin extends Remote{
    /**
     * Naam en password moeten tussen de 2 en 12 characters en kunenn niet leeg zijn
     * @param username naam van de gebruiker
     * @param password wachtwoord
     * @return IPortal als de combinatie kloppend is en null als de combinatie niet
     * bekend is bij de database
     * @throws RemoteException 
     */
    IPortal login(String username, String password) throws RemoteException;
    
    /**
     * Naam en password moeten tussen de 2 en 12 characters en kunenn niet leeg zijn
     * @param username naam van de gebruiker
     * @param password wachtwoord
     * @return IPortal als de username uniek is in de database en registreren is gelukt
     * returnt null als de registratie niet is voltooid
     * @throws RemoteException 
     */
    IPortal Register(String username, String password) throws RemoteException;
}
