package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class RemoveByID extends Command {
    public RemoveByID(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(Object argument) {
        int id = (int) argument;
        return collectionManager.removeById(id);
    }

    @Override
    public String getDescription() {
        return "удаляет из коллекции элемент с заданным id.";
    }

    @Override
    public String getArgumentRequirement() {
        return "(int <id>)";
    }
}
