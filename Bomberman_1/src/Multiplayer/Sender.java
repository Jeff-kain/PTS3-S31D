package Multiplayer;

/**
 * Created by Ferhat on 8-12-2015.
 */
/*
 * Writer
 */
import Multiplayer.HostServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender{
    ServerSocketChannel ssChannel;
   public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        String[] arguments = new String[3];
        arguments[0] = "client";
        arguments[1] = InetAddress.getLocalHost().getHostAddress();
        arguments[2] = "Rob";
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
