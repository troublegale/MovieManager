package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class Exit extends Command {
    public Exit(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(Object argument) {
        return "Выход из программы...\nДо свидания!";
    }

    @Override
    public String getDescription() {
        return "выход из программы.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
