package letsgo.lab6.client.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import letsgo.lab6.client.TCPClient;
import letsgo.lab6.client.validation.exceptions.InvalidScriptException;
import letsgo.lab6.client.validation.exceptions.ScriptRecursionException;
import letsgo.lab6.client.validation.validators.CommandValidator;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.client.validation.validators.ScriptValidator;
import letsgo.lab6.common.network.CommandRequest;
import letsgo.lab6.common.network.CommandResponse;

public class Console {

    private final TCPClient client;
    private final EntityAttributesOrganizer entityAttributesOrganizer;

    public Console(TCPClient client) {
        this.client = client;
        entityAttributesOrganizer = new EntityAttributesOrganizer(this);
    }

    private final Scanner scanner = new Scanner(System.in);

    String getInput() throws NoSuchElementException {
        return scanner.nextLine().trim();
    }

    public void start() {
        AuthorizationOrganizer authorizationOrganizer = new AuthorizationOrganizer(this, client);
        String username = authorizationOrganizer.authorize();
        initiateIO(username);
    }


    private void initiateIO(String username) {
        System.out.println("""
                Добро пожаловать. Начните вводить команды.
                Для получения информации о доступных командах используйте 'help'.
                """);
        try {
            while (true) {
                System.out.print("$ ");
                handleInput(username);
            }
        } catch (NoSuchElementException e) {
            System.out.println("До свидания!");
            System.exit(0);
        }
    }

    private void handleInput(String username) {
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
                    if (inputWords[0].equalsIgnoreCase("execute_script")) {
                        try {
                            String script = new ScriptValidator(inputWords[1]).getFinalScript();
                            CommandRequest commandRequest = new CommandRequest(inputWords[0], script, username);
                            String responseMessage = getResponseForRequest(commandRequest);
                            if (responseMessage != null) {
                                System.out.println(responseMessage);
                            }
                            return;
                        } catch (FileNotFoundException e) {
                            System.out.println("Возникла ошибка с файлом.");
                            return;
                        } catch (InvalidScriptException e) {
                            System.out.println("Файл скрипта не валиден.");
                            return;
                        } catch (ScriptRecursionException e) {
                            System.out.println("В скрипте обнаружена рекурсивные вызовы. Скрипт не валиден.");
                            return;
                        }
                    }
                    CommandRequest commandRequest;
                    String argument;
                    if (validationResult.furtherInputRequired()) {
                        argument = entityAttributesOrganizer
                                .organizeAttributesIntoString(validationResult.message());
                    } else {
                        argument = validationResult.message();
                    }
                    commandRequest = new CommandRequest(inputWords[0].toLowerCase(), argument, username);
                    String responseMessage = getResponseForRequest(commandRequest);
                    if (responseMessage != null) {
                        System.out.println(responseMessage);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Отмена.");
                }
            }
        }
    }


    private String getResponseForRequest(CommandRequest commandRequest) {
        try {
            CommandResponse commandResponse = client.getCommandResponse(commandRequest);
            return commandResponse.message();
        } catch (IOException e) {
            System.out.println("Ошибка при отправке запроса на сервер. Попробуйте еще раз.");
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка. Попробуйте еще раз.");
        }
        return null;
    }

}
