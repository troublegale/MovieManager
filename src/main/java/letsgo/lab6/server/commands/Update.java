package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Update implements Command {

    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        Queue<String> attributes = new ArrayDeque<>(List.of(argument.split("\n")));
        return collectionManager.updateElement(attributes, username);
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента с заданным id.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<long id>";
    }
}
