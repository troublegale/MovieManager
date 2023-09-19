package letsgo.lab6.client.io;

import java.util.NoSuchElementException;
import java.util.Scanner;

import letsgo.lab6.client.validation.InputValidator;

public class Console {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("""
                Добро пожаловать. Начните вводить команды.
                Для получения информации о доступных командах используйте 'help'.
                """);
        try {
            while (true) {
                System.out.print("$ ");
                handleInput();
            }
        } catch (NoSuchElementException e) {
            System.out.println("До свидания!");
        }
    }

    private void handleInput() {
        String input = scanner.nextLine().trim();
        if (!input.isBlank()) {
            String validationResult = InputValidator.validateCommand(input);
            if (!(validationResult == null)) {
                System.out.println(validationResult);
            }
        }
    }

}
