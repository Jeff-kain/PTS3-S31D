/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author jeffrey
 */
public class DatabaseConnection {

    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "bomberman";

    String driver = "com.mysql.jdbc.Driver";
    String username = "root";
    String password = "Retarded";

    Connection conn;

    public void connect() {

        try {
            conn = null;
            Properties props = loadDatabaseProperties();
            Class.forName(props.getProperty("jdbc.driver")).newInstance();

            conn = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.user"), props.getProperty("jdbc.pass"));

            if (conn != null) {
                System.out.println("Connected to database!");
            } else {
                System.out.println("Failed to connect to database");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Properties loadDatabaseProperties() throws IOException {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("data.properties"));
            return props;
        } catch (IOException ioe) {
            System.out.println("error while loading props: " + ioe.getMessage());
        }

        return null;
    }

    public void saveDatabaseProperties(String url, String user, String pass) throws IOException {

        Properties props = new Properties();
        props.setProperty("jdbc.url", url);
        props.setProperty("jdbc.user", user);
        props.setProperty("jdbc.pass", driver);

        FileOutputStream fos = new FileOutputStream("data.properties");
        props.store(fos, "saved?");
        System.out.println("Saved props");
        fos.close();
    }
}
