package com.spotitube.app.service;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.src.TrackDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TrackServiceTest {

    private ITrackDAO trackDAO;
    private TrackService service;
    private int playlistId = 10;
    private static final String TOKEN = "Test234437";

    @BeforeEach
    void setup() {
        service = new TrackService();
        trackDAO = Mockito.mock(TrackDAO.class);
        service.setTrackDAO(trackDAO);
    }

    @Test
    public void getAllTracksNotInPlaylistTest() {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(trackDAO.getAllTracksNotInPlaylist(playlistId)).thenReturn(tracks);
        Response getAllTracksNotInPlaylistResponse = service.getAllTracksNotInPlaylist(playlistId, TOKEN);
        Assertions.assertEquals(200, getAllTracksNotInPlaylistResponse.getStatus());
    }
}
