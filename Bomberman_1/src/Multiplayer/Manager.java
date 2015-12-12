/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.rmi.registry.Registry;

/**
 *
 * @author jeffrey
 */
public class Manager {

    static Manager manager;
    IRemoteHost remotehost = null;

    Registry registry;

    public Manager() {

    }

    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setRemotehost(IRemoteHost remotehost) {
        this.remotehost = remotehost;
    }
    
    

}
