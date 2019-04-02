package com.spotitube.app.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.PlaylistResponseDTO;
import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.TrackModel;
import org.json.JSONObject;

import java.util.List;

@Path("/playlists")
@RequestScoped
public class PlaylistService {

    @Inject private IPlaylistDAO playlistDAO;
    private IPlaylistModel playlistModel;
    @Inject private IUserHasPlaylistDAO userHasPlaylistDAO;

    /**
     * Request from frontend to get all playlists
     * @param token -> usertoken to validate
     * @return response with all the playlists
     */
    @GET
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token) {
        List<IPlaylistModel> playlists = playlistDAO.getPlaylists();
        PlaylistDTO[] playlistArray = new PlaylistDTO[playlists.size()];
        int playtime = 0;
        for(int i = 0; i < playlistArray.length; ++i) {
            String owner = token.equals(playlists.get(i).getOwnerName()) ? "true" : "false";
            playlistArray[i] = new PlaylistDTO(playlists.get(i).getId(), playlists.get(i).getName(), owner, new TrackModel[0]);
            playtime += playlists.get(i).getPlaylistLength();
        }
        PlaylistResponseDTO responseDTO = new PlaylistResponseDTO(playlistArray, playtime);
        return Response.ok().entity(responseDTO).build();
    }


    /**
     * Request to add playlist to database and link to owner
     * @param token -> queryParameter usertoken
     * @param id -> formParameter playlist id
     * @param name -> formParameter playlist name
     * @param owner -> formParameter playlist owner
     * @return response with playlists of the current user
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(
        @QueryParam("token") String token,
        @FormParam("id") int id,
        @FormParam("name") String name,
        @FormParam("owner") boolean owner
        ) {
        playlistModel.setId(id);
        playlistModel.setName(name);
        playlistModel.setOwner(owner);
        JSONObject userPlaylistsResponse = new JSONObject();
        if (playlistDAO.addPlaylistToDatabase(playlistModel, token)) {
            userHasPlaylistDAO.addPlaylistToUser(playlistModel, token);
            List<IPlaylistModel> playlists =  userHasPlaylistDAO.getPlaylistsOfUser(token);
            return getResponse(userPlaylistsResponse, 201);
        }
        return getResponse(userPlaylistsResponse, 500);
    }

    /**
     * Request to change the name of the playlist
     * @param token -> user token of session
     * @param id -> playlist id
     * @param name -> name of the playlist
     * @param owner -> owner of playlist
     * @return Response with playlists of the current user.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editPlaylist(
        @QueryParam("token") String token,
        @QueryParam("id") int id,
        @QueryParam("name") String name,
        @QueryParam("owner") boolean owner,
        @QueryParam("tracks") List<String> tracks
    ){
        JSONObject playlistResponse = new JSONObject();
        playlistModel.setId(id);
        playlistModel.setName(name);
        playlistModel.setOwner(owner);
        if(playlistDAO.updatePlaylistNameInDatabase(playlistModel)) {
            List<IPlaylistModel> playlists = userHasPlaylistDAO.getPlaylistsOfUser(token);
            return getResponse(playlistResponse, 200);
        }
        return getResponse(playlistResponse, 500);
    }

    /**
     * Request to remove playlist from user.
     * @param id -> playlist id
     * @param token -> user token of the session
     * @return Response with all playlist of the current user.
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response removePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        if(userHasPlaylistDAO.deletePlaylistFromUser(id, token)) {
            List<IPlaylistModel> playlistModels = playlistDAO.getPlaylists();
            for(IPlaylistModel model: playlistModels) {
                if(model.getOwnerName().equals(token)) {
                    playlistModels.remove(model);
                }
            }
            PlaylistDTO[] playlistDTOS = new PlaylistDTO[playlistModels.size()];
            int playtime = 0;
            for(int i = 0; i < playlistDTOS.length; ++i){
                playlistDTOS[i] = new PlaylistDTO(playlistModels.get(i).getId(), playlistModels.get(i).getName(), "true", new TrackModel[0]);
                playtime += playlistModels.get(i).getPlaylistLength();
            }
            PlaylistResponseDTO responseDTO = new PlaylistResponseDTO(playlistDTOS, playtime);
            return Response.ok().entity(responseDTO).build();
        }
        return Response.status(500).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tracks")
    public void getAllTracksFromPlaylist(@PathParam("id") int id) {
        getResponse(null, 404);
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
