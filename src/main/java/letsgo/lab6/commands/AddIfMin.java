package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;
import letsgo.lab6.entities.Movie;
import letsgo.lab6.managers.EntityManager;

public class AddIfMin extends Command {
    public AddIfMin(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Movie movie = EntityManager.constructMovie();
        return collectionManager.addIfMin(movie);
    }

    @Override
    public String getDescription() {
        return "добавить элемент в коллекцию, если он меньше всех элементов по значению.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
