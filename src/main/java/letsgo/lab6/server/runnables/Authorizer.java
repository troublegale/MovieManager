package letsgo.lab6.server.runnables;

import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.common.network.AuthResponse;
import letsgo.lab6.server.managers.handlers.AuthorizationManager;
import letsgo.lab6.server.utility.CachedThreadPoolStorage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Authorizer implements Runnable {

    private final String username;
    private final String password;
    private final boolean registerOrLogin;
    private final ObjectOutputStream objectOutputStream;
    private final SocketChannel clientChannel;

    public Authorizer(AuthRequest request, ObjectOutputStream objectOutputStream, SocketChannel clientChannel) {
        username = request.username();
        password = request.password();
        registerOrLogin = request.registerOrLogin();
        this.objectOutputStream = objectOutputStream;
        this.clientChannel = clientChannel;
    }
    @Override
    public void run() {
        int result;
        if (registerOrLogin) {
            result = register(username, password);
            AuthResponse response = switch (result) {
                case 1 -> new AuthResponse(false, "Пользователь с таким именем уже зарегистрирован. " +
                        "Пожалуйста, используйте другое.");
                case 2 -> new AuthResponse(false, "При авторизации на сервере произошла ошибка. " +
                        "Пожалуйста, попробуйте ещё раз.");
                default -> new AuthResponse(true, "Регистрация прошла успешно, добро пожаловать, " +
                        username + "!");
            };
            try {
                delegateResponseSending(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            result = login(username, password);
            AuthResponse response = switch (result) {
                case 1 -> new AuthResponse(false, "Пользователь с таким именем не зарегистрирован.");
                case 2 -> new AuthResponse(false, "Неверный пароль.");
                case 3 -> new AuthResponse(false, "При авторизации на сервере произошла ошибка. " +
                        "Пожалуйста, попробуйте ещё раз.");
                default -> new AuthResponse(true, "Вход прошёл успешно, добро пожаловать, " +
                        username + "!");
            };
            try {
                delegateResponseSending(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int register(String username, String password) {
        return AuthorizationManager.register(username, password);
    }

    private int login(String username, String password) {
        return AuthorizationManager.logIn(username, password);
    }

    private void delegateResponseSending(AuthResponse response) throws IOException {
        ExecutorService cachedThreadPool = CachedThreadPoolStorage.getThreadPool();
        cachedThreadPool.execute(new Sender(response, objectOutputStream, clientChannel));
    }

}
