package com.spotitube.app.persistence;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.entity.Playlist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class PlaylistPersistence {

    private EntityManagerFactory emFactory;
    private EntityManager entityManager;
    private static final String UNITNAME = "spotitube_research";

    public List<Playlist> getPlaylists() {
        openConnection();
        Query query = entityManager.createQuery("SELECT p FROM Playlist");
        List<Playlist> playlists = query.getResultList();
        closeConnection();
        return playlists;
    }

    public void addPlaylistToDatabase(PlaylistDTO playlistDTO, String token) {
        openConnection();
        entityManager.getTransaction().begin();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(playlistDTO.getName());
        playlist.setOwner(token);
        entityManager.getTransaction().commit();
        closeConnection();
    }

    public void updatePlaylistNameInDatabase(PlaylistDTO playlistDTO) {
        openConnection();
        entityManager.getTransaction().begin();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(playlistDTO.getName());
        entityManager.getTransaction().commit();
        closeConnection();
    }

    public void deletePlaylistFromDatabase(int playlistId) {
        openConnection();
        entityManager.getTransaction().begin();
        Playlist playlist = entityManager.find(Playlist.class, playlistId);
        entityManager.remove(playlist);
        entityManager.getTransaction().commit();
        closeConnection();
    }

    private void openConnection() {
        emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        entityManager = emFactory.createEntityManager();
    }

    private void closeConnection() {
        entityManager.close();
        emFactory.close();
    }
}
