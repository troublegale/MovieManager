package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.EntityManager;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class RemoveIfGreater implements Command {

    private final CollectionManager collectionManager;

    public RemoveIfGreater(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        Queue<String> attributes = new ArrayDeque<>();
        Collections.addAll(attributes, argument.split("\n"));
        Movie movie = EntityManager.constructMovie(attributes, username);
        return collectionManager.removeIfGreater(movie, username);
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение которых больше заданного.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
