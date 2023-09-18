package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class GroupCountingByGenre extends Command {
    public GroupCountingByGenre(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        return collectionManager.groupCountingByGenre();
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
