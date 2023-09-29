package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class RemoveHead implements Command {

    private final CollectionManager collectionManager;

    public RemoveHead(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument, String username) {
        return collectionManager.removeHead(username);
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
