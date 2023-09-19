package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.EntityManager;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class AddIfMin extends Command {
    public AddIfMin(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Queue<String> attributes = new ArrayDeque<>();
        Collections.addAll(attributes, argument.split("\n"));
        Movie movie = EntityManager.constructMovie(attributes);
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
