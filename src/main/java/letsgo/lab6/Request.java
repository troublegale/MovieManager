package letsgo.lab6;

import letsgo.lab6.commands.Command;

public record Request(Command command, Object argument) {
}
