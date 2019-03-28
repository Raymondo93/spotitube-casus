package com.spotitube.app.service;

import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.model.ITrackModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/track")
public class TrackService {

    private ITrackModel trackModel;
    @Inject private ITrackDAO trackDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksAvailable(
        @QueryParam("forPlayList") int playlistID,
        @QueryParam("token") String token
    ) {
        List<ITrackModel> tracks = trackDAO.getAllTracks();
        if(tracks.isEmpty()) {
            return getResponse(null, 500);
        }
        JSONObject allTracksInJson = parseDataForResponse(tracks);
        return getResponse(allTracksInJson, 200);
    }

    private JSONObject parseDataForResponse(List<ITrackModel> tracks) {
        JSONArray trackArray = new JSONArray();
        for(ITrackModel model : tracks) {
            JSONObject track = new JSONObject();
            track.put("id", model.getId());
            track.put("performer", model.getPerformer());
            track.put("title", model.getTitle());
//            track.put("url", model.getUrl());
            track.put("duration", model.getDuration());
            track.put("playcount", model.getPlayCount());
            track.put("offlineAvailable", model.isOfflineAvailable());
            trackArray.put(track);
//            track.put("album", model.getAlbum());
        }
        JSONObject responseData = new JSONObject();
        responseData.put("tracks", trackArray);
        return responseData;
    }

    private Response getResponse(JSONObject resonse, int httpStatus) {
        return Response.status(httpStatus).entity(resonse).type(MediaType.APPLICATION_JSON).build();
    }

}
