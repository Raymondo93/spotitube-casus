package com.spotitube.app.dao.src;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.PlaylistModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class PlaylistDAO implements IPlaylistDAO {

    private IDatabaseConnection databaseConnection;

    /**
     *  {@inheritDoc}
     */
    public List<IPlaylistModel> getPlaylists() {
        String query = "select  playlist.id, playlist.name, playlist.owner, sum(track.duration) as playtime\n" +
            "from playlist  \n" +
            "\tinner join playlist_has_track on playlist.id = playlist_has_track.playlist_id\n" +
            "\tinner join track on track.id = playlist_has_track.track_id\n" +
            "group by playlist.id";
        List<IPlaylistModel> playlists = new ArrayList<>();
        databaseConnection = new DatabaseConnection();
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();
        ){
            while(set.next()) {
                playlists.add(new PlaylistModel(set.getInt("id"), set.getString("name"),
                    set.getString("owner"), set.getInt("playtime")));
            }
            return playlists;
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return playlists;
    }


    /**
     *  {@inheritDoc}
     */
    public boolean addPlaylistToDatabase(IPlaylistModel playlistModel, String token) {
        String query = "INSERT INTO playlist(name, owner) SELECT ?, u.username FROM user WHERE token = ?;";
        databaseConnection = new DatabaseConnection();
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, playlistModel.getName());
            statement.setString(2, token);
            statement.execute();
            return true;
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  {@inheritDoc}
     */
    public boolean updatePlaylistNameInDatabase(IPlaylistModel playlistModel) {
        String query = "UPDATE playlist SET name = ? WHERE id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, playlistModel.getName());
            statement.setInt(2, playlistModel.getId());
            statement.execute();
            return true;
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
