package letsgo.lab6.server.managers.databaseManagers.dml;

import letsgo.lab6.common.enums.Color;
import letsgo.lab6.common.enums.Country;
import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.common.enums.MpaaRating;
import letsgo.lab6.server.entities.Coordinates;
import letsgo.lab6.server.entities.Location;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.entities.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void deleteMovie(Long id) throws SQLException {
        Connection connection = getConnection();
        deleteMovie(id, connection);
        connection.close();
    }

    public static void deleteMovies(List<Long> ids) throws SQLException {
        Connection connection = getConnection();
        for (Long id : ids) {
            deleteMovie(id, connection);
        }
        connection.close();
    }

    private static void deleteMovie(Long id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                select person_id from movies
                where id = ?;
                """);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Long personID = resultSet.getLong(1);
        preparedStatement = connection.prepareStatement("""
                delete from coordinates
                where coordinates.id in
                (select coordinates.id from coordinates join movies
                on coordinates.id = movies.coordinates_id and movies.id = ?);
                """);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("""
                delete from locations
                where locations.id in
                (select locations.id from locations join persons
                on locations.id = persons.location_id and persons.id = ?);
                """);
        preparedStatement.setLong(1, personID);
        preparedStatement.execute();
    }

    public static List<Movie> getMovies() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                select movies.id movie_id, movies.name movie_name, movies.creation_date movie_creation_date,
                movies.oscars_count movie_oscars_count, movies.genre movie_genre, movies.mpaa_rating movie_rating,
                movies.creator_id movie_creator_id, coordinates.x coord_x, coordinates.y coord_y,
                persons.name person_name, persons.height person_height, persons.eye_color person_eye_color,
                persons.nationality person_nationality, locations.x loc_x, locations.y loc_y, locations.z loc_z,
                locations.name loc_name from movies
                join coordinates on movies.coordinates_id = coordinates.id
                join persons on movies.person_id = persons.id
                join locations on persons.location_id = locations.id;
                """);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = constructMovie(resultSet);
            movies.add(movie);
        }
        connection.close();
        return movies;
    }

    private static Movie constructMovie(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("movie_id");
        String name = resultSet.getString("movie_name");
        String creationDate = resultSet.getString("movie_creation_date");
        Long oscarsCount = resultSet.getLong("movie_oscars_count");
        MovieGenre genre = MovieGenre.valueOf(resultSet.getString("movie_genre"));
        MpaaRating rating = resultSet.getString("movie_rating") == null ?
                null : MpaaRating.valueOf(resultSet.getString("movie_rating"));
        String creatorName = UserDMLManager.getUsernameByID(resultSet.getLong("movie_creator_id"));
        Double coordX = resultSet.getDouble("coord_x");
        Float coordY = resultSet.getFloat("coord_y");
        Coordinates coordinates = new Coordinates(coordX, coordY);
        String operatorName = resultSet.getString("person_name");
        Long operatorHeight = resultSet.getLong("person_height");
        Color operatorEyeColor = Color.valueOf(resultSet.getString("person_eye_color"));
        Country operatorNationality = Country.valueOf(resultSet.getString("person_nationality"));
        Float locX = resultSet.getFloat("loc_x");
        Float locY = resultSet.getFloat("loc_y");
        Long locZ = resultSet.getLong("loc_z");
        String locName = resultSet.getString("loc_name");
        Location location = new Location(locX, locY, locZ, locName);
        Person operator = new Person(operatorName, operatorHeight, operatorEyeColor, operatorNationality, location);
        return new Movie(id, creationDate, name, coordinates, genre, rating, oscarsCount, operator, creatorName);
    }

    public static void updateMovie(Long id, Movie movie) throws SQLException {
        Connection connection = getConnection();
        updateMovie(connection, id, movie);
        connection.close();
    }

    private static void updateMovie(Connection connection, Long id, Movie movie) throws SQLException {
        Coordinates coordinates = movie.getCoordinates();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                select coordinates.id from coordinates join movies
                on coordinates.id = movies.coordinates_id and movies.id = ?;
                """);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Long coordinatesID = resultSet.getLong(1);
        updateCoordinates(connection, coordinatesID, coordinates);

        Person operator = movie.getOperator();
        preparedStatement = connection.prepareStatement("""
                select persons.id from persons join movies
                on persons.id = movies.person_id and movies.id = ?""");
        preparedStatement.setLong(1, id);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Long operatorID = resultSet.getLong(1);
        updateOperator(connection, operatorID, operator);

        preparedStatement = connection.prepareStatement("""
                update movies
                set name = ?, oscars_count = ?, genre = ?, mpaa_rating = ?
                where id = ?;
                """);
        preparedStatement.setString(1, movie.getName());
        preparedStatement.setLong(2, movie.getOscarsCount());
        preparedStatement.setString(3, movie.getGenre().toString());
        preparedStatement.setString(4, movie.getMpaaRating() == null ?
                null : movie.getMpaaRating().toString());
        preparedStatement.setLong(5, id);
        preparedStatement.execute();
    }

    private static void updateCoordinates(Connection connection, Long id, Coordinates coordinates) throws SQLException {
        Double x = coordinates.x();
        Float y = coordinates.y();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                update coordinates
                set x = ?, y = ?
                where id = ?;
                """);
        if (x == null) {
            preparedStatement.setNull(1, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(1, x);
        }
        preparedStatement.setFloat(2, y);
        preparedStatement.setLong(3, id);
        preparedStatement.execute();
    }

    private static void updateOperator(Connection connection, Long id, Person operator) throws SQLException {
        Location location = operator.location();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                select locations.id from locations join persons
                on locations.id = persons.location_id and persons.id = ?;
                """);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Long locationID = resultSet.getLong(1);
        updateLocation(connection, locationID, location);

        preparedStatement = connection.prepareStatement("""
                update persons
                set name = ?, height = ?, eye_color = ?, nationality = ?
                where id = ?;
                """);
        preparedStatement.setString(1, operator.name());
        if (operator.height() == null) {
            preparedStatement.setNull(2, Types.BIGINT);
        } else {
            preparedStatement.setLong(2, operator.height());
        }
        preparedStatement.setString(3, operator.eyeColor().toString());
        preparedStatement.setString(4, operator.nationality().toString());
        preparedStatement.setLong(5, id);
        preparedStatement.execute();
    }

    private static void updateLocation(Connection connection, Long id, Location location) throws SQLException {
        Float x = location.x();
        Float y = location.y();
        Long z = location.z();
        String name = location.name();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                update locations
                set x = ?, y = ?, z = ?, name = ?
                where id = ?;
                """);
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
        preparedStatement.setString(4, name);
        preparedStatement.setLong(5, id);
        preparedStatement.execute();
    }

}
