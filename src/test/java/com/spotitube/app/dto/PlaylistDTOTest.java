package com.spotitube.app.dto;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.model.src.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistDTOTest {

    private static PlaylistDTO playlistDTO;

    private int id;
    private String name;
    private String owner;
    private TrackModel[] trackModel;

    @BeforeEach
    public void setup() {
        id = 15;
        name = "Super playlist";
        owner = "true";
        trackModel = new TrackModel[0];
        playlistDTO = new PlaylistDTO(id, name, owner, trackModel);
    }


    @Test
    public void getIdTest() {
        assertEquals(playlistDTO.getId(), id);
    }

    @Test
    public void setIdTest() {
        int newId = 20;
        playlistDTO.setId(newId);
        assertEquals(playlistDTO.getId(), newId);
    }

    @Test
    public void getNameTest() {
        assertEquals(playlistDTO.getName(), name);
    }

    @Test
    public void setNameTest() {
        String newName = "Awesome test";
        playlistDTO.setName(newName);
        assertEquals(playlistDTO.getName(), newName);
    }

    @Test
    public void getOwnerTest() {
        assertEquals(playlistDTO.getOwner(), owner);
    }

    @Test
    public void setOwnerTest() {
        String newOwner = "false";
        playlistDTO.setOwner(newOwner);
        assertEquals(playlistDTO.getOwner(), newOwner);
    }

    @Test
    public void getTracksTest() {
        assertEquals(playlistDTO.getTracks(), trackModel);
    }

}
