package letsgo.lab6.server.entities;

import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.common.enums.MpaaRating;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Movie implements Comparable<Movie> {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой

    private final Coordinates coordinates; //Поле не может быть null
    private final String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null

    private final MovieGenre genre; //Поле не может быть null

    private final MpaaRating mpaaRating; //Поле может быть null

    private final Person operator; //Поле не может быть null

    private final String creatorUsername;


    public Movie(String creationDate, String name, Coordinates coordinates, MovieGenre genre, MpaaRating mpaaRating,
                 Long oscarsCount, Person operator, String creatorUsername) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.oscarsCount = oscarsCount;
        this.operator = operator;
        this.creatorUsername = creatorUsername;
    }

    public Movie(Long id, String creationDate, String name, Coordinates coordinates, MovieGenre genre, MpaaRating mpaaRating,
                 Long oscarsCount, Person operator, String creatorUsername) {
        this(creationDate, name, coordinates, genre, mpaaRating, oscarsCount, operator, creatorUsername);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", создан " + creationDate + ", владелец - " + creatorUsername + "\n" +
                name + " (" + genre + ", " + "MPAA " + mpaaRating + ", Оскаров: " + oscarsCount + ", " +
                "снят на координатах " + coordinates + ")\n" +
                "Режиссёр: " + operator;
    }

    @Override
    public int compareTo(Movie movie) {
        return Long.compare(this.getOscarsCount(), movie.getOscarsCount());
    }


}
