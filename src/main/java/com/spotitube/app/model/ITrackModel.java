package com.spotitube.app.model;

public interface ITrackModel {

    int getId();

    void setId(int id);

    String getPerformer();

    void setPerformer(String performer);

    String getTitle();

    void setTitle(String title);

    String getUrl();

    void setUrl(String url);

    int getDuration();

    void setDuration(int duration);

    int getPlayCount();

    void setPlayCount(int playCount);

    boolean isOfflineAvailable();

    void setOfflineAvailable(boolean offlineAvailable);

    String getAlbum();
}
