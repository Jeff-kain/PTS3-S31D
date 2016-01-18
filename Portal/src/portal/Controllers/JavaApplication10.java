/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffrey
 */
public class JavaApplication10 implements Runnable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

//        Process p;
//        String ip = null;
//        try {
//            ip = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(JavaApplication10.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            p = Runtime.getRuntime().exec("java -jar Bomberman_1.jar localgame 192");
//        } catch (Exception e) {
//        }
//            ProcessBuilder pb = new ProcessBuilder("java -jar Bomberman_1.jar");
//            Process p1 = pb.start();
//        } catch (IOException ex) {
//            Logger.getLogger(JavaApplication10.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String argss[] = {"java", "-jar", "Bomberman_1.jar", "localgame", "192"};
        ProcessBuilder builder = new ProcessBuilder(argss);
        Process qq = builder.start();
    }

    @Override
    public void run() {
        String argss[] = {"java", "-jar", "Bomberman_1.jar", "localgame", "192"};
        ProcessBuilder builder = new ProcessBuilder(argss);
        try {
            Process qq = builder.start();
            // qq.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(JavaApplication10.class.getName()).log(Level.SEVERE, null, ex);
            try {
                builder.wait();
            } catch (InterruptedException ex1) {
                Logger.getLogger(JavaApplication10.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }
}
