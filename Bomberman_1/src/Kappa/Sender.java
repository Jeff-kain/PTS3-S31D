package Kappa; /**
 * Created by Ferhat on 8-12-2015.
 */
/*
 * Writer
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Sender {
    public static void main(String[] args) throws IOException {
        System.out.println("Sender Start");

        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(true);
        int port = 12345;
        ssChannel.socket().bind(new InetSocketAddress(port));

        Positie obj2 = new Positie(4,5,6);
        while (true) {
            SocketChannel sChannel = ssChannel.accept();

            ObjectOutputStream  oos = new
                    ObjectOutputStream(sChannel.socket().getOutputStream());
            oos.writeObject(obj2.objecten);
            oos.close();

            System.out.println("Connection ended");
        }
    }
}