package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.exceptions.UserHasPlaylistException;

public interface IUserHasPlaylistDAO {

    void addPlaylistToUser(PlaylistDTO dto, String token) throws UserHasPlaylistException;

    void deletePlaylistFromUser(int playlistId, String token) throws UserHasPlaylistException;
}
