package letsgo.lab6.common.network;

import java.io.Serializable;

public record AuthRequest(String username, String password, boolean registerOrLogin) implements Serializable {
}
