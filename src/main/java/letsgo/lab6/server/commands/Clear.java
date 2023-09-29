package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class Clear implements Command {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        return collectionManager.clearCollection(username);
    }

    @Override
    public String getDescription() {
        return "удалить все элементы из коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
