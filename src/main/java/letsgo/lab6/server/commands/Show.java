package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;

import java.util.Deque;

public class Show extends Command {

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public String execute(String argument) {
        Deque<Movie> movieDeque = collectionManager.getMovieDeque();
        if (movieDeque.isEmpty()) {
            return "Коллекция пуста.\n";
        }
        StringBuilder sb = new StringBuilder();
        movieDeque.forEach(m -> sb.append(m).append("\n\n"));
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "вывести на экран все элементы коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
