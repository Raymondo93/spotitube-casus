package com.spotitube.app.dto;

import com.spotitube.app.DTO.PlaylistDTO;
import com.spotitube.app.DTO.PlaylistResponseDTO;
import com.spotitube.app.model.src.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistResponseDTOTest {

    private static PlaylistResponseDTO playlistResponseDTO;

    private PlaylistDTO[] playlistDTO = new PlaylistDTO[1];
    private static PlaylistDTO dto;
    private final static int LENGTH = 50;
    private final static int ID = 10;
    private final static String NAME = "testdto";
    private final static String OWNER = "true";
    private static final TrackModel[] TRACKS = new TrackModel[0];

    @BeforeEach
    public void setUp(){
        playlistResponseDTO = new PlaylistResponseDTO(playlistDTO, LENGTH);
        dto = new PlaylistDTO(ID, NAME, OWNER, TRACKS);
    }

    @Test
    public void getPlaylistsTest() {
        assertEquals(playlistResponseDTO.getPlaylists(), playlistDTO);
    }

    @Test
    public void setPlaylistTest() {
        PlaylistDTO[] playlistDTOS = new PlaylistDTO[2];
        PlaylistDTO playlist = new PlaylistDTO(11, "another test dto" ,"true", new TrackModel[0]);
        playlistDTOS[0] = playlistDTO[0];
        playlistDTOS[1] = playlist;
        playlistResponseDTO.setPlaylists(playlistDTOS);
        assertEquals(playlistResponseDTO.getPlaylists(), playlistDTOS);
    }

    @Test
    public void getLengthTest() {
        assertEquals(playlistResponseDTO.getLength(), LENGTH);
    }

    @Test void setLengthTest() {
        int newLength = 60;
        playlistResponseDTO.setLength(newLength);
        assertEquals(playlistResponseDTO.getLength(), newLength);
    }

}
