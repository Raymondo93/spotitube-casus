package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.UserHasPlaylistDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
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

public class UserHasPlaylistTest {

    private static IDatabaseConnection databaseConnection;
    private ResultSet set;
    private PreparedStatement statement;
    private static IUserHasPlaylistDAO userHasPlaylistDAO;
    private static final String TOKEN = "Test22323424";
    private static final int PLAYLISTID = 567;
    private static PlaylistDTO playlistDTO;

    @BeforeAll
    public static void setupAll() {
        playlistDTO = new PlaylistDTO(PLAYLISTID, "Testplaylist", "true", new TrackModel[0]);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        userHasPlaylistDAO = new UserHasPlaylistDAO(databaseConnection);
    }

    @BeforeEach
    public void setup() throws NoDatabaseConnectionException, SQLException {
        Connection connection = Mockito.mock(Connection.class);
        set = Mockito.mock(ResultSet.class);
        statement = Mockito.mock(PreparedStatement.class);

        Mockito.when(databaseConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.execute()).thenReturn(true);
    }

    @Test
    public void addPlaylistToUserTest() throws SQLException {
        Assertions.assertTrue(userHasPlaylistDAO.addPlaylistToUser(playlistDTO, TOKEN));
    }

    @Test
    public void deletePlaylistFromUser() throws SQLException {
        Assertions.assertTrue(userHasPlaylistDAO.deletePlaylistFromUser(PLAYLISTID, TOKEN));
    }
}
