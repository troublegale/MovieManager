package letsgo.lab6.client.validation.validators;

import letsgo.lab6.client.validation.ConstraintsType;
import letsgo.lab6.client.validation.DataType;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.client.validation.constraints.AttributeConstraints;
import letsgo.lab6.common.enums.Color;
import letsgo.lab6.common.enums.Country;
import letsgo.lab6.common.enums.MovieGenre;
import letsgo.lab6.common.enums.MpaaRating;

import java.util.HashMap;
import java.util.Map;

public class AttributesValidator {

    private static final Map<ConstraintsType, AttributeConstraints> attributeConstraintsMap = new HashMap<>();

    static {
        attributeConstraintsMap.put(ConstraintsType.MOVIE_NAME, new AttributeConstraints(
                DataType.STRING, false, false,
                "Введите название фильма: ", "Название не может быть пустым!",
                "ты че сделал"));
        attributeConstraintsMap.put(ConstraintsType.MOVIE_OSCARS, new AttributeConstraints(
                DataType.LONG, false, true,
                "Введите кол-во Оскаров у фильма: ", "Кол-во Оскаров не может быть пустым!",
                "Кол-во Оскаров должно быть целым положительным числом типа Long!"));
        attributeConstraintsMap.put(ConstraintsType.MOVIE_GENRE, new AttributeConstraints(
                DataType.MOVIE_GENRE, false, false,
                "Введите жанр фильма: ", "Жанр не может быть пустым!",
                "Жанр должен принимать одно из след. значений: ACTION, DRAMA, TRAGEDY"));
        attributeConstraintsMap.put(ConstraintsType.MOVIE_RATING, new AttributeConstraints(
                DataType.MPAA_RATING, true, false,
                "Введите возрастной рейтинг фильмы (необязательно): ", "",
                "Рейтинг должен принимать одно из след. значений: G, PG, R, NC_17"));
        attributeConstraintsMap.put(ConstraintsType.OPERATOR_NAME, new AttributeConstraints(
                DataType.STRING, false, false,
                "Введите имя режиссёра: ", "Имя не может быть пустым!",
                "да ты че ваще"));
        attributeConstraintsMap.put(ConstraintsType.OPERATOR_HEIGHT, new AttributeConstraints(
                DataType.LONG, true, true,
                "Введите рост режиссёра (необязательно): ", "",
                "Рост должен быть целым положительным числом типа Long!"));
        attributeConstraintsMap.put(ConstraintsType.OPERATOR_EYE_COLOR, new AttributeConstraints(
                DataType.COLOR, false, false,
                "Введите цвет глаз режиссёра: ", "Цвет глаз не может быть пустым!",
                "Цвет глаз должен принимать одно из след. значений: GREEN, RED, BLACK, BLUE, BROWN;"));
        attributeConstraintsMap.put(ConstraintsType.OPERATOR_NATIONALITY, new AttributeConstraints(
                DataType.COUNTRY, false, false,
                "Введите страну рождения режиссёра: ", "Страна не может быть пустой!",
                "Страна должна принимать одно из след. значений: FRANCE, VATICAN, THAILAND, NORTH_KOREA, JAPAN;"));
        attributeConstraintsMap.put(ConstraintsType.COORDINATES_X, new AttributeConstraints(
                DataType.DOUBLE, true, false,
                "Введите координату X (необязательно): ", "",
                "Координата X должна быть десятичным числом формата Double!"));
        attributeConstraintsMap.put(ConstraintsType.LOCATION_X, new AttributeConstraints(
                DataType.FLOAT, true, false,
                "Введите координату X (необязательно): ", "",
                "Координата X должна быть десятичным числом формата Float!"));
        attributeConstraintsMap.put(ConstraintsType.COORDINATES_Y, new AttributeConstraints(
                DataType.FLOAT, false, false,
                "Введите координату Y: ", "Координата Y не может быть пустой!",
                "Координата Y должна быть десятичным числом формата Float!"));
        attributeConstraintsMap.put(ConstraintsType.LOCATION_Y, new AttributeConstraints(
                DataType.FLOAT, true, false,
                "Введите координату Y (необязательно): ", "",
                "Координата Y должна быть десятичным числом формата Float!"));
        attributeConstraintsMap.put(ConstraintsType.LOCATION_Z, new AttributeConstraints(
                DataType.LONG, true, false,
                "Введите координату Z (необязательно): ", "",
                "Координата Z должна быть целым числом типа Long!"));
        attributeConstraintsMap.put(ConstraintsType.LOCATION_NAME, new AttributeConstraints(
                DataType.STRING, false, true,
                "Введите название локации: ", "Имя не может быть пустым!",
                "Длина названия не должна быть больше 870!"));
    }

