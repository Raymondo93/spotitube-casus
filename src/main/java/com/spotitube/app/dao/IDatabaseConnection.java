package com.spotitube.app.dao;

import java.sql.Connection;

public interface IDatabaseConnection {

    Connection getConnection();
}
