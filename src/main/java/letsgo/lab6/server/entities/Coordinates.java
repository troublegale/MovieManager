package letsgo.lab6.server.entities;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coordinates {
    private Double x;

    private Float y; //Поле не может быть null

    public Coordinates() {

    }



    public Coordinates(Double x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
