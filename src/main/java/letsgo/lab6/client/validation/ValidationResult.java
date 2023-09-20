package letsgo.lab6.client.validation;

public record ValidationResult(boolean valid, String message, boolean furtherInputRequired) {

}
