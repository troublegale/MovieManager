package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class ExecuteScript extends Command {
    public ExecuteScript(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
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
