package com.spotitube.app.dao.src;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class UserDAO implements IUserDAO {

    private IDatabaseConnection databaseConnection;

    @Inject
    public UserDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public boolean loginUser(UserLoginDTO dto) throws UserOrPasswordFailException {
        String query = "SELECT 1 FROM user WHERE username = ? AND password = ?;";
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setString(1, dto.getUser());
            statement.setString(2, dto.getPassword());
            ResultSet set = statement.executeQuery();
            if(set.first() && set.getInt(1) == 1) {
               return true;
            }
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        throw new UserOrPasswordFailException("No user found on " + dto.getUser());
    }

    public void saveUserToken(UserLoginResponseDTO dto) {
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
        }
    }
}
