package com.spotitube.app.model;

import java.sql.Date;

public interface ITrackModel {

    int getId();

    void setId(int id);

    String getPerformer();

    void setPerformer(String performer);

    String getTitle();

    void setTitle(String title);

    int getDuration();

    void setDuration(int duration);

    int getPlayCount();

    void setPlayCount(int playCount);

    boolean isOfflineAvailable();

    void setOfflineAvailable(boolean offlineAvailable);

    String getAlbum();

    void setAlbum(String album);

    Date getPublicationDate();

    void setPublicationDate(Date publicationDate);

    String getDescription();

    void setDescription(String description);
}
