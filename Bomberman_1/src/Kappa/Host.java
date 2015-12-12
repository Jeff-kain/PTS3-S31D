/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kappa;

import Multiplayer.Sender;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffrey
 */
public class Host extends Thread {

    ServerSocketChannel ssChannel;
    SocketChannel sChannel;
    Positie obj2;

    public Host() throws IOException {

        this.start();
    }

    public void run() {
        ServerSocketChannel ssChannel = null;
        try {
            ssChannel = ServerSocketChannel.open();
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ssChannel.configureBlocking(true);
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
        int port = 12345;
        try {
            ssChannel.socket().bind(new InetSocketAddress(port));
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        Positie obj2 = new Positie(4, 5, 6);
        while (true) {
            try {
                sChannel = ssChannel.accept();
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }

            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                oos.writeObject(obj2.objecten);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Connection ended");
        }
    }
}
