package letsgo.lab6.server.managers;

import letsgo.lab6.common.enums.Color;
import letsgo.lab6.common.enums.Country;
import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.common.enums.MpaaRating;
import letsgo.lab6.server.entities.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Queue;

public class EntityManager {

    private static Long nextID;

    private static Long getNextID() {
        return nextID++;
    }

    public static Movie constructMovie(Queue<String> attributes) {
        Long id = getNextID();
        String creationDate = LocalDate.now().toString();
        return constructMovie(id, creationDate, attributes);
    }

    public static Movie constructMovie(Long id, String creationDate, Queue<String> attributes) {
        String name = attributes.poll();
        MovieGenre genre = MovieGenre.valueOf(Objects.requireNonNull(attributes.poll()).toUpperCase());
        String ratingString = attributes.poll();
        MpaaRating rating = Objects.requireNonNull(ratingString).isEmpty() ?
                null : MpaaRating.valueOf(Objects.requireNonNull(ratingString).toUpperCase());
        Long oscarsCount = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        Coordinates coordinates = constructCoordinates(attributes);
        Person operator = constructOperator(attributes);

        return new Movie(id, creationDate, name, coordinates, genre, rating, oscarsCount, operator);
    }

    private static Coordinates constructCoordinates(Queue<String> attributes) {
        String xString = attributes.poll();
        Double x = Objects.requireNonNull(xString).isEmpty() ?
                null : Double.parseDouble(Objects.requireNonNull(xString));
        Float y = Float.parseFloat(Objects.requireNonNull(attributes.poll()));
        return new Coordinates(x, y);
    }

    private static Person constructOperator(Queue<String> attributes) {
        String name = attributes.poll();
        String heightString = attributes.poll();
        Long height = Objects.requireNonNull(heightString).isEmpty() ?
                null : Long.parseLong(Objects.requireNonNull(heightString));
        Color eyeColor = Color.valueOf(Objects.requireNonNull(attributes.poll()).toUpperCase());
        Country nationality = Country.valueOf(Objects.requireNonNull(attributes.poll()).toUpperCase());
        Location location = constructLocation(attributes);
        return new Person(name, height, eyeColor, nationality, location);
    }

    private static Location constructLocation(Queue<String> attributes) {
        String xString = attributes.poll();
        String yString = attributes.poll();
        String zString = attributes.poll();
        Float x = Objects.requireNonNull(xString).isEmpty() ?
                null : Float.parseFloat(Objects.requireNonNull(xString));
        Float y = Objects.requireNonNull(yString).isEmpty() ?
                null : Float.parseFloat(Objects.requireNonNull(yString));
        Long z = Objects.requireNonNull(zString).isEmpty() ?
                null : Long.parseLong(Objects.requireNonNull(zString));
        String name = attributes.poll();
        return new Location(x, y, z, name);
    }

    public static void setNextID(Long newNextID) {
        nextID = newNextID;
    }

}
