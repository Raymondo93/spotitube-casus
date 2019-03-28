package com.spotitube.app.model.src;

import com.spotitube.app.model.ITrackModel;

import javax.enterprise.inject.Default;
import java.util.Date;

@Default
public abstract class TrackModel implements ITrackModel {

    private int id;
    private String performer;
    private String title;
    private int duration;
    private String url;
    private int playCount;
    private boolean offlineAvailable;

    public TrackModel() {

    }
    public TrackModel(int id, String performer, String title, int duration, String url,
                      int playCount, boolean offlineAvailable) {
        this.id = id;
        this.performer = performer;
        this.title = title;
        this.duration = duration;
        this.url = url;
        this.playCount = playCount;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public abstract String getAlbum();

}
