package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;

import java.util.Deque;

public class SumOfOscarsCount extends Command {
    public SumOfOscarsCount(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Deque<Movie> movieDeque = collectionManager.getMovieDeque();
        if (movieDeque.isEmpty()) {
            return "В коллекции нет элементов, и Оскаров тоже :(\n";
        }
        long sumOfOscarsCount = movieDeque.stream().mapToLong(Movie::getOscarsCount).sum();
        return "Количество всех Оскаров у всех фильмов коллекции - " + sumOfOscarsCount + ".\n";
    }

    @Override
    public String getDescription() {
        return "вывести на экран количество всех Оскаров у всех фильмов коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
