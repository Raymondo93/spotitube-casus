package com.spotitube.app.dao;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.exceptions.TracksException;

import java.util.List;

public interface ITrackDAO {

    List<TrackDTO> getAllTracksNotInPlaylist(int playlistId) throws TracksException;

    List<TrackDTO> getTracksFromPlaylist(int playlistId) throws TracksException;
}
