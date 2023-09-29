package letsgo.lab6.server.managers.handlers;

import letsgo.lab6.server.commands.*;
import letsgo.lab6.server.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commandMap;

    public CommandManager(CollectionManager collectionManager) {
        commandMap = new HashMap<>();
        commandMap.put("add", new Add(collectionManager));
        commandMap.put("add_if_min", new AddIfMin(collectionManager));
        commandMap.put("clear", new Clear(collectionManager));
        commandMap.put("count_greater_than_genre", new CountGreaterThanGenre(collectionManager));
        commandMap.put("group_counting_by_genre", new GroupCountingByGenre(collectionManager));
        commandMap.put("info", new Info(collectionManager));
        commandMap.put("remove_by_id", new RemoveByID(collectionManager));
        commandMap.put("remove_head", new RemoveHead(collectionManager));
        commandMap.put("remove_if_greater", new RemoveIfGreater(collectionManager));
        commandMap.put("show", new Show(collectionManager));
        commandMap.put("sum_of_oscars_count", new SumOfOscarsCount(collectionManager));
        commandMap.put("update", new Update(collectionManager));
        commandMap.put("help", new Help(commandMap));
        commandMap.put("execute_script", new ExecuteScript(commandMap));
    }

    public String execute(String commandName, String argument, String username) {
        Command command = commandMap.get(commandName);
        return command.execute(argument, username);
    }

}
