package letsgo.lab6.server.commands;

import java.util.Map;
import java.util.Scanner;

public class ExecuteScript implements Command {

    private final Map<String, Command> commandMap;

    public ExecuteScript(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
        commandMap.put("execute_script", this);
    }

    @Override
    public String execute(String argument, String username) {
        Scanner scanner = new Scanner(argument);
        StringBuilder sb = new StringBuilder("Executing script.\n");
        while (scanner.hasNextLine()) {
            String executionResult;
            try {
                executionResult = emulateCommandManager(scanner, username);
            } catch (IllegalStateException e) {
                sb.append("Завершения работы скрипта по команде 'exit'.\n");
                return sb.toString();
            }
            if (executionResult != null) {
                sb.append(executionResult).append("\n");
            }
        }
        return sb.append("Script finished execution.\n").toString();
    }

    private String emulateCommandManager(Scanner scanner, String username) throws IllegalStateException {
        String currentString = scanner.nextLine();
        String[] words = currentString.split("\\s+", 2);
        if (words[0].isBlank()) {
            return null;
        }
        if (words[0].equalsIgnoreCase("exit")) {
            throw new IllegalStateException();
        }
        if (words[0].equalsIgnoreCase("add") || words[0].equalsIgnoreCase("add_if_min")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 14; i++) {
                sb.append(scanner.nextLine()).append("\n");
            }
            return commandMap.get(words[0]).execute(sb.toString(), username);
        } else if (words[0].equalsIgnoreCase("update")) {
            StringBuilder sb = new StringBuilder(words[1]);
            for (int i = 0; i < 14; i++) {
                sb.append(scanner.nextLine()).append("\n");
            }
            return commandMap.get(words[0]).execute(sb.toString(), username);
        } else {
            if (words.length > 1) {
                return commandMap.get(words[0]).execute(words[1], username);
            } else {
                return commandMap.get(words[0]).execute(null, username);
            }
        }
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт с указанным именем.";
    }

    @Override
    public String getArgumentRequirement() {
        return "<string file_name>";
    }
}
