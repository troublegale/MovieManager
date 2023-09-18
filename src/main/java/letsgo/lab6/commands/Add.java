package letsgo.lab6.commands;

import letsgo.lab6.entities.Movie;
import letsgo.lab6.managers.CollectionManager;
import letsgo.lab6.managers.EntityManager;

public class Add extends Command {
    public Add(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Movie movie = EntityManager.constructMovie();
        return collectionManager.addElement(movie);
    }

    @Override
    public String getDescription() {
        return "добавить элемент в коллекцию.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
