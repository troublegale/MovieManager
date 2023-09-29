package letsgo.lab6.server.managers;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.databaseManagers.dml.MovieDMLManager;

import java.sql.SQLException;
import java.util.*;

public class CollectionManager {

    private final ArrayDeque<Movie> movieDeque;


    public CollectionManager(ArrayDeque<Movie> movieDeque) {
        this.movieDeque = movieDeque;
    }


    public Deque<Movie> getMovieDeque() {
        return movieDeque;
    }

    public String clearCollection(String username) {
        List<Movie> ownedMovies = movieDeque.stream().filter(m -> m.getCreatorUsername().equals(username)).toList();
        if (ownedMovies.isEmpty()) {
            return "В коллекции нет элементов, принадлежащих вам.\n";
        }
        List<Long> ids = ownedMovies.stream().mapToLong(Movie::getId).boxed().toList();
        try {
            MovieDMLManager.deleteMovies(ids);
            movieDeque.removeIf(m -> m.getCreatorUsername().equals(username));
            return "Ваши элементы были удалены.\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элементы не были удалены.\n";
        }
    }

    private Movie findById(Long id) {
        if (movieDeque.stream().mapToLong(Movie::getId).anyMatch(n -> n == id)) {
            return movieDeque.stream().filter(m -> m.getId().equals(id)).toList().get(0);
        }
        return null;
    }

    public String removeById(Long id, String username) {
        Movie movie = findById(id);
        if (movie == null) {
            return "В коллекции нет элемента с таким id.\n";
        }
        if (!movie.getCreatorUsername().equals(username)) {
            return "Элемент с этим ID не принадлежит вам!";
        }
        try {
            MovieDMLManager.deleteMovie(id);
            movieDeque.remove(movie);
            return "Следующий элемент был удалён из коллекции:\n" + movie + "\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элемент не был удалён.\n";
        }
    }

    public String removeHead(String username) {
        Movie movie = movieDeque.peek();
        if (movie == null) {
            return "Коллекция пуста.\n";
        }
        if (!movie.getCreatorUsername().equals(username)) {
            return "Первый элемент коллекции вам не принадлежит.\n";
        }
        try {
            MovieDMLManager.deleteMovie(movie.getId());
            movieDeque.poll();
            return "Следующий элемент был удалён из коллекции:\n" + movie + "\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элемент не был удалён.\n";
        }
    }

    public String addElement(Queue<String> attributes, String username) {
        Movie movie = EntityManager.constructMovie(attributes, username);
        return addElement(movie, username);
    }

    public String addElement(Movie movie, String username) {
        try {
            Long id = MovieDMLManager.addMovie(movie, username);
            movie.setId(id);
            movieDeque.add(movie);
            return "Следующий элемент был добавлен в коллекцию:\n" + movie + "\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элемент не был добавлен.\n";
        }
    }

    public String addIfMin(Queue<String> attributes, String username) {
        if (movieDeque.isEmpty()) {
            return addElement(attributes, username);
        }
        Movie movie = EntityManager.constructMovie(attributes, username);
        Movie minMovie = movieDeque.stream().min(Movie::compareTo).orElseThrow();
        if (movie.compareTo(minMovie) < 0) {
            return addElement(movie, username);
        }
        return "Данный элемент не был добавлен в коллекцию, " +
                "так как он больше либо равен одному из уже добавленных элементов.\n";
    }

    public String removeIfGreater(Movie movie, String username) {
        List<Movie> ownedMovies = movieDeque.stream().filter(m -> m.getCreatorUsername().equals(username)).toList();
        if (ownedMovies.isEmpty()) {
            return "В коллекции нет элементов, принадлежащих вам.\n";
        }
        List<Movie> greaterMovies = ownedMovies.stream().filter(m -> m.compareTo(movie) > 0).toList();
        if (greaterMovies.isEmpty()) {
            return "Ни один элемент не был удалён.\n";
        }
        List<Long> ids = greaterMovies.stream().mapToLong(Movie::getId).boxed().toList();
        try {
            MovieDMLManager.deleteMovies(ids);
            int before = movieDeque.size();
            movieDeque.removeIf(m -> m.compareTo(movie) > 0);
            int after = movieDeque.size();
            return "Было удалено элементов: " + (before - after) + "\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элементы не были удалены.\n";
        }
    }

    public String updateElement(Queue<String> attributes, String username) {
        Long id = Long.parseLong(Objects.requireNonNull(attributes.poll()));
        Movie oldMovie = findById(id);
        if (oldMovie == null) {
            return "В коллекции нет элемента с таким id.\n";
        }
        if (!oldMovie.getCreatorUsername().equals(username)) {
            return "Элемент с этим ID не принадлежит вам!";
        }
        String creationDate = oldMovie.getCreationDate();
        Movie movie = EntityManager.constructMovie(creationDate, attributes, username);
        movie.setId(id);
        try {
            MovieDMLManager.updateMovie(id, movie);
            movieDeque.remove(oldMovie);
            movieDeque.add(movie);
            return "Старый элемент:\n" + oldMovie + "\nБыл заменён на новый:\n" + movie + "\n";
        } catch (SQLException e) {
            return "Возникла ошибка при взаимодействии с базой данных. Элемент не был обновлён.\n";
        }
    }

}
