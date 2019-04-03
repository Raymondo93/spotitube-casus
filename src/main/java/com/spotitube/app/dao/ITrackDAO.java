package com.spotitube.app.dao;

import com.spotitube.app.DTO.TrackDTO;

import java.util.List;

public interface ITrackDAO {

    List<TrackDTO> getAllTracksNotInPlaylist(int playlistId);

    List<TrackDTO> getTracksFromPlaylist(int playlistId);
}
