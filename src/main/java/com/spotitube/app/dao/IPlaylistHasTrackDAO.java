package com.spotitube.app.dao;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.exceptions.PlaylistHasTrackException;

public interface IPlaylistHasTrackDAO {

    void removeTrackFromPlaylist(int playlistId, int trackId) throws PlaylistHasTrackException;

    void addTrackToPlaylist(TrackDTO dto, int playlistId) throws PlaylistHasTrackException;

}
