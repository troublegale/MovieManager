package letsgo.lab6.managers;

import letsgo.lab6.models.Movie;

import java.time.LocalDate;
import java.util.Deque;

public class CollectionManager {

    private final Deque<Movie> movieDeque;
    private final LocalDate initDate;

    public CollectionManager(Deque<Movie> movieDeque) {
        this.movieDeque = movieDeque;
        this.initDate = LocalDate.now();
    }

    public String getCollectionInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Фильмы хранятся в коллекции ").append(movieDeque.getClass()).append(".\n");
        sb.append("Дата инициализации коллекции - ").append(initDate).append(".\n");
        if (movieDeque.isEmpty()) {
            sb.append("Пока в коллекции ничего нет.\n");
        } else {
            sb.append("В коллекции хранится ").append(movieDeque.size());
            sb.append(movieDeque.size() % 10 == 1 && movieDeque.size() != 11 ?
                    "элемент.\n" : "элементов(а).\n");

            int mostOscars = movieDeque.stream().mapToInt(Movie::getOscarsCount).max().orElseThrow();
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

    private Movie findById(int id) {
        if (movieDeque.stream().mapToInt(Movie::getId).anyMatch(n -> n == id)) {
            return movieDeque.stream().filter(m -> m.getId() == id).toList().get(0);
        }
        return null;
    }

    public String removeById(int id) {
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
        int sumOfOscarsCount = movieDeque.stream().mapToInt(Movie::getOscarsCount).sum();
        return "Количество всех Оскаров у всех фильмов коллекции - " + sumOfOscarsCount + ".\n";
    }

}
