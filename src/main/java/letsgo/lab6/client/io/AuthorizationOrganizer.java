package letsgo.lab6.client.io;

import letsgo.lab6.client.TCPClient;
import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.common.network.AuthResponse;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class AuthorizationOrganizer {

    private final Console console;
    private final TCPClient client;

    public AuthorizationOrganizer(Console console, TCPClient client) {
        this.console = console;
        this.client = client;
    }

    public void authorize() {
        boolean auth = false;
        while (!auth) {
            System.out.println("Для использования программы необходимо авторизоваться.\n" +
                    "Для регистрации введите 'R', для входа введите 'L'.\n" +
                    "Для выхода введите 'exit'.");
            try {
                String input = console.getInput();
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
        return sendToAuthorize(authRequest);
    }

    private String[] getAuthDetails(boolean registerOrLogin) throws NoSuchElementException {
        System.out.println("Для отмены авторизации введите '/q'.");
        String username = getUsername();
        if (username == null) {
            return null;
        }
        String password = getPassword(registerOrLogin);
        if (password == null) {
            return null;
        }
        return new String[]{username, password};
    }

    private String getUsername() {
        String username;
        boolean empty = true;
        do {
            System.out.print("Имя пользователя: ");
            username = console.getInput();
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

    private String getPassword(boolean registerOrLogin) {
        String password = null;
        boolean flag = true;
        if (registerOrLogin) {
            System.out.println("""
                    Требования к паролю:
                    Пароль должен содержать каждый из следующих символов:
                    Строчная и прописная латинские буквы; цифра; один из символов '_', '-', '.'
                    (использование других символов запрещено);
                    Длина пароля должна быть от 8 до 64 символов""");
        }
        while (flag) {
            System.out.print("Пароль: ");
            password = console.getInput();
            if (password.equalsIgnoreCase("/q")) {
                return null;
            }
            if (password.isBlank()) {
                System.out.println("Пароль не может быть пустым!");
                continue;
            }
            if (!registerOrLogin) {
                flag = false;
                continue;
            }
            flag = validatePassword(password) == null;
        }
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

    private boolean sendToAuthorize(AuthRequest authRequest) {
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

}
