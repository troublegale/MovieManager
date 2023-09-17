package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Show extends Command {

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public String execute(Object argument) {
        return collectionManager.getAllElementsAsString();
    }

    @Override
    public String getDescription() {
        return "вывести на экран все элементы коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
