package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

public class Exit extends Command {
    public Exit(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        return "Выход";
    }

    @Override
    public String getDescription() {
        return "выйти из программы.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
