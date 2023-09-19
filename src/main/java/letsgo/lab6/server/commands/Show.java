package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class Show extends Command {

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public String execute(String argument) {
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