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
            sb.append("Пока в коллекции ничего нет");
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
        StringBuilder sb = new StringBuilder();
        movieDeque.forEach(m -> sb.append(m).append("\n"));
        return sb.toString();
    }

}
