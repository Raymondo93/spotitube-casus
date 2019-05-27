package com.spotitube.app.dao;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.TrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.TracksException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAOTest {

    private static TrackDTO trackDTO;
    private static IDatabaseConnection databaseConnection;
    private PreparedStatement statement;
    private ResultSet set;
    private static ITrackDAO trackDAO;
    private static final int PLAYLISTID = 7;

    @BeforeAll
    public static void setupAll() {
        trackDTO = new TrackDTO(1, "testTrack", "Test performer", 200, "testAlbum", 0, null, null, false);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        trackDAO = new TrackDAO(databaseConnection);
    }

    @BeforeEach
    public void setup() throws NoDatabaseConnectionException, SQLException {
        Connection connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        set = Mockito.mock(ResultSet.class);

        Mockito.when(databaseConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
    }

    @Test
    public void getAllTracksNotInPlaylistTest() throws SQLException, TracksException {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(statement.executeQuery()).thenReturn(set);
        Assertions.assertEquals(trackDAO.getAllTracksNotInPlaylist(PLAYLISTID),tracks);
    }

    @Test
    public void getTracksFromPlaylistTest() throws SQLException, TracksException {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(statement.executeQuery()).thenReturn(set);
        Assertions.assertEquals(trackDAO.getTracksFromPlaylist(PLAYLISTID), tracks);
    }

}
