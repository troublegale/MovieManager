package letsgo.lab6.server.managers;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import letsgo.lab6.server.entities.Movie;

import java.util.*;

@XmlRootElement(name = "collection")
public class CollectionManager {

    @XmlElement(name = "movies")
    private ArrayDeque<Movie> movieDeque;

    public CollectionManager() {

    }

    public CollectionManager(ArrayDeque<Movie> movieDeque) {
        this.movieDeque = movieDeque;
    }

    public void setMovieDeque(ArrayDeque<Movie> movieDeque) {
        this.movieDeque = movieDeque;
    }

    public Deque<Movie> getMovieDeque() {
        return movieDeque;
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

    public String addElement(Queue<String> attributes) {
        Movie movie = EntityManager.constructMovie(attributes);
        return addElement(movie);
    }

    public String addElement(Movie movie) {
        movieDeque.add(movie);
        return "Следующий элемент был добавлен в коллекцию:\n" + movie + "\n";
    }

    public String addIfMin(Queue<String> attributes) {
        if (movieDeque.isEmpty()) {
            return addElement(attributes);
        }
        Movie movie = EntityManager.constructMovie(attributes);
        Movie minMovie = movieDeque.stream().min(Movie::compareTo).orElseThrow();
        if (movie.compareTo(minMovie) < 0) {
            return addElement(movie);
        }
        return "Данный элемент не был добавлен в коллекцию, " +
                "так как он больше либо равен одному из уже добавленных элементов.\n";
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
        movieDeque.remove(oldMovie);
        String creationDate = oldMovie.getCreationDate();
        Movie newMovie = EntityManager.constructMovie(id, creationDate, attributes);
        movieDeque.add(newMovie);
        return "Старый элемент:\n" + oldMovie + "\nБыл заменён на новый:\n" + newMovie + "\n";
    }

    public Long getMaxID() {
        try {
            return movieDeque.stream().mapToLong(Movie::getId).max().orElseThrow();
        } catch (NoSuchElementException e) {
            return 1L;
        }
    }

    public void save() {
        FileManager.writeCollectionIntoFile(this);
    }

}
