package com.spotitube.app.service;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.ITrackDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/track")
public class TrackService {

    @Inject private ITrackDAO trackDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksNotInPlaylist(
        @QueryParam("forPlaylist") int playlistID,
        @QueryParam("token") String token
    ) {
        List<TrackDTO> trackDTOS = trackDAO.getAllTracksNotInPlaylist(playlistID);
        TrackDTO[] responseDTO = new TrackDTO[trackDTOS.size()];
        for (int i = 0; i < trackDTOS.size(); ++i) {
            String date = trackDTOS.get(i).getPublicationDate();
            responseDTO[i] = new TrackDTO(trackDTOS.get(i).getId(), trackDTOS.get(i).getTitle(), trackDTOS.get(i).getPerformer(), trackDTOS.get(i).getDuration(),
                trackDTOS.get(i).getAlbum(), trackDTOS.get(i).getPlaycount(), date, trackDTOS.get(i).getDescription(),
                trackDTOS.get(i).isOfflineAvailable());
        }
        return Response.ok().entity(responseDTO).build();
    }

}
