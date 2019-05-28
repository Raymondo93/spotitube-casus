package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.PlaylistHasTrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.exceptions.PlaylistHasTrackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistHasTrackDAOTest {

    private static IDatabaseConnection databaseConnection;
    private ResultSet set;
    private PreparedStatement statement;
    private static IPlaylistHasTrackDAO playlistHasTrackDAO;
    private final static int PLAYLISTID = 20;
    private final static int TRACKID = 5;
    private static PlaylistDTO playlistDTO;


    @BeforeAll
    public static void setupAll() {
        playlistDTO = new PlaylistDTO(20, "testPlaylist",true,  "true", new TrackDTO[0], 40);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        playlistHasTrackDAO = new PlaylistHasTrackDAO(databaseConnection);
    }

    @BeforeEach
    public void setup() throws NoDatabaseConnectionException, SQLException {
        Connection connection = Mockito.mock(Connection.class);
        set = Mockito.mock(ResultSet.class);
        statement = Mockito.mock(PreparedStatement.class);

        Mockito.when(databaseConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
    }

    @Test
    public void removeTrackFromPlaylistTest() throws SQLException, PlaylistHasTrackException {
        Mockito.when(statement.execute()).thenReturn(true);
        playlistHasTrackDAO.removeTrackFromPlaylist(PLAYLISTID, TRACKID);
    }

    @Test
    public void removeTrackFromPlaylistExceptionTest() throws SQLException {
        Mockito.when(statement.execute()).thenThrow(new SQLException());
        PlaylistHasTrackException exception = Assertions.assertThrows(PlaylistHasTrackException.class, () ->
            playlistHasTrackDAO.removeTrackFromPlaylist(PLAYLISTID, TRACKID));
        Assertions.assertEquals("Error while deleting track of playlist", exception.getMessage());
    }

    @Test
    public void addTrackToPlaylistTest() throws SQLException, PlaylistHasTrackException {
        TrackDTO trackDTO = Mockito.mock(TrackDTO.class);
        Mockito.when(statement.execute()).thenReturn(true);
        playlistHasTrackDAO.addTrackToPlaylist(trackDTO, PLAYLISTID);
    }

    @Test
    public void addTrackFromPlaylistExceptionTest() throws SQLException {
        Mockito.when(statement.execute()).thenThrow(new SQLException());
        TrackDTO trackDTO = Mockito.mock(TrackDTO.class);
        PlaylistHasTrackException exception = Assertions.assertThrows(PlaylistHasTrackException.class, () ->
            playlistHasTrackDAO.addTrackToPlaylist(trackDTO, TRACKID));
        Assertions.assertEquals("Error while adding track of playlist", exception.getMessage());
    }
}

