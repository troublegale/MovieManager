package letsgo.lab6.common.network;

import java.io.Serializable;

public record Request(String commandName, String argument) implements Serializable {
}
