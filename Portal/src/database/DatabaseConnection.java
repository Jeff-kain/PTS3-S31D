/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import portal.Models.Game;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.List;

import javafx.collections.FXCollections;
import portal.Models.Score;
import portalserver.User;

/**
 *
 * @author Rob
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance = null;
    private Properties props;
    private Connection conn;
    
    private String url;
    private String username;
    private String password;
    
    private DatabaseConnection() throws FileNotFoundException, IOException {
        //db = new DatabaseConnection();
        props = new Properties();
	    InputStream input = null;
        File file = new File("./src/database/config.properties");
        
        try {
            input = new FileInputStream(file);

            // load a properties file
            props.load(input);

            // get the property value and print it out
            System.out.println(props.getProperty("url"));

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
        if(instance != null) {
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
    
    public boolean TestConnection(){
        boolean isOpen = open();
        close();
        return isOpen;
    }
    
    public boolean CheckLogin(String username, String password) throws SQLException {
        
        try {
            boolean isOpen = open();
            
            ResultSet rs;
            Statement stat = conn.createStatement();
            
            String query = "SELECT * FROM USERS WHERE NAME ='" + username + "' AND PASSWORD = '" + password + "';";
            
            stat = conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            return rs.first();
        }
        finally{            
            close();
        }
    }

    public User getUser(String username, String password) throws SQLException {
        System.out.println("getUser()");
        boolean isOpen = open();

        System.out.println(username);
        System.out.println(password);

        ResultSet rs;
        Statement stat;
        String query = "SELECT * FROM USERS WHERE NAME ='" + username + "' AND PASSWORD = '" + password + "';";

        stat = conn.prepareStatement(query);
        rs = stat.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            System.out.println("User" + id + " - " + name);

            return new User(id, name);
        }

        System.out.println("Beetje jammer dit.");
        System.out.println("inderdaad");
        return null;
    }
    
        public List<Game> getGames() {
        List<Game> games = new ArrayList<>();

        boolean isOpen = open();
        System.out.println(isOpen);

        try {
            ResultSet rs;
            Statement stat = null;

            String query = "select * from GAMES";

            stat = conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");

                Game game = new Game(id, name, description);

                games.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }
    
    public List<Score> getLeaderboard(String game) throws SQLException {
        boolean isOpen = open();

        List<Score> leaderboard = new ArrayList<>();
        ResultSet rs;
        Statement stat;

        if(isOpen) {
            
            String query = "SELECT lb.Wins, lb.losses, u.Name FROM users u, leaderboard lb, games g WHERE u.id = lb.id_user AND g.id = lb.id_Game  AND g.Name = '" + game + "'";

            stat = conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            while (rs.next()) {
                int wins = rs.getInt("Wins");
                int losses = rs.getInt("Losses");
                String name = rs.getString("Name");
                        
                double totalGames = (double) wins + losses;
                double winratio = (wins / totalGames) * 100;
                winratio = Math.round(winratio);
        
                leaderboard.add(new Score(winratio, wins, losses, name));
            }
            
            return leaderboard;
        }

        return null;
    }
    
    public Score getScoresPlayer(String enteredName, String game) throws SQLException {
        boolean isOpen = open();

        Score s = null;
        ResultSet rs;
        Statement stat;

        if(isOpen) {
            
            String query = "SELECT lb.Wins, lb.losses FROM users u, leaderboard lb, games g WHERE u.id = lb.id_user AND g.id = lb.id_Game AND u.Name = '" + enteredName + "'  AND g.Name = '" + game + "'";;

            stat = conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            while (rs.next()) {
                int wins = rs.getInt("Wins");
                int losses = rs.getInt("Losses");
                String name = enteredName;
                double totalGames = (double) wins + losses;
                double winratio = (wins / totalGames) * 100;
                winratio = Math.round(winratio);
                                
                s = new Score(winratio, wins, losses, name);
            }
            
            return s;
        }

        return null;
    }
}
