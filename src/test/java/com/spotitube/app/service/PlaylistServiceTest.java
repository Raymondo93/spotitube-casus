package com.spotitube.app.service;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.IPlaylistHasTrackDAO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.dao.src.PlaylistDAO;
import com.spotitube.app.dao.src.PlaylistHasTrackDAO;
import com.spotitube.app.dao.src.TrackDAO;
import com.spotitube.app.dao.src.UserHasPlaylistDAO;
import com.spotitube.app.model.src.TrackModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PlaylistServiceTest {

    static final String TOKEN = "test13341234";

    private PlaylistService service;
    private IPlaylistDAO playlistDAO;
    private IUserHasPlaylistDAO userHasPlaylistDAO;
    private ITrackDAO trackDAO;
    private IPlaylistHasTrackDAO playlistHasTrackDAO;

    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        service = new PlaylistService();
        playlistDAO = Mockito.mock(PlaylistDAO.class);
        userHasPlaylistDAO = Mockito.mock(UserHasPlaylistDAO.class);
        trackDAO = Mockito.mock(TrackDAO.class);
        playlistHasTrackDAO = Mockito.mock(PlaylistHasTrackDAO.class);

        service.setPlaylistDAO(playlistDAO);
        service.setUserHasPlaylistDAO(userHasPlaylistDAO);
        service.setTrackDAO(trackDAO);
        service.setPlaylistHasTrackDAO(playlistHasTrackDAO);
        playlistDTO = new PlaylistDTO(12, "testDTO", "true", new TrackModel[0]);
        trackDTO = new TrackDTO(1, "testTrack", "Test performer", 200, "testAlbum", 0, null, null, false);
    }

    @Test
    public void getAllPlaylistsTest() {
        Response getPlaylistResponse = service.getAllPlaylists(TOKEN);
        Assertions.assertEquals(200, getPlaylistResponse.getStatus());
        PlaylistDTO playlistDTO = new PlaylistDTO(12, "testDTO", "true", new TrackModel[0]);
    }

    @Test
    public void addPlaylistTest() {
        Mockito.when(playlistDAO.addPlaylistToDatabase(playlistDTO, TOKEN)).thenReturn(true);
        Mockito.when(userHasPlaylistDAO.addPlaylistToUser(playlistDTO, TOKEN)).thenReturn(true);
        Response addPlaylistResponse = service.addPlaylist(playlistDTO, TOKEN);
        Assertions.assertEquals(200, addPlaylistResponse.getStatus());
    }

    @Test
    public void editPlaylistTest() {
        Mockito.when(playlistDAO.updatePlaylistNameInDatabase(playlistDTO)).thenReturn(true);
        Response editPlayersResponse = service.editPlaylist(playlistDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, editPlayersResponse.getStatus());
    }

    @Test
    public void removePlaylistTest() {
        Mockito.when(userHasPlaylistDAO.deletePlaylistFromUser(playlistDTO.getId(), TOKEN)).thenReturn(true);
        Response removePlaylistResponse = service.removePlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, removePlaylistResponse.getStatus());
    }

    @Test
    public void getAllTracksFromPlaylistTest() {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(trackDTOS);
        Response getAllTracksFromPlaylistResponse = service.getAllTracksFromPlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, getAllTracksFromPlaylistResponse.getStatus());
    }

    @Test
    public void removeTrackFromPlaylistTest(){
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(playlistHasTrackDAO.removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId())).thenReturn(true);
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
        Response removeTrackFromPlaylistResponse = service.removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId(), TOKEN);
        Assertions.assertEquals(200, removeTrackFromPlaylistResponse.getStatus());
    }

    @Test
    public void addTrackToPlaylistTest() {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(playlistHasTrackDAO.addTrackToPlaylist(trackDTO, playlistDTO.getId())).thenReturn(true);
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
        Response addTrackToPlaylistResponse = service.addTrackToPlaylist(trackDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, addTrackToPlaylistResponse.getStatus());
    }

}