    public static AttributeConstraints getConstraints(ConstraintsType constraintsType) {
        return attributeConstraintsMap.get(constraintsType);
    }

    public static ValidationResult validateAttribute(String attribute, AttributeConstraints constraints) {
        if (!checkArgumentPresence(attribute, constraints.isNullable())) {
            return new ValidationResult(false, constraints.getNoArgumentMessage(), false);
        } else if (attribute.isBlank()) {
            return new ValidationResult(true, "\n", false);
        }
        return checkAttributeValue(attribute, constraints);
    }

    private static boolean checkArgumentPresence(String attribute, boolean nullable) {
        if (attribute.isBlank()) {
            return nullable;
        }
        return true;
    }

    private static ValidationResult checkAttributeValue(String attribute, AttributeConstraints constraints) {
        switch (constraints.getDataType()) {
            case LONG -> {
                return validateLong(attribute, constraints);
            }
            case STRING -> {
                return validateString(attribute, constraints);
            }
            case MOVIE_GENRE -> {
                return validateMovieGenre(attribute, constraints);
            }
            case MPAA_RATING -> {
                return validateMpaaRating(attribute, constraints);
            }
            case COLOR -> {
                return validateColor(attribute, constraints);
            }
            case COUNTRY -> {
                return validateCountry(attribute, constraints);
            }
            case DOUBLE -> {
                return validateDouble(attribute, constraints);
            }
            case FLOAT -> {
                return validateFloat(attribute, constraints);
            }
        }
        return null; //до сюда не дойдёт
    }

    private static ValidationResult validateLong(String attribute, AttributeConstraints constraints) {
        try {
            long test = Long.parseLong(attribute);
            if (constraints.isPositive() && test <= 0) {
                return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
            } else {
                return new ValidationResult(true, attribute, false);
            }
        } catch (NumberFormatException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateDouble(String attribute, AttributeConstraints constraints) {
        try {
            double test = Double.parseDouble(attribute);
            if (constraints.isPositive() && Double.compare(test, 0) <= 0 || Double.isInfinite(test)) {
                return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
            } else {
                return new ValidationResult(true, attribute, false);
            }
        } catch (NumberFormatException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateFloat(String attribute, AttributeConstraints constraints) {
        try {
            float test = Float.parseFloat(attribute);
            if (constraints.isPositive() && Float.compare(test, 0) <= 0 || Float.isInfinite(test)) {
                return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
            } else {
                return new ValidationResult(true, attribute, false);
            }
        } catch (NumberFormatException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateString(String attribute, AttributeConstraints constraints) {
        if (constraints.isPositive() && attribute.length() > 870) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        } else {
            return new ValidationResult(true, attribute, false);
        }
    }

    private static ValidationResult validateMovieGenre(String attribute, AttributeConstraints constraints) {
        try {
            MovieGenre genre = MovieGenre.valueOf(attribute.toUpperCase());
            return new ValidationResult(true, genre.toString(), false);
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateMpaaRating(String attribute, AttributeConstraints constraints) {
        try {
            MpaaRating rating = MpaaRating.valueOf(attribute.toUpperCase());
            return new ValidationResult(true, rating.toString(), false);
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateColor(String attribute, AttributeConstraints constraints) {
        try {
            Color color = Color.valueOf(attribute.toUpperCase());
            return new ValidationResult(true, color.toString(), false);
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

    private static ValidationResult validateCountry(String attribute, AttributeConstraints constraints) {
        try {
            Country country = Country.valueOf(attribute.toUpperCase());
            return new ValidationResult(true, country.toString(), false);
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, constraints.getWrongArgumentTypeMessage(), false);
        }
    }

}
