package com.spotitube.app.dao;

import com.spotitube.app.model.IPlaylistModel;

import java.util.List;

public interface IUserHasPlaylistDAO {

   boolean addPlaylistToUser(IPlaylistModel playlistModel, String token);

   List<IPlaylistModel> getPlaylistsOfUser(String token);

   boolean deletePlaylistFromUser(int playlistId, String token);
}
