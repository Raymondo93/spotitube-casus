package com.spotitube.app.dao.src;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IPlaylistHasTrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Default
public class PlaylistHasTrackDAO implements IPlaylistHasTrackDAO {

    private IDatabaseConnection databaseConnection;

    public PlaylistHasTrackDAO() {

    }

    public boolean removeTrackFromPlaylist(int playlistId, int trackId) {
        String query = "DELETE FROM playlist_has_track WHERE playlist_id = ? AND track_id = ?;";
        return executeQuery(query, playlistId, trackId);
    }

    public boolean addTrackToPlaylist(TrackDTO dto, int playlistId) {
        String query = "INSERT INTO playlist_has_track(playlist_id, track_id) VALUES (?, ?);";
        return executeQuery(query, playlistId, dto.getId());
    }

    private boolean executeQuery(String query, int playlistId, int trackId) {
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.execute();
            return true;
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Inject public void setDatabaseConnection(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

}
