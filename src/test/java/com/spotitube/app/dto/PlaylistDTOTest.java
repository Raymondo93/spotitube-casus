package com.spotitube.app.dto;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistDTOTest {

    private static PlaylistDTO playlistDTO;

    private int id;
    private String name;
    private String ownerName;
    private TrackDTO[] trackModel;

    @BeforeEach
    public void setup() {
        id = 15;
        name = "Super playlist";
        ownerName = "true";
        trackModel = new TrackDTO[0];
        playlistDTO = new PlaylistDTO(id, name, true, ownerName, trackModel, 15);
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
        assertEquals(playlistDTO.getOwnerName(), ownerName);
    }

    @Test
    public void setOwnerTest() {
        String newOwner = "false";
        playlistDTO.setOwnerName(newOwner);
        assertEquals(playlistDTO.getOwnerName(), newOwner);
    }

    @Test
    public void getTracksTest() {
        assertEquals(playlistDTO.getTracks(), trackModel);
    }

}
