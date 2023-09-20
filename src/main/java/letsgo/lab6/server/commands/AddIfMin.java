package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class AddIfMin implements Command {

    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        Queue<String> attributes = new ArrayDeque<>(List.of(argument.split("\n")));
        return collectionManager.addIfMin(attributes);
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
