package letsgo.lab6.client.validation.validators;

import letsgo.lab6.client.validation.DataType;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.client.validation.constraints.ArgumentConstraints;
import letsgo.lab6.common.enums.MovieGenre;

import java.util.HashMap;
import java.util.Map;

public class CommandValidator {

    private static final Map<String, ArgumentConstraints> commandConstraintsMap = new HashMap<>();

    static {
        commandConstraintsMap.put("add", new ArgumentConstraints(true));
        commandConstraintsMap.put("add_if_min", new ArgumentConstraints(true));
        commandConstraintsMap.put("clear", new ArgumentConstraints());
        commandConstraintsMap.put("count_greater_than_genre", new ArgumentConstraints(DataType.MOVIE_GENRE));
        commandConstraintsMap.put("exit", new ArgumentConstraints());
        commandConstraintsMap.put("group_counting_by_genre", new ArgumentConstraints());
        commandConstraintsMap.put("help", new ArgumentConstraints());
        commandConstraintsMap.put("info", new ArgumentConstraints());
        commandConstraintsMap.put("remove_by_id", new ArgumentConstraints(DataType.LONG));
        commandConstraintsMap.put("remove_head", new ArgumentConstraints());
        commandConstraintsMap.put("remove_if_greater", new ArgumentConstraints());
        commandConstraintsMap.put("show", new ArgumentConstraints());
        commandConstraintsMap.put("sum_of_oscars_count", new ArgumentConstraints());
        commandConstraintsMap.put("update", new ArgumentConstraints(DataType.LONG, true));
    }

    public static ValidationResult validateCommand(String[] inputWords) {
        String command = inputWords[0].toLowerCase();
        ArgumentConstraints constraints = commandConstraintsMap.get(command);
        if (constraints == null) {
            String message = "Неизвестная команда '" + command + "'. Для получения информации о доступных командах" +
                    "используйте 'help'.";
            return new ValidationResult(false, message, false);
        }
        return validateArgument(inputWords, constraints);
    }

    private static ValidationResult validateArgument(String[] inputWords, ArgumentConstraints constraints) {
        if (!argumentIsPresent(inputWords, constraints.isNullable())) {
            String message = "Команде '" + inputWords[0].toLowerCase() + "' требуется аргумент!";
            return new ValidationResult(false, message, false);
        }
        if (constraints.isNullable()) {
            return new ValidationResult(true, null, constraints.isFurtherInputRequired());
        }
        return validateArgumentValue(inputWords, constraints);
    }

    private static boolean argumentIsPresent(String[] inputWords, boolean nullable) {
        if (nullable) {
            return true;
        }
        return inputWords.length > 1;
    }

    private static ValidationResult validateArgumentValue(String[] inputWords, ArgumentConstraints constraints) {
        switch (constraints.getDataType()) {
            case LONG -> {
                try {
                    return new ValidationResult(true, String.valueOf(Long.parseLong(inputWords[1])),
                            constraints.isFurtherInputRequired());
                } catch (NumberFormatException e) {
                    String message = "Неверный тип аргумента для команды '" + inputWords[0] + "'. " +
                            "Требуется Long.";
                    return new ValidationResult(false, message, false);
                }
            }
            case MOVIE_GENRE -> {
                try {
                    return new ValidationResult(true, MovieGenre.valueOf(inputWords[1].toUpperCase()).toString(),
                            constraints.isFurtherInputRequired());
                } catch (IllegalArgumentException e) {
                    String message = "Неверный тип аргумента для команды '" + inputWords[0] + "'." +
                            "Требуется MovieGenre(ACTION, DRAMA, TRAGEDY).";
                    return new ValidationResult(false, message, false);
                }
            }
            default -> {
                return null;
            }
        }
    }

}
