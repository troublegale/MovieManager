package letsgo.lab6.server.entities;

public record User(Long id, String name, String passwordDigest, String salt) {
}
