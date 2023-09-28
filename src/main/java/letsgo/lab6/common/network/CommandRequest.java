package letsgo.lab6.common.network;

import java.io.Serializable;

public record CommandRequest(String commandName, String argument, String username) implements Serializable {
}
