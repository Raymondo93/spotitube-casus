package com.spotitube.app.dao.src;

import com.spotitube.app.dao.IDatabaseConnection;
import com.spotitube.app.dao.ITrackDAO;
import com.spotitube.app.exceptions.NoDatabaseConnectionException;
import com.spotitube.app.model.ITrackModel;
import com.spotitube.app.model.src.TrackModel;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class TrackDAO implements ITrackDAO {

    @Inject private IDatabaseConnection databaseConnection;

    public List<ITrackModel> getAllTracks() {
        String query = "SELECT * FROM track;";
        List<ITrackModel> tracks = new ArrayList<>();
        try(
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()) {
                // TODO => forloop to take all te results
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
