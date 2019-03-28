package com.spotitube.app.model;

import java.util.List;

public interface IPlaylistModel {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    boolean isOwner();

    void setOwner(boolean owner);

    String getOwnerName();

    void setOwnerName(String owner);

    int getPlaylistLength();

    void setPlaylistLength(int playlistLength);
}
