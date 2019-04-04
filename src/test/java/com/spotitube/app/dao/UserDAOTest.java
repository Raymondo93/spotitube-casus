package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class UserDAOTest {

    @Mock Connection connection;
    @Mock PreparedStatement statement;
    @Mock ResultSet set;
    @Mock UserLoginDTO dto;
    @InjectMocks IDatabaseConnection databaseConnection;

    @BeforeEach
    public void setUp() {
        databaseConnection = mock(DatabaseConnection.class, RETURNS_DEEP_STUBS);
        connection = mock(Connection.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test void testLoginUser() throws UserOrPasswordFailException, NoDatabaseConnectionException {
        UserLoginDTO dto = new UserLoginDTO("admin", "welkom01");
        when(databaseConnection.getConnection()).thenReturn(connection);
        IUserDAO target = new UserDAO();
        assertTrue(target.loginUser(dto));
    }
}
