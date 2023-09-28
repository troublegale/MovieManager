package letsgo.lab6.server.entities;

import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.common.enums.MpaaRating;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Movie implements Comparable<Movie> {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null

    private MovieGenre genre; //Поле не может быть null

    private MpaaRating mpaaRating; //Поле может быть null

    private Person operator; //Поле не может быть null

    public Movie() {

    }

    public Movie(Long id, String creationDate, String name, Coordinates coordinates,
                 MovieGenre genre, MpaaRating mpaaRating, Long oscarsCount, Person operator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.oscarsCount = oscarsCount;
        this.operator = operator;
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

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", создан " + creationDate + "\n" +
                name + " (" + genre + ", " + "MPAA " + mpaaRating + ", Оскаров: " + oscarsCount + ", " +
                "снят на координатах " + coordinates + ")\n" +
                "Режиссёр: " + operator;
    }

    @Override
    public int compareTo(Movie movie) {
        return Long.compare(this.getOscarsCount(), movie.getOscarsCount());
    }

    public boolean allFieldsValid() {
        try {
            return (id > 0 && !name.isBlank() && coordinates.y() != null &&
                    (LocalDate.parse(creationDate).isBefore(LocalDate.now()) ||
                            LocalDate.parse(creationDate).equals(LocalDate.now())) &&
                    oscarsCount > 0 && genre != null && !operator.name().isBlank() &&
                    (operator.height() == null || operator.height() > 0) &&
                    operator.eyeColor() != null && operator.nationality() != null &&
                    operator.location().name().length() <= 870);
        } catch (NullPointerException | DateTimeParseException e) {
            return false;
        }
    }
}
