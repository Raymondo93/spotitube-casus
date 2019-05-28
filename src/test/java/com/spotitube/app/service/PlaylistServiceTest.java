package com.spotitube.app.service;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.IPlaylistHasTrackDAO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.dao.src.PlaylistDAO;
import com.spotitube.app.dao.src.PlaylistHasTrackDAO;
import com.spotitube.app.dao.src.TrackDAO;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.dao.src.UserHasPlaylistDAO;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.PlaylistException;
import com.spotitube.app.exceptions.PlaylistHasTrackException;
import com.spotitube.app.exceptions.TracksException;
import com.spotitube.app.exceptions.UserHasPlaylistException;
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
    private IUserDAO userDAO;


    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        service = new PlaylistService();
        playlistDAO = Mockito.mock(PlaylistDAO.class);
        userHasPlaylistDAO = Mockito.mock(UserHasPlaylistDAO.class);
        trackDAO = Mockito.mock(TrackDAO.class);
        playlistHasTrackDAO = Mockito.mock(PlaylistHasTrackDAO.class);
        userDAO = Mockito.mock(UserDAO.class);


        service.setPlaylistDAO(playlistDAO);
        service.setUserHasPlaylistDAO(userHasPlaylistDAO);
        service.setTrackDAO(trackDAO);
        service.setPlaylistHasTrackDAO(playlistHasTrackDAO);
        service.setUserDAO(userDAO);
        playlistDTO = new PlaylistDTO(12, "testDTO", true, "true", new TrackDTO[0], 980);
        trackDTO = new TrackDTO(1, "testTrack", "Test performer", 200, "testAlbum", 0, null, null, false);
    }

    @Test
    public void getAllPlaylistsTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Response getPlaylistResponse = service.getAllPlaylists(TOKEN);
        Assertions.assertEquals(200, getPlaylistResponse.getStatus());
    }

    @Test
    public void getAllPlaylistsNotAuthorizedExceptionTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenThrow(new NotAuthorizedException("Not authorized"));
        Response getPlaylistResponse = service.getAllPlaylists(TOKEN);
        Assertions.assertEquals(403, getPlaylistResponse.getStatus());
    }

    @Test
    public void getAllPlaylistsException() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(false);
        Response playlistResponse = service.getAllPlaylists(TOKEN);
        Assertions.assertEquals(401, playlistResponse.getStatus());
    }

    @Test
    public void addPlaylistTest() throws NotAuthorizedException, PlaylistException, UserHasPlaylistException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.doNothing().when(playlistDAO).addPlaylistToDatabase(playlistDTO, TOKEN);
        Mockito.doNothing().when(userHasPlaylistDAO).addPlaylistToUser(playlistDTO, TOKEN);
        Response addPlaylistResponse = service.addPlaylist(playlistDTO, TOKEN);
        Assertions.assertEquals(200, addPlaylistResponse.getStatus());
    }

    @Test
    public void addPlaylistExceptionTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(false);
        Response response = service.addPlaylist(playlistDTO, TOKEN);
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void addPlaylistNotAuthorizedException() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenThrow(new NotAuthorizedException("Not authorized bro"));
        Response response = service.addPlaylist(playlistDTO, TOKEN);
        Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    public void editPlaylistTest() throws PlaylistException, NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.doNothing().when(playlistDAO).updatePlaylistNameInDatabase(playlistDTO);
        Response editPlayersResponse = service.editPlaylist(playlistDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, editPlayersResponse.getStatus());
    }

    @Test
    public void editPlaylistExceptionTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(false);
        Response response = service.editPlaylist(playlistDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void editPlaylistNotAuthorizedTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenThrow(new NotAuthorizedException("Not authorized bro"));
        Response response = service.editPlaylist(playlistDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    public void removePlaylistTest() throws UserHasPlaylistException, NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.doNothing().when(userHasPlaylistDAO).deletePlaylistFromUser(playlistDTO.getId(), TOKEN);
        Response removePlaylistResponse = service.removePlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, removePlaylistResponse.getStatus());
    }

    @Test
    public void removePlaylistExceptionTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(false);
        Response response = service.removePlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void removePlaylistNotAuthorizedTest() throws NotAuthorizedException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenThrow(new NotAuthorizedException("Not authorized bro"));
        Response response = service.removePlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(403, response.getStatus());
    }

    // TODO => more scenario's for exceptions

    @Test
    public void getAllTracksFromPlaylistTest() throws NotAuthorizedException, TracksException {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(trackDTOS);
        Response getAllTracksFromPlaylistResponse = service.getAllTracksFromPlaylist(playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, getAllTracksFromPlaylistResponse.getStatus());
    }


    @Test
    public void removeTrackFromPlaylistTest() throws PlaylistHasTrackException, TracksException, NotAuthorizedException {
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        Mockito.doNothing().when(playlistHasTrackDAO).removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId());
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
        Response removeTrackFromPlaylistResponse = service.removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId(), TOKEN);
        Assertions.assertEquals(200, removeTrackFromPlaylistResponse.getStatus());
    }

//    @Test
//    public void removeTrackFromPlaylistFailureTest() throws TracksException, PlaylistHasTrackException {
//        List<TrackDTO> tracks = new ArrayList<>();
//        Mockito.doThrow(new TracksException("Error no tracks")).when(playlistHasTrackDAO).removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId());
//        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
//        Response removeTrackFromPlaylistResponse = service.removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId(), TOKEN);
//        Assertions.assertEquals(500, removeTrackFromPlaylistResponse.getStatus());
//    }
//
    @Test
    public void addTrackToPlaylistTest() throws PlaylistHasTrackException, NotAuthorizedException, TracksException {
        Mockito.when(userDAO.isAuthorized(TOKEN)).thenReturn(true);
        List<TrackDTO> tracks = new ArrayList<>();
        Mockito.doNothing().when(playlistHasTrackDAO).addTrackToPlaylist(trackDTO, playlistDTO.getId());
        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
        Response addTrackToPlaylistResponse = service.addTrackToPlaylist(trackDTO, playlistDTO.getId(), TOKEN);
        Assertions.assertEquals(200, addTrackToPlaylistResponse.getStatus());
    }
//
//    @Test
//    public void addTrackToPlaylistFailureTest() {
//        List<TrackDTO> tracks = new ArrayList<>();
//        Mockito.when(playlistHasTrackDAO.addTrackToPlaylist(trackDTO, playlistDTO.getId())).thenReturn(false);
//        Mockito.when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);
//        Response addTrackToPlaylistResponse = service.addTrackToPlaylist(trackDTO, playlistDTO.getId(), TOKEN);
//        Assertions.assertEquals(500, addTrackToPlaylistResponse.getStatus());
//
//    }

}
