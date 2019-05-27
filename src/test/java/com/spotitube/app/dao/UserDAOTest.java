package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;
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
        Mockito.when(set.getInt(1)).thenReturn(1);

        userDAO.loginUser(dto);
    }

    @Test
    public void loginUserExceptionTest() throws SQLException, UserOrPasswordFailException {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setPassword(PASSWORD);
        dto.setUser(USERNAME);

        Mockito.when(statement.executeQuery()).thenReturn(set);
        Mockito.when(set.getInt(1)).thenReturn(0);

        UserOrPasswordFailException exception = Assertions.assertThrows(UserOrPasswordFailException.class, () ->
            userDAO.loginUser(dto));
        Assertions.assertEquals("No user found on " + dto.getUser(), exception.getMessage());
    }

    @Test
    public void loginUserSQLExceptionTest() throws SQLException, UserOrPasswordFailException {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setPassword(PASSWORD);
        dto.setUser(USERNAME);

        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());

        UserOrPasswordFailException exception = Assertions.assertThrows(UserOrPasswordFailException.class, () ->
            userDAO.loginUser(dto));
        Assertions.assertEquals("No user found on " + dto.getUser(), exception.getMessage());
    }


    @Test
    public void saveUserTokenTest() throws SQLException, UserTokenException {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.setToken(TOKEN);
        dto.setUser(USERNAME);

        Mockito.when(statement.execute()).thenReturn(true);

        userDAO.saveUserToken(dto);
    }

    @Test
    public void saveUserTokenSQLExceptionTest() throws SQLException {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.setToken(TOKEN);
        dto.setUser(USERNAME);

        Mockito.when(statement.execute()).thenThrow(new SQLException());
        UserTokenException exception = Assertions.assertThrows(UserTokenException.class, () ->
            userDAO.saveUserToken(dto));

        Assertions.assertEquals("Failed to save user token of user " + dto.getUser(), exception.getMessage());
    }

    @Test
    public void isAuthorizedTest() throws NotAuthorizedException, SQLException {
        Mockito.when(statement.executeQuery()).thenReturn(set);
        Mockito.when(set.first()).thenReturn(true);
        Mockito.when(set.getInt(1)).thenReturn(1);

        Assertions.assertTrue(userDAO.isAuthorized(TOKEN));
    }

    @Test
    public void isAuthorizedExceptionTest() throws NotAuthorizedException, SQLException {
        Mockito.when(statement.executeQuery()).thenReturn(set);
        Mockito.when(set.first()).thenReturn(true);
        Mockito.when(set.getInt(1)).thenReturn(0);

        NotAuthorizedException exception = Assertions.assertThrows(NotAuthorizedException.class, () ->
            userDAO.isAuthorized(TOKEN));
        Assertions.assertEquals("User is not authorized", exception.getMessage());
    }


    @Test
    public void isAuthorizedSQLExceptionTest() throws NotAuthorizedException, SQLException {
        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());

        NotAuthorizedException exception = Assertions.assertThrows(NotAuthorizedException.class, () ->
            userDAO.isAuthorized(TOKEN));
        Assertions.assertEquals("User is not authorized", exception.getMessage());
    }
}