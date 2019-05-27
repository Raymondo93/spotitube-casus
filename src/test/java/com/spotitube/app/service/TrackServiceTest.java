package com.spotitube.app.service;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.src.TrackDAO;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.TracksException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.jws.soap.SOAPBinding;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TrackServiceTest {

    private ITrackDAO trackDAO;
    private IUserDAO userDAO;
    private TrackService service;
    private int playlistId = 10;
    private static final String TOKEN = "Test234437";

    @BeforeEach
    void setup() {
        service = new TrackService();
        trackDAO = Mockito.mock(TrackDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        service.setTrackDAO(trackDAO);
        service.setUserDAO(userDAO);
    }

    @Test
    public void getAllTracksNotInPlaylistTest() throws TracksException, NotAuthorizedException {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.when(trackDAO.getAllTracksNotInPlaylist(playlistId)).thenReturn(tracks);
        Response getAllTracksNotInPlaylistResponse = service.getAllTracksNotInPlaylist(playlistId, TOKEN);
        Assertions.assertEquals(200, getAllTracksNotInPlaylistResponse.getStatus());
    }
}
