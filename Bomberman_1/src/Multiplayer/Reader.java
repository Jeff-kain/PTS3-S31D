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
        public static void main(String[] args) throws IOException {
        System.out.println("Reader Start");
        String hostIP = InetAddress.getLocalHost().getHostAddress();
        String hostservice = ("rmi://" + hostIP+":"+1100+"/host");
        Client client = new Client(1090, "client", hostservice);
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
