package com.spotitube.app.dao.src;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Default
public class UserDAO implements IUserDAO {

    private IDatabaseConnection databaseConnection;

    @Inject
    public UserDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void loginUser(UserLoginDTO dto) throws UserOrPasswordFailException {
        String query = "SELECT 1 FROM `user` WHERE username = ? AND password = ?;";
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setString(1, dto.getUser());
            statement.setString(2, dto.getPassword());
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new UserOrPasswordFailException("No user found on " + dto.getUser());
        }
    }

    public void saveUserToken(UserLoginResponseDTO dto) throws UserTokenException {
        String query = "UPDATE user SET token = ? WHERE username = ?";
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, dto.getToken());
            statement.setString(2, dto.getUser());
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new UserTokenException("Failed to save user token of user" + dto.getUser());
        }
    }

    public void isAuthorized(String userToken) throws NotAuthorizedException {
        String query = "SELECT 1 from `user` WHERE token = ?;";
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, userToken);
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new NotAuthorizedException("User is not authorized");
        }
    }
}
