package letsgo.lab6.server.managers;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.common.enums.MovieGenre;

import java.time.LocalDate;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;

public class CollectionManager {

    private final Deque<Movie> movieDeque;
    private LocalDate initDate;

    public CollectionManager(Deque<Movie> movieDeque, LocalDate initDate) {
        this.movieDeque = movieDeque;
        this.initDate = initDate;
    }

    public String getCollectionInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Фильмы хранятся в коллекции ").append(movieDeque.getClass()).append(".\n");
        sb.append("Дата инициализации коллекции - ").append(initDate).append(".\n");
        if (movieDeque.isEmpty()) {
            sb.append("Пока что коллекция пуста.\n");
        } else {
            sb.append("В коллекции хранится ").append(movieDeque.size());
            sb.append(movieDeque.size() % 10 == 1 && movieDeque.size() != 11 ?
                    "элемент.\n" : "элементов(а).\n");

            Long mostOscars = movieDeque.stream().mapToLong(Movie::getOscarsCount).max().orElseThrow();
            sb.append("Самое большое количество Оскаров у одного фильма - ").append(mostOscars).append(".\n");
        }
        return sb.toString();
    }

    public String getAllElementsAsString() {
        if (movieDeque.isEmpty()) {
            return "Коллекция пуста.\n";
        }
        StringBuilder sb = new StringBuilder();
        movieDeque.forEach(m -> sb.append(m).append("\n\n"));
        return sb.toString();
    }

    public String clearCollection() {
        if (movieDeque.isEmpty()) {
            return "Коллекция уже пуста.\n";
        }
        int size = movieDeque.size();
        movieDeque.clear();
        return "Коллекция была очищена. Было удалено " + size + " элементов.\n";
    }

    private Movie findById(Long id) {
        if (movieDeque.stream().mapToLong(Movie::getId).anyMatch(n -> n == id)) {
            return movieDeque.stream().filter(m -> m.getId().equals(id)).toList().get(0);
        }
        return null;
    }

    public String removeById(Long id) {
        Movie movie = findById(id);
        if (movie != null) {
            movieDeque.remove(movie);
            return "Следующий элемент был удалён из коллекции:\n" + movie + "\n";
        }
        return "В коллекции нет элемента с таким id.\n";
    }

    public String removeHead() {
        Movie movie = movieDeque.poll();
        if (movie == null) {
            return "Коллекция уже пуста.\n";
        }
        return "Следующий элемент был удалён из коллекции:\n" + movie + "\n";
    }

    public String getSumOfOscarsCount() {
        if (movieDeque.isEmpty()) {
            return "В коллекции нет элементов, и Оскаров тоже :(\n";
        }
        long sumOfOscarsCount = movieDeque.stream().mapToLong(Movie::getOscarsCount).sum();
        return "Количество всех Оскаров у всех фильмов коллекции - " + sumOfOscarsCount + ".\n";
    }

    public String addElement(Movie movie) {
        if (movieDeque.isEmpty()) {
            initDate = LocalDate.parse(movie.getCreationDate());
        }
        movieDeque.add(movie);
        return "Следующий элемент был добавлен в коллекцию:\n" + movie + "\n";
    }

    public String addIfMin(Movie movie) {
        if (movieDeque.isEmpty()) {
            return addElement(movie);
        }
        Movie minMovie = movieDeque.stream().min(Movie::compareTo).orElseThrow();
        if (movie.compareTo(minMovie) < 0) {
            return addElement(movie);
        }
        return "Данный элемент не был добавлен в коллекцию, " +
                "так как он больше либо равен одному из уже добавленных элементов.\n";
    }

    public String countGreaterThanGenre(MovieGenre genre) {
        long count = movieDeque.stream().filter(m -> m.getGenre().compareTo(genre) > 0).count();
        return count == 0 ? "В коллекции не нашлось подходящих элементов\n" : "Подходящих элементов: " + count + ".\n";
    }

    public String groupCountingByGenre() {
        if (movieDeque.isEmpty()) {
            return "Пока что коллекция пуста.\n";
        }
        StringBuilder sb = new StringBuilder();
        for (MovieGenre targetGenre : MovieGenre.values()) {
            long count = movieDeque.stream().filter(m -> m.getGenre() == targetGenre).count();
            sb.append("Фильмов в жанре ").append(targetGenre).append(count == 0 ? " нет.\n" : ": " + count);
        }
        return sb.toString();
    }

    public String removeIfGreater(Movie movie) {
        if (movieDeque.isEmpty()) {
            return "Коллекция уже пуста.";
        }
        int preCount = movieDeque.size();
        movieDeque.removeIf(m -> m.compareTo(movie) < 0);
        int postCount = movieDeque.size();
        if (preCount == postCount) {
            return "Ни один элемент не был удалён.";
        }
        return "Было удалено элементов: " + (preCount - postCount);
    }

    public String updateElement(Queue<String> attributes) {
        Long id = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        Movie oldMovie = findById(id);
        if (oldMovie == null) {
            return "В коллекции нет элемента с таким id.\n";
        }
        String creationDate = oldMovie.getCreationDate();
        Movie newMovie = EntityManager.constructMovie(id, creationDate, attributes);
        movieDeque.add(newMovie);
        return "Старый элемент:\n" + oldMovie + "\nБыл заменён на новый:\n" + newMovie + "\n";
    }

}
