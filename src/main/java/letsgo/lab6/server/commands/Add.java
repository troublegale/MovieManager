package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Add implements Command {

    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        Queue<String> attributes = new ArrayDeque<>(List.of(argument.split("\n")));
        return collectionManager.addElement(attributes, username);
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
