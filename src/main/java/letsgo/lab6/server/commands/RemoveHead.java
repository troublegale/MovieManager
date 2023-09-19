package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class RemoveHead extends Command {
    public RemoveHead(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        return collectionManager.removeHead();
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
