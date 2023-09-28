package letsgo.lab6.server.entities;

import letsgo.lab6.common.enums.Color;
import letsgo.lab6.common.enums.Country;

public record Person(String name, Long height, Color eyeColor, Country nationality, Location location) {

    @Override
    public String toString() {
        return name + " (" + nationality + ", рост: " + height + " см, глаза цвета " + eyeColor +
                ", место пребывания: " + location + ")";
    }
}
