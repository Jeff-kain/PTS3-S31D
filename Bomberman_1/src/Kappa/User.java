/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kappa;

import static Kappa.Reader.sChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffrey
 */
public class User extends Thread {

    public User() {

    }

    public void Run() {
        try {

            ObjectInputStream ois
                    = new ObjectInputStream(sChannel.socket().getInputStream());

            //Positie p = (Positie)ois.readObject();
            CopyOnWriteArrayList s = null;
            try {
                s = (CopyOnWriteArrayList) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
            s.add(1, new Positie(11, 22, 33));
            System.out.println("Object is: '" + s + "'");
            System.out.println(s.size());

        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
