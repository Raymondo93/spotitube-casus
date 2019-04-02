package com.spotitube.app.exceptions;

public class NoDatabaseConnectionException extends Exception {
    public NoDatabaseConnectionException(String no_database_connection) {
        super(no_database_connection);
    }
}
