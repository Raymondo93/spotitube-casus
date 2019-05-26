package com.spotitube.app.dao.src;

import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;

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

    public Connection getConnection() throws NoDatabaseConnectionException {
        Connection connection;
        try{
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            Class.forName(prop.getProperty("driver"));
            System.out.println("jeej");
            connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            return connection;
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
        throw new NoDatabaseConnectionException("No database connection");
    }

}
