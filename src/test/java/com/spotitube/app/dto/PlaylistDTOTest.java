package com.spotitube.app.dto;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PlaylistDTOTest {

    private static PlaylistDTO playlistDTO;

    private int id;
    private String name;
    private String ownerName;
    private boolean owner;
    private TrackDTO[] trackDTO;
    private int playlistlength;

    @BeforeEach
    public void setup() {
        id = 15;
        name = "Super playlist";
        ownerName = "true";
        trackDTO = new TrackDTO[0];
        owner = true;
        playlistlength = 30;
        playlistDTO = new PlaylistDTO(id, name, owner, ownerName, trackDTO, playlistlength);
    }


    @Test
    public void getIdTest() {
        Assertions.assertEquals(playlistDTO.getId(), id);
    }

    @Test
    public void setIdTest() {
        int newId = 20;
        playlistDTO.setId(newId);
        Assertions.assertEquals(playlistDTO.getId(), newId);
    }

    @Test
    public void getNameTest() {
        Assertions.assertEquals(playlistDTO.getName(), name);
    }

    @Test
    public void setNameTest() {
        String newName = "Awesome test";
        playlistDTO.setName(newName);
        Assertions.assertEquals(playlistDTO.getName(), newName);
    }

    @Test
    public void getOwnerNameTest() {
        Assertions.assertEquals(playlistDTO.getOwnerName(), ownerName);
    }

    @Test
    public void setOwnerNameTest() {
        String newOwner = "false";
        playlistDTO.setOwnerName(newOwner);
        Assertions.assertEquals(playlistDTO.getOwnerName(), newOwner);
    }

    @Test
    public void getTracksTest() {
        Assertions.assertEquals(playlistDTO.getTracks(), trackDTO);
    }

    @Test
    public void setTracksTest() {
        TrackDTO[] dto = new TrackDTO[0];
        playlistDTO.setTracks(dto);
        Assertions.assertEquals(playlistDTO.getTracks(), dto);
    }

    @Test
    public void getPlaylistLengthTest() {
        Assertions.assertEquals(playlistDTO.getPlaylistLength(), playlistlength);
    }

    @Test
    public void setPlaylistLengthTest() {
        playlistDTO.setPlaylistLength(100);
        Assertions.assertEquals(playlistDTO.getPlaylistLength(), 100);
    }

    @Test
    public void getOwnerTest() {
        Assertions.assertEquals(playlistDTO.getOwner(), owner);
    }

    @Test
    public void setOwnerTest() {
        playlistDTO.setOwner(false);
        Assertions.assertFalse(playlistDTO.getOwner());
    }

}
