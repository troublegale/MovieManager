package letsgo.lab6.client.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import letsgo.lab6.client.TCPClient;
import letsgo.lab6.client.validation.exceptions.InvalidScriptException;
import letsgo.lab6.client.validation.exceptions.ScriptRecursionException;
import letsgo.lab6.client.validation.validators.CommandValidator;
import letsgo.lab6.client.validation.ValidationResult;
import letsgo.lab6.client.validation.validators.ScriptValidator;
import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.common.network.AuthResponse;
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
        boolean auth = false;
        while (!auth) {
            System.out.println("Для использования программы необходимо авторизоваться.\n" +
                    "Для регистрации введите 'R', для входа введите 'L'.\n" +
                    "Для выхода введите 'exit'.");
            try {
                String input = getInput();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("До свидания!");
                    System.exit(0);
                }
                if (!(input.equalsIgnoreCase("R") || input.equalsIgnoreCase("L"))) {
                    System.out.println("Пожалуйста, выберите 'R' или 'L'.");
                } else {
                    auth = registerOrLogin(input.equalsIgnoreCase("R"));
                }
            } catch (NoSuchElementException e) {
                System.out.println("До свидания!");
                System.exit(0);
            }
        }
        initiateIO();
    }

    private boolean registerOrLogin(boolean registerOrLogin) {
        String[] details = getAuthDetails(registerOrLogin);
        if (details == null) {
            System.out.println("Отмена.\n");
            return false;
        }
        String username = details[0];
        String password = details[1];
        AuthRequest authRequest = new AuthRequest(username, password, registerOrLogin);
        return authorize(authRequest);
    }


    private String[] getAuthDetails(boolean registerOrLogin) throws NoSuchElementException {
        System.out.println("Для отмены авторизации введите '/q'.");
        String username = getUsername();
        String password = null;
        if (username == null) {
            return null;
        }
        if (registerOrLogin) {
            System.out.println("""
                    Требования к паролю:
                    Пароль должен содержать каждый из следующих символов:
                    Строчная и прописная латинские буквы; цифра; один из символов '_', '-', '.'
                    (использование других символов запрещено);
                    Длина пароля должна быть от 8 до 64 символов""");
            password = getPassword();
            if (password == null) {
                return null;
            }
            do {
                password = validatePassword(password);
            } while (password == null);
            return new String[]{username, password};
        }
        return new String[]{username, password};
    }

    private String getUsername() {
        String username;
        boolean empty = true;
        do {
            System.out.print("Имя пользователя: ");
            username = getInput();
            if (username.equalsIgnoreCase("/q")) {
                return null;
            }
            if (!username.isBlank()) {
                empty = false;
                continue;
            }
            System.out.println("Имя пользователя не может быть пустым!");
        } while (empty);
        return username;
    }

    private String getPassword() {
        String password;
        boolean empty = true;
        do {
            System.out.print("Пароль: ");
            password = getInput();
            if (password.equalsIgnoreCase("/q")) {
                return null;
            }
            if (!password.isBlank()) {
                empty = false;
                continue;
            }
            System.out.println("Пароль не может быть пустым!");
        } while (empty);
        return password;
    }

    private String validatePassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[-_.])[a-zA-Z0-9-_.]{8,64}$";
        if (!Pattern.matches(pattern, password)) {
            System.out.println("Пароль не удовлетворяет требованиям!");
            return null;
        }
        return password;
    }


    private void initiateIO() {
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
            System.exit(0);
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
                    if (inputWords[0].equalsIgnoreCase("execute_script")) {
                        try {
                            String script = new ScriptValidator(inputWords[1]).getFinalScript();
                            CommandRequest commandRequest = new CommandRequest(inputWords[0], script);
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
                    commandRequest = new CommandRequest(inputWords[0].toLowerCase(), argument);
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

    private boolean authorize(AuthRequest authRequest) {
        try {
            AuthResponse authResponse = client.authorize(authRequest);
            if (authResponse.message() != null) {
                System.out.println(authResponse.message());
            }
            return authResponse.result();
        } catch (IOException e) {
            System.out.println("Ошибка при отправке запроса на сервер. Попробуйте еще раз.\n");
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка. Попробуйте еще раз.\n");
        }
        return false;
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
