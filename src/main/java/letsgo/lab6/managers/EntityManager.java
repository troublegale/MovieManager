package letsgo.lab6.managers;

import letsgo.lab6.entities.Movie;

public class EntityManager {

    private static Long nextID = 1L;

    public static Movie constructMovie() {
        nextID++;
        return null;
    }

    public static Movie constructMovie(Long id) {
        return null;
    }

    public static void setNextID(Long newNextID) {
        nextID = newNextID;
    }

}
