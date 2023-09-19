package letsgo.lab6.common;

import java.io.Serializable;

public record Request(String commandName, Object argument) implements Serializable {
}
