package com.spotitube.app.dao.src;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class TrackDAO implements ITrackDAO {

    private IDatabaseConnection databaseConnection;

    @Inject
    public TrackDAO(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<TrackDTO> getAllTracksNotInPlaylist(int playlistId) {
        String query = "SELECT track.id, track.title, track.performer, track.duration, track.album, track.playcount," +
            "    track.publication_date, track.description, track.offline_available\n" +
            "FROM track left join playlist_has_track ON track.id = playlist_has_track.track_id\n" +
            "WHERE playlist_has_track.playlist_id != ? OR playlist_has_track.playlist_id is null";
        return getTracks(query, playlistId);
    }

    public List<TrackDTO> getTracksFromPlaylist(int playlistId) {
        String query = "SELECT track.id, track.title, track.performer, track.duration, track.album, track.playcount, track.publication_date, track.description, track.offline_available \n" +
            "FROM track left join playlist_has_track ON track.id = playlist_has_track.track_id\n" +
            "WHERE playlist_has_track.playlist_id = ?\n";
        return getTracks(query, playlistId);
    }

    private List<TrackDTO> getTracks(String query, int playlistId) {
        List<TrackDTO> tracks = new ArrayList<>();
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setInt(1, playlistId);
            ResultSet s = statement.executeQuery();
            while(s.next()) {
                tracks.add(new TrackDTO(s.getInt("id"), s.getString("title"),
                    s.getString("performer"), s.getInt("duration"), s.getString("album"),
                    s.getInt("playcount"), s.getString("publication_date"),
                    s.getString("description"), s.getBoolean("offline_available")));
            }
            return tracks;
        } catch (SQLException | NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return tracks;
    }

}
