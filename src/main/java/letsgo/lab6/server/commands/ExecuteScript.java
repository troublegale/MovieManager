package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class ExecuteScript implements Command {

    private final CollectionManager collectionManager;

    public ExecuteScript(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        return null;
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт с указанным именем.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<string file_name>";
    }
}
