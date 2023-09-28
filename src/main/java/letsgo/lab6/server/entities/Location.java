package letsgo.lab6.server.entities;

public record Location(Float x, Float y, Long z, String name) {

    @Override
    public String toString() {
        return "(" + x + "; " + y + "; " + z + ")";
    }
}
