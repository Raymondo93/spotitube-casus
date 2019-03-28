package com.spotitube.app.dao.src;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.IPlaylistDAO;
import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.src.PlaylistModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class PlaylistDAO implements IPlaylistDAO {

    @Inject
    private IDatabaseConnection databaseConnection;

    /**
     *  {@inheritDoc}
     */
    public List<IPlaylistModel> getPlaylists() {
        String query = "SELECT p.id, p.name, u.usertoken /*lengt playlist subselect */ FROM playlist p INNER JOIN user ON p.owner = u.username;";
        List<IPlaylistModel> playlists = new ArrayList<>();
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery()
        ){
            if (!set.first()) {
                return playlists;
            }
            //TODO -> set the resultset in a object -> dirty fix
            while(set.next()) {
                playlists.add(new PlaylistModel(set.getInt("id"), set.getString("name"),
                    set.getString("owner"), set.getInt("playlistLength")));
            }
            return playlists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }


    /**
     *  {@inheritDoc}
     */
    public boolean addPlaylistToDatabase(IPlaylistModel playlistModel, String token) {
        String query = "INSERT INTO playlist(name, owner) SELECT ?, u.username FROM user WHERE token = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, playlistModel.getName());
            statement.setString(2, token);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  {@inheritDoc}
     */
    public boolean updatePlaylistNameInDatabase(IPlaylistModel playlistModel) {
        String query = "UPDATE playlist SET name = ? WHERE id = ?;";
        try (
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, playlistModel.getName());
            statement.setInt(2, playlistModel.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
