package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;

import java.time.LocalDate;
import java.util.Deque;

public class Info extends Command {

    public Info(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public String execute(String argument) {
        Deque<Movie> movieDeque = collectionManager.getMovieDeque();
        LocalDate initDate = collectionManager.getInitDate();
        StringBuilder sb = new StringBuilder();
        sb.append("Фильмы хранятся в коллекции ").append(movieDeque.getClass()).append(".\n");
        sb.append("Дата инициализации коллекции - ").append(initDate).append(".\n");
        if (movieDeque.isEmpty()) {
            sb.append("Пока что коллекция пуста.\n");
        } else {
            sb.append("Элементов в коллекции: ").append(movieDeque.size()).append(".\n");
            Long mostOscars = movieDeque.stream().mapToLong(Movie::getOscarsCount).max().orElseThrow();
            sb.append("Самое большое количество Оскаров у одного фильма: ").append(mostOscars).append(".\n");
        }
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "вывести на экран информацию о коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
