package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public abstract class Command {

    protected CollectionManager collectionManager;

    public Command(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public abstract String execute(String argument);
    abstract String getDescription();
    abstract String getArgumentRequirement();
}
