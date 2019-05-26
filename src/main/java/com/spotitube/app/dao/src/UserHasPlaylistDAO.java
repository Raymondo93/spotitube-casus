package com.spotitube.app.dao.src;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.UserHasPlaylistException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Default
public class UserHasPlaylistDAO implements IUserHasPlaylistDAO {

    private IDatabaseConnection databaseConnection;

    @Inject
    public UserHasPlaylistDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void addPlaylistToUser(PlaylistDTO dto, String token) throws UserHasPlaylistException {
        String query = "INSERT INTO user_has_playlist (username, playlist_id) SELECT username, (SELECT id FROM playlist WHERE name = ?) FROM user WHERE token = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setString(1, dto.getName());
            statement.setString(2, token);
            statement.execute();
        } catch(SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new UserHasPlaylistException("Error while saving playlist " + dto.getName() + " to user");
        }
    }


    public void deletePlaylistFromUser(int playlistId, String token) throws UserHasPlaylistException {
        String query = "DELETE FROM user_has_playlist WHERE username = (SELECT username FROM `user` WHERE token = ?) AND playlist_id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setString(1, token);
            statement.setInt(2, playlistId);
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new UserHasPlaylistException("Error while deleting playlist " + playlistId);
        }
    }

}
