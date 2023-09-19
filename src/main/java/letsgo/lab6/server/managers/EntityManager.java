package letsgo.lab6.server.managers;

import letsgo.lab6.server.entities.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Queue;

public class EntityManager {

    private static Long nextID = 1L;

    private static Long getNextID() {
        return nextID++;
    }

    public static Movie constructMovie(Queue<String> attributes) {
        Long id = getNextID();
        String creationDate = LocalDate.now().toString();
        String name = attributes.poll();
        Coordinates coordinates = constructCoordinates(attributes);
        MovieGenre genre = MovieGenre.valueOf(attributes.poll());
        MpaaRating rating = MpaaRating.valueOf(attributes.poll());
        Long oscarsCount = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        Person operator = constructOperator(attributes);

        return new Movie(id, creationDate, name, coordinates, genre, rating, oscarsCount, operator);
    }

    private static Coordinates constructCoordinates(Queue<String> attributes) {
        Double x = Double.parseDouble(Objects.requireNonNull(attributes.poll()));
        Float y = Float.parseFloat(Objects.requireNonNull(attributes.poll()));
        return new Coordinates(x, y);
    }

    private static Person constructOperator(Queue<String> attributes) {
        String name = attributes.poll();
        Long height = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        Color eyeColor = Color.valueOf(attributes.poll());
        Country nationality = Country.valueOf(attributes.poll());
        Location location = constructLocation(attributes);
        return new Person(name, height, eyeColor, nationality, location);
    }

    public static Location constructLocation(Queue<String> attributes) {
        Float x = Float.parseFloat(Objects.requireNonNull(attributes.poll()));
        Float y = Float.parseFloat(Objects.requireNonNull(attributes.poll()));
        Long z = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        return new Location(x, y, z);
    }

    public static Movie constructMovie(Long id, String creationDate, Queue<String> attributes) {
        return null;
    }

    public static void setNextID(Long newNextID) {
        nextID = newNextID;
    }

}
