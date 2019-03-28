package com.spotitube.app.service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.dao.IUserHasPlaylistDAO;
import com.spotitube.app.model.IPlaylistModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Path("/playlists")
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        List<IPlaylistModel> playlists = playlistDAO.getPlaylists();
        JSONObject allPlaylistsResponse = parsePlaylistsToResonse(playlists, token);
        return getResponse(allPlaylistsResponse, 200);
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
            userPlaylistsResponse = parsePlaylistsToResonse(playlists, token);
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
            playlistResponse = parsePlaylistsToResonse(playlists, token);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response removePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        JSONObject userPlaylistsResponse = new JSONObject();
        if(userHasPlaylistDAO.deletePlaylistFromUser(id, token)) {
            List<IPlaylistModel> playlists = userHasPlaylistDAO.getPlaylistsOfUser(token);
            userPlaylistsResponse = parsePlaylistsToResonse(playlists, token);
            return getResponse(userPlaylistsResponse, 200);
        }
        return getResponse(userPlaylistsResponse, 500);
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
     * Parse playlist objects to JSON objects for frontend
     * @param playlists -> List with all playlists
     * @param token -> user token of session
     * @return Response with in JSON formatted data
     */
    private JSONObject parsePlaylistsToResonse(List<IPlaylistModel> playlists, String token) {
        JSONArray playlistArray = new JSONArray();
        int totalDuration = 0;
        for(IPlaylistModel model: playlists) {
            JSONObject playlist = new JSONObject();
            playlist.put("id", model.getId());
            playlist.put("name", model.getName());
            boolean isOwner = model.getOwnerName().equals(token) ? true : false;
            playlist.put("owner", isOwner);
            totalDuration += model.getPlaylistLength();
            playlistArray.put(playlist);
        }
        JSONObject responseData = new JSONObject();
        responseData.put("playlists", playlistArray);
        responseData.put("length", totalDuration);
        return responseData;
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
