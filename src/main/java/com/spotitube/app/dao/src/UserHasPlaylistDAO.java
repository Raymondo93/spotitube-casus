package com.spotitube.app.dao.src;

import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.PlaylistModel;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class UserHasPlaylistDAO implements IUserHasPlaylistDAO {

    @Inject IDatabaseConnection databaseConnection;

    public boolean addPlaylistToUser(IPlaylistModel playlistModel, String token){
        String query = "INSERT INTO user_has_playlist SELECT username, ? FROM user WHERE token = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setInt(1, playlistModel.getId());
            statement.setString(2, token);
            statement.execute();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<IPlaylistModel> getPlaylistsOfUser(String token) {
        // TODO -> create query
        String query = " ";
        List<IPlaylistModel> playlists = new ArrayList<>();
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery()
        ) {
            if(!set.first()) {
                return playlists;
            }
            while(set.next()) {
                playlists.add(new PlaylistModel(set.getInt("id"), set.getString("name"),
                    set.getString("owner"), set.getInt("playlistLength")));
            }
            return playlists;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public boolean deletePlaylistFromUser(int playlistId, String token) {
        String query = "DELETE FROM user_has_playlist WHERE username = (SELECT username FROM user WHERE token = ?) AND playlist_id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setString(1, token);
            statement.setInt(2, playlistId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
