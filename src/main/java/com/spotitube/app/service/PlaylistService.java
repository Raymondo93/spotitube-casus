package com.spotitube.app.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.PlaylistResponseDTO;
import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.TrackModel;
import org.json.JSONObject;

import java.util.List;

@Path("/playlists")
@RequestScoped
public class PlaylistService {

    @Inject private IPlaylistDAO playlistDAO;
    @Inject private IUserHasPlaylistDAO userHasPlaylistDAO;
    @Inject private ITrackDAO trackDAO;

    /**
     * Request from frontend to get all playlists
     * @param token -> usertoken to validate
     * @return response with all the playlists
     */
    @GET
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token) {
        PlaylistResponseDTO responseDTO = getPlaylists(token);
        return Response.ok().entity(responseDTO).build();
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
        if(playlistDAO.addPlaylistToDatabase(dto, token) && userHasPlaylistDAO.addPlaylistToUser(dto, token)){
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        }
        return Response.status(500).build();
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
        if(playlistDAO.updatePlaylistNameInDatabase(dto)){
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        }
        return Response.status(500).build();
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
        if(userHasPlaylistDAO.deletePlaylistFromUser(id, token)) {
            PlaylistResponseDTO responseDTO = getPlaylists(token);
            return Response.ok().entity(responseDTO).build();
        }
        return Response.status(500).build();
    }

    private PlaylistResponseDTO getPlaylists(String token) {
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

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}/tracks")
    public Response getAllTracksFromPlaylist(@PathParam("id") int playlistId) {
        List<TrackDTO> t = trackDAO.getTracksFromPlaylist(playlistId);
        TrackDTO[] responseDTO = new TrackDTO[t.size()];
        for(int i = 0; i < responseDTO.length; ++i) {
            responseDTO[i] = new TrackDTO(t.get(i).getId(), t.get(i).getTitle(), t.get(i).getPerformer(), t.get(i).getDuration(),
                t.get(i).getAlbum(), t.get(i).getPlaycount(), t.get(i).getPublicationDate(), t.get(i).getDescription(),
                t.get(i).isOfflineAvailable());
        }
        return Response.ok().entity(responseDTO).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks{trackId}")
    public void removeTrackFromPlaylist(
        @QueryParam("playlistId") int playlistId,
        @QueryParam("trackId") int trackId
    ){
        getResponse(null, 404);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tracks")
    public void addTrackToPlaylist(@PathParam("id") int id) {
        getResponse(null, 404);
    }

    /**
     * Send response
     * @param response -> Resonse object
     * @param httpStatus -> http code status
     * @return The response
     */
    private Response getResponse(JSONObject response, int httpStatus) {
        return Response.status(httpStatus).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

}
