package letsgo.lab6.server.commands;

import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;

import java.util.Deque;

public class GroupCountingByGenre extends Command {
    public GroupCountingByGenre(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Deque<Movie> movieDeque = collectionManager.getMovieDeque();
        if (movieDeque.isEmpty()) {
            return "Пока что коллекция пуста.\n";
        }
        StringBuilder sb = new StringBuilder();
        for (MovieGenre targetGenre : MovieGenre.values()) {
            long count = movieDeque.stream().filter(m -> m.getGenre() == targetGenre).count();
            sb.append("Фильмов в жанре ").append(targetGenre).append(count == 0 ? " нет.\n" : ": " + count + "\n");
        }
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "вывести на экран количество фильмов каждого жанра в коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
