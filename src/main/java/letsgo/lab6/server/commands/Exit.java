package letsgo.lab6.server.commands;

public class Exit implements Command {

    @Override
    public String execute(String argument) {
        return "Выход";
    }

    @Override
    public String getDescription() {
        return "выйти из программы.";
    }

    @Override
    public String getArgumentRequirement() {
        return null;
    }
}
