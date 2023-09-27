package letsgo.lab6.common.network;

import java.io.Serializable;

public record CommandRequest(String commandName, String argument) implements Serializable {
}
