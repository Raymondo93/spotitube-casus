package com.spotitube.app.dao;

import com.spotitube.app.DTO.TrackDTO;

public interface IPlaylistHasTrackDAO {

    boolean removeTrackFromPlaylist(int playlistId, int trackId);

    boolean addTrackToPlaylist(TrackDTO dto, int playlistId);

}
