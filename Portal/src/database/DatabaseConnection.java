/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Rob
 */
public class DatabaseConnection {
    
    private Properties props;
    private Connection conn;
    
    private String url;
    private String username;
    private String password;
    
    public void DatabaseConnection() throws SQLException, FileNotFoundException, IOException  {
        
//        props = new Properties();
//	InputStream input = null;
//	try {
//		input = new FileInputStream("/config.properties");
//
//		// load a properties file
//		props.load(input);

//		// get the property value and print it out
//		System.out.println(props.getProperty("url"));
//		System.out.println(props.getProperty("username"));
//                System.out.println(props.getProperty("password"));
                
//                url = props.getProperty("url");
//                username = props.getProperty("url");
//                password = props.getProperty("password");
//                
        url="jdbc:mysql//192.168.15.50:1521/fhictora";
        username="dbi323344";
        password="R0l0azuZdy";

//	} catch (IOException ex) {
//		ex.printStackTrace();
//	} finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
    
    public boolean open() {
        try {
        conn = DriverManager.getConnection(url, username, password);
        return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean close() {
        try {
            conn.close();
            conn = null;
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
