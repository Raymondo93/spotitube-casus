package com.spotitube.app.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.PlaylistResponseDTO;
import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.IPlaylistHasTrackDAO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.PlaylistException;
import com.spotitube.app.exceptions.PlaylistHasTrackException;
import com.spotitube.app.exceptions.UserHasPlaylistException;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.TrackModel;
import java.util.List;

@Path("/playlists")
@RequestScoped
public class PlaylistService {

    private IPlaylistDAO playlistDAO;
    private IUserHasPlaylistDAO userHasPlaylistDAO;
    private ITrackDAO trackDAO;
    private IPlaylistHasTrackDAO playlistHasTrackDAO;
    private IUserDAO userDAO;


    /**
     * Request from frontend to get all playlists
     * @param token -> usertoken to validate
     * @return response with all the playlists
     */
    @GET
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token) {
        try {
            userDAO.isAuthorized(token);
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        } catch (PlaylistException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }


    /**
     * Request to add playlist to database and link to owner
     * @param token -> queryParameter usertoken
     * @return response with playlists of the current user
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(PlaylistDTO dto, @QueryParam("token") String token) {
        try{
            userDAO.isAuthorized(token);
            playlistDAO.addPlaylistToDatabase(dto, token);
            userHasPlaylistDAO.addPlaylistToUser(dto, token);
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return Response.status(403).build();
        } catch (PlaylistException | UserHasPlaylistException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    /**
     * Request to change the name of the playlist
     * @param token -> user token of session
     * @return Response with playlists of the current user.
     */
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response editPlaylist(PlaylistDTO dto, @PathParam("id") int id, @QueryParam("token") String token){
        try{
            userDAO.isAuthorized(token);
            playlistDAO.updatePlaylistNameInDatabase(dto);
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        } catch (PlaylistException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    /**
     * Request to remove playlist from user.
     * @param id -> playlist id
     * @param token -> user token of the session
     * @return Response with all playlist of the current user.
     */
    @DELETE
    @Produces("application/json")
    @Path("/{id}")
    public Response removePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        try {
           userDAO.isAuthorized(token);
           userHasPlaylistDAO.deletePlaylistFromUser(id, token);
           PlaylistResponseDTO responseDTO = getPlaylists(token);
           return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        } catch (UserHasPlaylistException | PlaylistException e) {
            e.printStackTrace();
            return Response.status(500).build();

        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}/tracks")
    public Response getAllTracksFromPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        try {
            userDAO.isAuthorized(token);
            List<TrackDTO> t = trackDAO.getTracksFromPlaylist(playlistId);
            TrackDTO[] responseDTO = getTracksFromPlaylist(t);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        }
    }

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{playlistId}/tracks/{trackId}")
    public Response removeTrackFromPlaylist(
        @PathParam("playlistId") int playlistId,
        @PathParam("trackId") int trackId,
        @QueryParam("token") String token
    ){
        try {
            userDAO.isAuthorized(token);
            playlistHasTrackDAO.removeTrackFromPlaylist(playlistId, trackId);
            List<TrackDTO> t = trackDAO.getTracksFromPlaylist(playlistId);
            TrackDTO[] responseDTO = getTracksFromPlaylist(t);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        } catch (PlaylistHasTrackException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}/tracks")
    public Response addTrackToPlaylist(TrackDTO dto, @PathParam("id") int playlistId, @QueryParam("token") String token) {
        try {
            userDAO.isAuthorized(token);
            playlistHasTrackDAO.addTrackToPlaylist(dto, playlistId);
            List<TrackDTO> t = trackDAO.getTracksFromPlaylist(playlistId);
            TrackDTO[] responseDTO = getTracksFromPlaylist(t);
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).build();
        } catch (PlaylistHasTrackException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    /**
     * Get tracks of certain playlist and parse to an array
     * @param t -> list of playlists
     * @return Array of playlists
     */
    private TrackDTO[] getTracksFromPlaylist(List<TrackDTO> t) {
        TrackDTO[] responseDTO = new TrackDTO[t.size()];
        for(int i = 0; i < t.size(); ++i) {
            responseDTO[i] = new TrackDTO(t.get(i).getId(), t.get(i).getTitle(), t.get(i).getPerformer(), t.get(i).getDuration(),
                t.get(i).getAlbum(), t.get(i).getPlaycount(), t.get(i).getPublicationDate(), t.get(i).getDescription(),
                t.get(i).isOfflineAvailable());
        }
        return responseDTO;
    }

    /**
     * Get playlists of a user
     * @param token -> usertoken
     * @return -> array of playlists
     */
    private PlaylistResponseDTO getPlaylists(String token) throws PlaylistException {
        List<IPlaylistModel> playlistModels = playlistDAO.getPlaylists();
        PlaylistDTO[] playlistDTOS = new PlaylistDTO[playlistModels.size()];
        int playtime = 0;
        for(int i = 0; i < playlistDTOS.length; ++i){
            String owner = playlistModels.get(i).getOwnerName().equals(token) ? "true" : "false";
            playlistDTOS[i] = new PlaylistDTO(playlistModels.get(i).getId(), playlistModels.get(i).getName() , owner, new TrackModel[0]);
            playtime += playlistModels.get(i).getPlaylistLength();
        }
        return new PlaylistResponseDTO(playlistDTOS, playtime);
    }

    @Inject void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject void setUserHasPlaylistDAO(IUserHasPlaylistDAO userHasPlaylistDAO) {
        this.userHasPlaylistDAO = userHasPlaylistDAO;
    }

    @Inject void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject void setPlaylistHasTrackDAO(IPlaylistHasTrackDAO playlistHasTrackDAO) {
        this.playlistHasTrackDAO = playlistHasTrackDAO;
    }

    @Inject void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
