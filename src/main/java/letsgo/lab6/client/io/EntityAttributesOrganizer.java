package letsgo.lab6.client.io;

import letsgo.lab6.client.validation.ConstraintsType;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.client.validation.constraints.AttributeConstraints;
import letsgo.lab6.client.validation.validators.AttributesValidator;

import java.util.NoSuchElementException;

public class EntityAttributesOrganizer {

    private final Console console;

    public EntityAttributesOrganizer(Console console) {
        this.console = console;
    }

    public String organizeAttributesIntoString(String id) throws NoSuchElementException {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id).append("\n");
        }
        System.out.println("Составляем фильм. Для отмены введите '/q'.\n");
        sb.append(getAttribute(ConstraintsType.MOVIE_NAME)).append("\n");
        sb.append(getAttribute(ConstraintsType.MOVIE_GENRE)).append("\n");
        sb.append(getAttribute(ConstraintsType.MOVIE_RATING)).append("\n");
        sb.append(getAttribute(ConstraintsType.MOVIE_OSCARS)).append("\n");
        System.out.println("Координаты:\n");
        sb.append(getAttribute(ConstraintsType.COORDINATES_X)).append("\n");
        sb.append(getAttribute(ConstraintsType.COORDINATES_Y)).append("\n");
        System.out.println("Режиссёр:\n");
        sb.append(getAttribute(ConstraintsType.OPERATOR_NAME)).append("\n");
        sb.append(getAttribute(ConstraintsType.OPERATOR_HEIGHT)).append("\n");
        sb.append(getAttribute(ConstraintsType.OPERATOR_EYE_COLOR)).append("\n");
        sb.append(getAttribute(ConstraintsType.OPERATOR_NATIONALITY)).append("\n");
        System.out.println("Локация:\n");
        sb.append(getAttribute(ConstraintsType.LOCATION_X)).append("\n");
        sb.append(getAttribute(ConstraintsType.LOCATION_Y)).append("\n");
        sb.append(getAttribute(ConstraintsType.LOCATION_Z)).append("\n");
        sb.append(getAttribute(ConstraintsType.LOCATION_NAME)).append("\n");

        return sb.toString();
    }

    private String getAttribute(ConstraintsType constraintsType) throws NoSuchElementException {
        AttributeConstraints constraints = AttributesValidator.getConstraints(constraintsType);
        String inviteMessage = constraints.getInviteMessage();
        String attribute = null;
        System.out.print(inviteMessage);
        boolean valid = false;
        while (!valid) {
            String input = console.getInput();
            if (input.equals("/q")) {
                throw new NoSuchElementException();
            }
            ValidationResult validationResult = AttributesValidator.validateAttribute(input, constraints);
            valid = validationResult.valid();
            if (!valid) {
                System.out.println(validationResult.message());
            } else {
                attribute = input;
            }
        }
        return attribute;
    }

}
