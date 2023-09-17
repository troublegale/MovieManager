package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Exit extends Command {
    public Exit(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(Object argument) {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
