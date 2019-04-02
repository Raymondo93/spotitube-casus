package com.spotitube.app.dao;

import com.spotitube.app.exceptions.NoDatabaseConnectionException;

import java.sql.Connection;

public interface IDatabaseConnection {

    Connection getConnection() throws NoDatabaseConnectionException;
}
