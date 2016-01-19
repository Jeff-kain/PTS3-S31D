package Kappa;

/**
 * Created by Ferhat on 8-12-2015.
 */

import javafx.geometry.Pos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

    static SocketChannel sChannel;

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        String[] arguments = new String[5];
        arguments[0] = "spectate";
        arguments[1] = InetAddress.getLocalHost().getHostAddress();
        arguments[2] = "Jeffrey";
        arguments[3] = InetAddress.getLocalHost().getHostAddress();
        arguments[4] = InetAddress.getLocalHost().getHostAddress();
        bomberman.Bomberman.main(arguments);
//        System.out.println("Receiver Start");
//
//        sChannel = SocketChannel.open();
//        sChannel.configureBlocking(true);
//            if (sChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 12345))) {
//                User user = new User();
//            }
//        System.out.println("End Receiver");
    }

}
