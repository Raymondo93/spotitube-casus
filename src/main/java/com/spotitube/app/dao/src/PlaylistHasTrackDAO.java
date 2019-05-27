package com.spotitube.app.dao.src;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IPlaylistHasTrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.PlaylistHasTrackException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Default
public class PlaylistHasTrackDAO implements IPlaylistHasTrackDAO {

    private IDatabaseConnection databaseConnection;

    @Inject
    public PlaylistHasTrackDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void removeTrackFromPlaylist(int playlistId, int trackId) throws PlaylistHasTrackException {
        String query = "DELETE FROM playlist_has_track WHERE playlist_id = ? AND track_id = ?;";
        executeQuery(query, playlistId, trackId, "deleting");
    }

    public void addTrackToPlaylist(TrackDTO dto, int playlistId) throws PlaylistHasTrackException {
        String query = "INSERT INTO playlist_has_track(playlist_id, track_id) VALUES (?, ?);";
        executeQuery(query, playlistId, dto.getId(), "adding");
    }

    private void executeQuery(String query, int playlistId, int trackId, String action) throws PlaylistHasTrackException {
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.execute();
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
            throw new PlaylistHasTrackException("Error while " + action + " track of playlist");
        }
    }

}
