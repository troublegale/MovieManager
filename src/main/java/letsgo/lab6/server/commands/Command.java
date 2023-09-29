package letsgo.lab6.server.commands;

public interface Command {

    String execute(String argument, String username);
    String getDescription();
    String getArgumentRequirement();
}
