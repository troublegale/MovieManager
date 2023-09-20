package letsgo.lab6.client.validation;

import java.util.HashMap;
import java.util.Map;

public class InputValidator {

    private static final Map<String, Constraints> commandConstraintsMap = new HashMap<>();
    private static final Map<String, Constraints> dataConstraintsMap = new HashMap<>();

    static {
        commandConstraintsMap.put("remove_by_id", new Constraints());

        dataConstraintsMap.put("long", new Constraints());
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
