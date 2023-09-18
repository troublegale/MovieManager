package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;
import letsgo.lab6.entities.Movie;

public class RemoveIfGreater extends Command {
    public RemoveIfGreater(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Movie movie = null;
        return collectionManager.removeIfGreater(movie);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
