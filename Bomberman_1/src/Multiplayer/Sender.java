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
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender{
    ServerSocketChannel ssChannel;
    public static void main(String[] args) throws IOException {
        System.out.println("Sender Start");


        
        HostServer host = new HostServer(1100,"host");
//        int port = 12345;
//        ssChannel.socket().bind(new InetSocketAddress(port));
//
//        Positie obj2 = new Positie(4, 5, 6);
//        while (true) {
//            SocketChannel sChannel = ssChannel.accept();
//
//            ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
//            oos.writeObject(obj2.objecten);
//            oos.close();
//
//            System.out.println("Connection ended");
//        }
    }

}
