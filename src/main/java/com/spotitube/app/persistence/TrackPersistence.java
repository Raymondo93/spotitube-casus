package com.spotitube.app.persistence;

import com.spotitube.app.DTO.TrackDTO;
import com.spotitube.app.entity.Track;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TrackPersistence {

    private static final String UNITNAME = "spotitube_research";

    public List<Track> getAllTracks() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT t from Track t");
        List<Track> tracks = query.getResultList();
        entityManager.close();
        emFactory.close();
        return tracks;
    }

    public void createSong() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

    }

    public void createTrack() {

    }
}
