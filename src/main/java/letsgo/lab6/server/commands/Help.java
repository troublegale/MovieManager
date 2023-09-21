package letsgo.lab6.server.commands;

import java.util.Map;

public class Help implements Command {

    private final Map<String, Command> commandMap;

    public Help(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }
    @Override
    public String execute(String argument) {
        StringBuilder sb = new StringBuilder();
        commandMap.forEach((k, v) -> sb.append(k)
                .append(v.getArgumentRequirement() == null ? "" : " " + v.getArgumentRequirement())
                .append(" - ").append(v.getDescription()).append("\n"));
        return sb.toString();
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
