package com.spotitube.app.dao.src;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.PlaylistException;
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

    @Inject
    public PlaylistDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    /**
     *  {@inheritDoc}
     */
    public List<IPlaylistModel> getPlaylists() throws PlaylistException {
        String query = "select  playlist.id, playlist.name, playlist.owner, sum(track.duration) as playtime\n" +
            "from playlist  \n" +
            "\tleft join playlist_has_track on playlist.id = playlist_has_track.playlist_id\n" +
            "\tleft join track on track.id = playlist_has_track.track_id\n" +
            "group by playlist.id";
        List<IPlaylistModel> playlists = new ArrayList<>();
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
            throw new PlaylistException("Error while fetching playlists");
        }
    }


    /**
     *  {@inheritDoc}
     */
    public void addPlaylistToDatabase(PlaylistDTO dto, String token) throws PlaylistException {
        String queryPlaylist = "INSERT INTO playlist(name, owner) SELECT ?, username FROM user WHERE token = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(queryPlaylist);
        ) {
            statement.setString(1, dto.getName());
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new PlaylistException("Error while saving playlist " + dto.getName());
        }
    }

    /**
     *  {@inheritDoc}
     */
    public void updatePlaylistNameInDatabase(PlaylistDTO dto) throws PlaylistException {
        String query = "UPDATE playlist SET name = ? WHERE id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getId());
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new PlaylistException("Error while updating playlist " + dto.getName());
        }
    }

    public void deletePlaylistFromDatabase(int playlistId) throws PlaylistException {
        String query = "DELETE FROM playlist WHERE id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, playlistId);
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new PlaylistException("Error while deleting playlist " + playlistId);
        }
    }
}
