package letsgo.lab6.server.entities;

public record Coordinates(Double x, Float y) {

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
