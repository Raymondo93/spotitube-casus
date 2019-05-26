package com.spotitube.app.dao;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.exceptions.PlaylistException;
import com.spotitube.app.model.IPlaylistModel;

import java.util.List;

public interface IPlaylistDAO {

    /**
     * Get all playlists from database, with length of all tracks.
     * @return Arraylist of playlists
     */
    List<IPlaylistModel> getPlaylists();

    /**
     * Add a new playlist for a user in the database
     * @param dto -> Playlist Data Transfer Object
     * @param token -> usertoken
     */
    void addPlaylistToDatabase(PlaylistDTO dto, String token) throws PlaylistException;

    /**
     * Update the name of a playlist
     * @param dto -> The playlist who's name has to be changed
     */
    void updatePlaylistNameInDatabase(PlaylistDTO dto) throws PlaylistException;
}
