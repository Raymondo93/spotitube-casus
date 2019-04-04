package com.spotitube.app.model.src;

import com.spotitube.app.model.ITrackModel;

import javax.enterprise.inject.Default;
import java.sql.Date;

@Default
public class TrackModel implements ITrackModel {

    private int id;
    private String title;
    private String performer;
    private int duration;
    private String album;
    private int playCount;
    private Date publicationDate;
    private String description;
    private boolean offlineAvailable;

    public TrackModel() {

    }

    public TrackModel(int id, String title, String performer, int duration, String album, int playCount, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this.playCount = playCount;
        this.offlineAvailable = offlineAvailable;
    }

    public TrackModel(int id ,String title, String performer, int duration, int playCount, Date publicationDate, String description, boolean offlineAvailable){
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.playCount = playCount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public boolean isOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

    public void setAlbum(String album) {
        this.album = album;
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
        return album;
    }
}
