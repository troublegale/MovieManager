package letsgo.lab6.common.network;

public record AuthRequest(String username, String password, boolean registerOrLogin) {
}
