package com.spotitube.app.model.src;

import com.spotitube.app.model.src.TrackModel;

import java.util.Date;

public class VideoModel extends TrackModel {

    private Date publicationDate;
    private String description;

    public VideoModel() {

    }


    public VideoModel(int id, String performer, String title, int duration, String url, int playcount,
                      Date publicationDate, String description, boolean offlineAvailable) {
        super(id, performer, title, duration, url, playcount, offlineAvailable);
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlbum() {
        return " ";
    }
}
