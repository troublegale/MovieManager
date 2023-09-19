package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.entities.MovieGenre;

public class CountGreaterThanGenre extends Command {
    public CountGreaterThanGenre(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        MovieGenre genre = MovieGenre.valueOf(argument.toUpperCase());
        return collectionManager.countGreaterThanGenre(genre);
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
