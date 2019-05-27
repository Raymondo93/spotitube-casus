package com.spotitube.app.service;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.TracksException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/track")
public class TrackService {

    private ITrackDAO trackDAO;
    private IUserDAO userDAO;

    /**
     * Get all the playlists those are not in the playlist. First check is to authorize
     * @param playlistID => GET param playlist id
     * @param token => GET param user token
     * @return => If authorized a list of playlist, else http 403 error.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksNotInPlaylist(
        @QueryParam("forPlaylist") int playlistID,
        @QueryParam("token") String token
    ) {
        try {
            userDAO.isAuthorized(token);
            List<TrackDTO> trackDTOS = trackDAO.getAllTracksNotInPlaylist(playlistID);
            TrackDTO[] responseDTO = new TrackDTO[trackDTOS.size()];
            for (int i = 0; i < trackDTOS.size(); ++i) {
                String date = trackDTOS.get(i).getPublicationDate();
                responseDTO[i] = new TrackDTO(trackDTOS.get(i).getId(), trackDTOS.get(i).getTitle(),
                    trackDTOS.get(i).getPerformer(), trackDTOS.get(i).getDuration(), trackDTOS.get(i).getAlbum(),
                    trackDTOS.get(i).getPlaycount(), date, trackDTOS.get(i).getDescription(), trackDTOS.get(i).isOfflineAvailable());
            }
            return Response.ok().entity(responseDTO).build();
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return Response.status(403).build();
        } catch (TracksException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
