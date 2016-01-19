/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author jeffrey
 */
public class Reader {

   

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        String[] arguments = new String[3];
        arguments[0] = "host";
        arguments[1] = InetAddress.getLocalHost().getHostAddress();
        arguments[2] = "Jeffrey";
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
//        }
}


