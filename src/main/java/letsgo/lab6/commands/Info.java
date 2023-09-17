package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Info extends Command {

    public Info(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public String execute(Object argument) {
        return collectionManager.getCollectionInfo();
    }

    @Override
    public String getDescription() {
        return "вывести на экран информацию о коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}