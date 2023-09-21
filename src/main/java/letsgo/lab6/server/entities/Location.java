package letsgo.lab6.server.entities;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {

    private Long z;
    private Float y;
    private Float x;
    private String name; //Длина строки не должна быть больше 870, Поле не может быть null

    public Location() {

    }



    public Location(Float x, Float y, Long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + "; " + z + ")";
    }
}
