package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.PlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.TrackModel;
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

public class PlaylistDAOTest {

    private static PlaylistDTO playlistDTO;
    private static IDatabaseConnection databaseConnection;
    private static IPlaylistDAO playlistDAO;

    private PreparedStatement statement;
    private ResultSet set;
    private final static String TOKEN = "test234234234";

    @BeforeAll
    public static void setupAll() {
        playlistDTO = new PlaylistDTO(20, "testPlaylist", "true", new TrackModel[0]);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        playlistDAO = new PlaylistDAO(databaseConnection);
    }

    @BeforeEach
    public void setup() throws NoDatabaseConnectionException, SQLException {
        Connection conn = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        set = Mockito.mock(ResultSet.class);

        Mockito.when(databaseConnection.getConnection()).thenReturn(conn);
        Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(statement);
    }

    @Test
    public void getPlaylistsTest() throws SQLException {
        List<IPlaylistModel> playlists = new ArrayList<>();

        Mockito.when(statement.executeQuery()).thenReturn(set);

        Assertions.assertEquals(playlistDAO.getPlaylists(), playlists);
    }

    @Test
    public void addPlaylistToDatabaseTest() throws SQLException {
        Mockito.when(statement.execute()).thenReturn(true);
        Assertions.assertTrue(playlistDAO.addPlaylistToDatabase(playlistDTO, TOKEN));
    }

    @Test
    public void updatePlaylistNameInDatabaseTest() throws SQLException {
        Mockito.when(statement.execute()).thenReturn(true);
        Assertions.assertTrue(playlistDAO.updatePlaylistNameInDatabase(playlistDTO));
    }
}
