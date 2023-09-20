package letsgo.lab6.client.validation;

import java.util.HashMap;
import java.util.Map;

public class CommandValidator {

    private static final Map<String, Constraints> commandConstraintsMap = new HashMap<>();

    static {
        commandConstraintsMap.put("add", new Constraints());
        commandConstraintsMap.put("add_if_min", new Constraints());
        commandConstraintsMap.put("clear", new Constraints());
        commandConstraintsMap.put("count_greater_than_genre", new Constraints(DataType.MOVIE_GENRE));
        commandConstraintsMap.put("exit", new Constraints());
        commandConstraintsMap.put("group_counting_by_genre", new Constraints());
        commandConstraintsMap.put("help", new Constraints());
        commandConstraintsMap.put("info", new Constraints());
        commandConstraintsMap.put("remove_by_id", new Constraints(DataType.LONG));
        commandConstraintsMap.put("remove_head", new Constraints());
        commandConstraintsMap.put("remove_if_greater", new Constraints());
        commandConstraintsMap.put("show", new Constraints());
        commandConstraintsMap.put("sum_of_oscars_count", new Constraints());
        commandConstraintsMap.put("update", new Constraints(DataType.LONG));
    }

    public static String validateCommand(String input) {
        String[] inputWords = input.split("\\s+");
        Constraints constraints = commandConstraintsMap.get(inputWords[0]);
        if (constraints == null) {
            return "Несуществующая команда. Используйте 'help' для получения информации о доступных командах.";
        }
        if (argumentIsPresent(inputWords, constraints.isNullable())) {
            return null;
        }
        return null;
    }

    private static boolean argumentIsPresent(String[] inputWords, boolean mandatory) {
        if (!mandatory) {
            return true;
        }
        return inputWords.length > 1;
    }

}
