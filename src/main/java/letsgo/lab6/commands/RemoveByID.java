package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class RemoveByID extends Command {
    public RemoveByID(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        Long id = Long.parseLong(argument);
        return collectionManager.removeById(id);
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
