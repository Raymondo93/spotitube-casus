package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOTest {

    private final static String USERNAME = "testusername";
    private final static String PASSWORD = "testpassword";
    private final static String TOKEN = "test2342545";

    private IDatabaseConnection databaseConnection;
    private IUserDAO userDAO;

    private PreparedStatement statement;
    private ResultSet set;

    @BeforeEach
    public void setup() throws NoDatabaseConnectionException, SQLException {
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        userDAO = new UserDAO(databaseConnection);
        Connection conn = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        set = Mockito.mock(ResultSet.class);

        Mockito.when(databaseConnection.getConnection()).thenReturn(conn);
        Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(statement);
    }

    @Test
    public void loginUserTest() throws SQLException, UserOrPasswordFailException {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUser(USERNAME);
        dto.setPassword(PASSWORD);

        Mockito.when(statement.executeQuery()).thenReturn(set);
        Mockito.when(set.first()).thenReturn(true);
        Mockito.when(set.getInt(Mockito.anyInt())).thenReturn(1);

        Assertions.assertTrue(userDAO.loginUser(dto));
    }

    @Test
    public void saveUserTokenTest() throws SQLException {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.setToken(TOKEN);
        dto.setUser(USERNAME);

        Mockito.when(statement.execute()).thenReturn(true);

        Assertions.assertTrue(userDAO.saveUserToken(dto));
    }
}