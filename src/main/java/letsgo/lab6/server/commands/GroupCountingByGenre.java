package letsgo.lab6.server.commands;

import letsgo.lab6.server.managers.CollectionManager;

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
        return "вывести на экран количество фильмов каждого жанра в коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
