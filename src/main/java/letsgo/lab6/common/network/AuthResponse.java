package letsgo.lab6.common.network;

import java.io.Serializable;

public record AuthResponse(boolean result, String message) implements Serializable {
}
