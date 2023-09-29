package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.common.enums.MovieGenre;

import java.util.Deque;

public class CountGreaterThanGenre implements Command {

    private final CollectionManager collectionManager;

    public CountGreaterThanGenre(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        MovieGenre genre = MovieGenre.valueOf(argument.toUpperCase());
        Deque<Movie> movieDeque = collectionManager.getMovieDeque();
        long count = movieDeque.stream().filter(m -> m.getGenre().compareTo(genre) > 0).count();
        return count == 0 ? "В коллекции не нашлось подходящих элементов\n" : "Подходящих элементов: " + count + ".\n";
    }

    @Override
    public String getDescription() {
        return "вывести на экран количество элементов коллекции, у которых значение genre больше заданного.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<MovieGenre genre>";
    }
}
