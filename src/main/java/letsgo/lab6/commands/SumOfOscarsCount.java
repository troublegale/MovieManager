package letsgo.lab6.commands;

import letsgo.lab6.managers.CollectionManager;

public class SumOfOscarsCount extends Command {
    public SumOfOscarsCount(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String argument) {
        return collectionManager.getSumOfOscarsCount();
    }

    @Override
    public String getDescription() {
        return "вывести на экран количество всех Оскаров у всех фильмов коллекции.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
