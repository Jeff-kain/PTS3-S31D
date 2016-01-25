/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author jeffrey
 */
public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private Properties props;
    private Connection conn;

    private String url;
    private String username;
    private String password;

    private DatabaseConnection() throws IOException {
        props = new Properties();
        InputStream input = null;
        File file = new File("config.properties");

        try {
            input = new FileInputStream(file);

            // load a properties file
            props.load(input);

            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DatabaseConnection getInstance() throws IOException {
        if (instance != null) {
            return instance;
        } else {
            instance = new DatabaseConnection();
            return instance;
        }
    }

    public boolean open() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public boolean close() {
        try {
            conn.close();
            conn = null;
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public void updateLeaderboard(String userName, boolean wonGame) throws SQLException {

        ResultSet rs;
        PreparedStatement stat;
        String query;

        try {
            boolean isOpen = open();
            if (isOpen) {
                if (wonGame) {

//                query = "UPDATE Leaderboard lb, Users u, Games g " +
//                                "SET lb.Wins = lb.Wins + 1" +
//                "WHERE lb.id_User = u.id AND g.id = lb.id_game AND u.Name = ? ";
                    query = "UPDATE leaderboard lb, users u, games g SET lb.Wins = lb.Wins + 1 WHERE lb.Id_User = u.Id AND g.Id = lb.Id_Game AND u.Name ='" + userName + "';";
//                    query = "UPDATE Leaderboard lb, Users u, Games g "+ 
//                            "SET lb.Wins = lb.Wins + 1 " + 
//                            "WHERE lb.id_User = u.id AND g.id = lb.id_game AND u.Name = 'Jeffrey'";
                } else {
                    query = "UPDATE leaderboard lb, users u, games g SET lb.Losses = lb.Losses + 1 WHERE lb.Id_User = u.Id AND g.Id = lb.Id_Game AND u.Name ='" + userName + "';";
//
//                    query = "UPDATE Leaderboard lb, Users u, Games g "
//                            + "SET lb.Losses = lb.losses + 1 "
//                            + "WHERE lb.id_User = u.id AND g.id = lb.id_game AND u.Name = ? ";
                }
                stat = conn.prepareStatement(query);
                System.out.println("Update:"+userName);
                stat.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
