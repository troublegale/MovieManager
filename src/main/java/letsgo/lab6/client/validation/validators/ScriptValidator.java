package letsgo.lab6.client.validation.validators;

import letsgo.lab6.client.validation.ConstraintsType;
import letsgo.lab6.client.validation.exceptions.InvalidScriptException;
import letsgo.lab6.client.validation.exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static letsgo.lab6.client.validation.ConstraintsType.*;

public class ScriptValidator {

    private final Scanner scanner;
    private static final List<String> allScriptsContent = new ArrayList<>();
    private static final Deque<File> scriptDeque = new ArrayDeque<>();
    private static final Queue<ConstraintsType> constraintsInOrder = new ArrayDeque<>();

    static {
        Collections.addAll(constraintsInOrder, MOVIE_NAME, MOVIE_GENRE, MOVIE_RATING,
                MOVIE_OSCARS, COORDINATES_X, COORDINATES_Y, OPERATOR_NAME, OPERATOR_HEIGHT, OPERATOR_EYE_COLOR,
                OPERATOR_NAME, LOCATION_X, LOCATION_Y, LOCATION_Z, LOCATION_NAME);
    }

    public ScriptValidator(String scriptName) throws FileNotFoundException {
        try {
            File script = new File(scriptName).getAbsoluteFile();
            scanner = new Scanner(script);
            scriptDeque.addLast(script);
        } catch (SecurityException e) {
            throw new FileNotFoundException();
        }
    }

    private String next() {
        return scanner.nextLine().trim();
    }

    public String getFinalScript() throws FileNotFoundException, InvalidScriptException, ScriptRecursionException {
        try {
            if (validateScript()) {
                StringBuilder sb = new StringBuilder();
                allScriptsContent.forEach(s -> sb.append(s).append("\n"));
                allScriptsContent.clear();
                return sb.toString();
            } else {
                allScriptsContent.clear();
                scriptDeque.clear();
                throw new InvalidScriptException();
            }
        } catch (ScriptRecursionException e) {
            allScriptsContent.clear();
            scriptDeque.clear();
            throw new ScriptRecursionException();
        }
    }

    private boolean validateScript() throws FileNotFoundException, ScriptRecursionException {
        while (scanner.hasNextLine()) {
            String currentString = next();
            if (currentString.isBlank()) {
                continue;
            }
            String[] currentWords = currentString.split("\\s+", 2);
            if (!CommandValidator.validateCommand(currentWords).valid()) {
                return false;
            }
            if (currentWords[0].equalsIgnoreCase("add") || currentWords[0].equalsIgnoreCase("update")
                    || currentWords[0].equalsIgnoreCase("add_if_min")) {
                allScriptsContent.add(currentString);
                if (!validateMovieFields()) {
                    return false;
                } else {
                    continue;
                }
            } else if (currentWords[0].equalsIgnoreCase("execute_script")) {
                if (!validateNestedScript(currentWords[1])) {
                    return false;
                }
            }
            if (!currentWords[0].equalsIgnoreCase("execute_script")) {
                allScriptsContent.add(currentString);
            }
        }
        return true;
    }

    private boolean checkRecursion(String scriptName) {
        File file = new File(scriptName).getAbsoluteFile();
        return !scriptDeque.contains(file);
    }

    private boolean validateMovieFields() {
        try {
            for (ConstraintsType constraints : constraintsInOrder) {
                String currentString = next();
                if (!AttributesValidator
                        .validateAttribute(currentString, AttributesValidator.getConstraints(constraints)).valid()) {
                    return false;
                }
                allScriptsContent.add(currentString);
            }
            return true;
        } catch (NoSuchElementException | IllegalStateException e) {
            return false;
        }
    }


    private boolean validateNestedScript(String scriptName) throws FileNotFoundException, ScriptRecursionException {
        if (checkRecursion(scriptName)) {
            ScriptValidator scriptValidator = new ScriptValidator(scriptName);
            boolean result = scriptValidator.validateScript();
            scriptDeque.removeLast();
            return result;
        } else {
            throw new ScriptRecursionException();
        }
    }

}
