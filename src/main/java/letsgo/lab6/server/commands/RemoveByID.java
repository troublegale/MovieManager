package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class RemoveByID implements Command {

    private final CollectionManager collectionManager;

    public RemoveByID(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        Long id = Long.parseLong(argument);
        return collectionManager.removeById(id, username);
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции элемент с заданным id.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<int id>";
    }
}
