package letsgo.lab6.common.network;

import java.io.Serializable;

public record CommandResponse(String message) implements Serializable {
}
