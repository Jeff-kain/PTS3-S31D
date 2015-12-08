package Kappa; /**
 * Created by Ferhat on 8-12-2015.
 */

import javafx.geometry.Pos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;

public class Reader {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        System.out.println("Receiver Start");

        SocketChannel sChannel = SocketChannel.open();
        sChannel.configureBlocking(true);
        if (sChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 12345))) {

            ObjectInputStream ois =
                    new ObjectInputStream(sChannel.socket().getInputStream());

            //Positie p = (Positie)ois.readObject();
            CopyOnWriteArrayList s = (CopyOnWriteArrayList)ois.readObject();
            s.add(1, new Positie(11,22,33));
            System.out.println("Object is: '" + s + "'");
            System.out.println(s.size());
        }

        System.out.println("End Receiver");
    }
}

