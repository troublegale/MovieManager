package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public abstract class Command {

    protected CollectionManager collectionManager;

    public Command(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    abstract String execute(Object argument);
    abstract String getDescription();
    abstract String getArgumentRequirement();
}
