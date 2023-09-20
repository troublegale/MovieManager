package letsgo.lab6.client.validation;

import java.util.HashMap;
import java.util.Map;

public class AttributesValidator {

    private static final Map<String, AttributeConstraints> attributeConstraintsMap = new HashMap<>();

    static {
        attributeConstraintsMap.put("movie_name", new AttributeConstraints(
                DataType.STRING, false, false,
                "Введите название фильма: ", "Название не может быть пустым!",
                "ты че сделал"));
        attributeConstraintsMap.put("movie_oscars", new AttributeConstraints(
                DataType.LONG, false, true,
                "Введите кол-во Оскаров у фильма: ", "Кол-во Оскаров не может быть пустым!",
                "Кол-во Оскаров должно быть целым положительным числом типа Long!"));
        attributeConstraintsMap.put("movie_genre", new AttributeConstraints(
                DataType.MOVIE_GENRE, false, false,
                "Введите жанр фильма: ", "Жанр не может быть пустым!",
                "Жанр должен принимать одно из след. значений: ACTION, DRAMA, TRAGEDY"));
        attributeConstraintsMap.put("movie_rating", new AttributeConstraints(
                DataType.MPAA_RATING, true, false,
                "Введите возрастной рейтинг фильмы (необязательно): ", "",
                "Рейтинг должен принимать одно из след. значений: G, PG, R, NC_17"));
        attributeConstraintsMap.put("operator_name", new AttributeConstraints(
                DataType.STRING, false, false,
                "Введите имя режиссёра: ", "Имя не может быть пустым!",
                "да ты че ваще"));
        attributeConstraintsMap.put("operator_height", new AttributeConstraints(
                DataType.LONG, true, true,
                "Введите рост режиссёра (необязательно): ", "",
                "Рост должен быть целым положительным числом типа Long!"));
        attributeConstraintsMap.put("operator_eye_color", new AttributeConstraints(
                DataType.COLOR, false, false,
                "Введите цвет глаз режиссёра: ", "Цвет глаз не может быть пустым!",
                "Цвет глаз должен принимать одно из след. значений: GREEN, RED, BLACK, BLUE, BROWN;"));
        attributeConstraintsMap.put("operator_nationality", new AttributeConstraints(
                DataType.COUNTRY, false, false,
                "Введите страну рождения режиссёра: ", "Страна не может быть пустой!",
                "Страна должна принимать одно из след. значений: FRANCE, VATICAN, THAILAND, NORTH_KOREA, JAPAN;"));
        attributeConstraintsMap.put("coordinates_x", new AttributeConstraints(
                DataType.DOUBLE, true, false,
                "Введите координату X (необязательно): ", "",
                "Координата X должна быть действительным числом формата Double!"));
        attributeConstraintsMap.put("location_x", new AttributeConstraints(
                DataType.FLOAT, true, false,
                "Введите координату X (необязательно): ", "",
                "Координата X должна быть действительным числом формата Float!"));
        attributeConstraintsMap.put("coordinates_y", new AttributeConstraints(
                DataType.FLOAT, false, false,
                "Введите координату Y: ", "Координата Y не может быть пустой!",
                "Координата Y должна быть действительным числом формата Float!"));
        attributeConstraintsMap.put("location_y", new AttributeConstraints(
                DataType.FLOAT, true, false,
                "Введите координату Y (необязательно): ", "",
                "Координата Y должна быть действительным числом формата Float!"));
        attributeConstraintsMap.put("location_z", new AttributeConstraints(
                DataType.LONG, true, false,
                "Введите координату Z (необязательно): ", "",
                "Координата Z должна быть целым числом типа Long!"));
        attributeConstraintsMap.put("location_name", new AttributeConstraints(
                DataType.STRING, false, true,
                "Введите название локации: ", "Имя не может быть пустым!",
                "Длина названия не должна быть больше 870!"));
    }

}
