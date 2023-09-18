package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Clear extends Command {
    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(Object argument) {
        return collectionManager.clearCollection();
    }

    @Override
    public String getDescription() {
        return "удаляет все элементы из коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
