package letsgo.lab6.server.managers.databaseManagers.dml;

import letsgo.lab6.server.entities.Coordinates;
import letsgo.lab6.server.entities.Location;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.entities.Person;

import java.sql.*;

public class MovieDMLManager extends DMLManager {

    public static Long addMovie(Movie movie, String username) throws SQLException {
        Connection connection = getConnection();
        Long creatorID = UserDMLManager.getIDByUsername(username);
        Long movieID = addMovie(movie, connection, creatorID);
        connection.close();
        return movieID;
    }

    private static Long addMovie(Movie movie, Connection connection, Long creatorID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into movies (name, coordinates_id, creation_date,
                    oscars_count, genre, mpaa_rating, person_id, creator_id)
                values (?, ?, ?, ?, ?, ?, ? ,?)
                returning id;
                """);
        Long coordinatesID = addCoordinates(movie.getCoordinates(), connection, creatorID);
        String rating = movie.getMpaaRating() == null ? null : movie.getMpaaRating().toString();
        Long operatorID = addPerson(movie.getOperator(), connection, creatorID);
        preparedStatement.setString(1, movie.getName());
        preparedStatement.setLong(2, coordinatesID);
        preparedStatement.setString(3, movie.getCreationDate());
        preparedStatement.setLong(4, movie.getOscarsCount());
        preparedStatement.setString(5, movie.getGenre().toString());
        preparedStatement.setString(6, rating);
        preparedStatement.setLong(7, operatorID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    private static Long addCoordinates(Coordinates coordinates, Connection connection, Long creatorID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into coordinates (x, y, creator_id)
                values (?, ?, ?)
                returning id;
                """);
        preparedStatement.setDouble(1, coordinates.x());
        preparedStatement.setFloat(2, coordinates.y());
        preparedStatement.setLong(3, creatorID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    private static Long addPerson(Person person, Connection connection, Long creatorID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into persons (name, height, eye_color, nationality, location_id, creator_id)
                values (?, ?, ?, ?, ?, ?)
                returning id;
                """);
        preparedStatement.setString(1, person.name());
        Long height = person.height();
        if (height == null) {
            preparedStatement.setNull(2, Types.BIGINT);
        } else {
            preparedStatement.setLong(2, height);
        }
        preparedStatement.setString(3, person.eyeColor().toString());
        preparedStatement.setString(4, person.nationality().toString());
        Long locationID = addLocation(person.location(), connection, creatorID);
        preparedStatement.setLong(5, locationID);
        preparedStatement.setLong(6, creatorID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    private static Long addLocation(Location location, Connection connection, Long creatorID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into locations (x, y, z, creator_id)
                values (?, ?, ?, ?)
                returning id;
                """);
        Float x = location.x();
        Float y = location.y();
        Long z = location.z();
        if (x == null) {
            preparedStatement.setNull(1, Types.FLOAT);
        } else {
            preparedStatement.setFloat(1, x);
        }
        if (y == null) {
            preparedStatement.setNull(2, Types.FLOAT);
        } else {
            preparedStatement.setFloat(2, y);
        }
        if (z == null) {
            preparedStatement.setNull(3, Types.BIGINT);
        } else {
            preparedStatement.setLong(3, z);
        }
        preparedStatement.setLong(4, creatorID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }
}
