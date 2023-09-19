package letsgo.lab6.server.commands;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.EntityManager;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class Update extends Command {
    public Update(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Queue<String> attributes = new ArrayDeque<>();
        Collections.addAll(attributes, argument.split("\n"));
        return collectionManager.updateElement(attributes);
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
