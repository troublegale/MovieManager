package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class Clear extends Command {
    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        return collectionManager.clearCollection();
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