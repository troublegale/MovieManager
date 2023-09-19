package letsgo.lab6.common;

import java.io.Serializable;

public record Request(String commandName, String argument) implements Serializable {
}
