package com.spotitube.app.dao.src;

import com.spotitube.app.dao.IDatabaseConnection;

import javax.enterprise.inject.Default;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


@Default
public class DatabaseConnection implements IDatabaseConnection {

    public DatabaseConnection() {
        // Empty constructor
    }

    public Connection getConnection() {
        Connection connection = null;
        try{
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.getMessage();
        }
        return connection;
    }

}
