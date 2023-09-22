package letsgo.lab6.client.io;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import letsgo.lab6.client.TCPClient;
import letsgo.lab6.client.validation.validators.CommandValidator;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.common.network.Request;
import letsgo.lab6.common.network.Response;

public class Console {

    private final TCPClient client;
    private final EntityAttributesOrganizer entityAttributesOrganizer;

    public Console(TCPClient client) {
        this.client = client;
        entityAttributesOrganizer = new EntityAttributesOrganizer(this);
    }

    private final Scanner scanner = new Scanner(System.in);

    String getInput() {
        return scanner.nextLine().trim();
    }

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
            try {
                client.disconnect();
            } catch (IOException ex) {
                System.out.println("Ошибка при прерывании соединения.");
            }
        }
    }

    private void handleInput() {
        String input = getInput();
        if (input.equalsIgnoreCase("exit")) {
            throw new NoSuchElementException();
        }
        if (!input.isBlank()) {
            String[] inputWords = input.split("\\s+");
            ValidationResult validationResult = CommandValidator.validateCommand(inputWords);
            if (!validationResult.valid()) {
                System.out.println(validationResult.message());
            } else {
                try {
                    Request request;
                    String argument;
                    if (validationResult.furtherInputRequired()) {
                        argument = entityAttributesOrganizer
                                .organizeAttributesIntoString(validationResult.message());
                    } else {
                        argument = validationResult.message();
                    }
                    request = new Request(inputWords[0].toLowerCase(), argument);
                    String responseMessage = getResponseForRequest(request);
                    if (responseMessage != null) {
                        System.out.println(responseMessage);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Отмена.");
                }
            }
        }
    }

    private String getResponseForRequest(Request request) {
        try {
            Response response = client.communicateWithServer(request);
            return response.message();
        } catch (IOException e) {
            System.out.println("Ошибка при отправке запроса на сервер. Попробуйте еще раз.");
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка. Попробуйте еще раз.");
        }
        return null;
    }

}
