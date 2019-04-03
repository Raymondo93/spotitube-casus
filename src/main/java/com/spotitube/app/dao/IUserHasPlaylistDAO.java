package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.model.IPlaylistModel;

import java.util.List;

public interface IUserHasPlaylistDAO {

   boolean addPlaylistToUser(PlaylistDTO dto, String token);


   boolean deletePlaylistFromUser(int playlistId, String token);
}
