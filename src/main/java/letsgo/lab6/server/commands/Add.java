package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.EntityManager;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Add extends Command {
    public Add(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Queue<String> attributes = new ArrayDeque<>(List.of(argument.split("\n")));
        return collectionManager.addElement(attributes);
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
