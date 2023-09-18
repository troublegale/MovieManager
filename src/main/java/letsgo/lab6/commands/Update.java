package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Update extends Command {
    public Update(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        long id = Long.parseLong(argument);
        return collectionManager.updateElement(id);
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента с заданным id.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<long id>";
    }
}
