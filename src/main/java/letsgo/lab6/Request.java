package letsgo.lab6;

import java.io.Serializable;

public record Request(String commandName, Object argument) implements Serializable {
}
