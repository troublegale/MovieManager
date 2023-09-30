package letsgo.lab6.server.runnables;

import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.common.network.CommandRequest;
import letsgo.lab6.server.managers.handlers.CommandManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Receiver implements Runnable {

    private final int port;
    private final CommandManager commandManager;

    public Receiver(int port, CommandManager commandManager) {
        this.port = port;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Сервер запущен.");
            while (true) {
                try {
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    if (clientChannel != null) {
                        handleClient(clientChannel);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при обработке клиента.");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при открытии сетевого канала.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка.");
            System.exit(1);
        }
    }

    private void handleClient(SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(clientChannel.socket().getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientChannel.socket().getOutputStream());
        Object request = objectInputStream.readObject();
        if (request instanceof AuthRequest authRequest) {
            System.out.println("Получен запрос на авторизацию: [" + authRequest.username() + ":" +
                    authRequest.password() + (authRequest.registerOrLogin() ? "] регистрация" : "] вход"));
            delegateAuthorization(objectOutputStream, authRequest, clientChannel);
        } else if (request instanceof CommandRequest commandRequest) {
            System.out.println("Получен запрос от " + commandRequest.username() +
                    ": " + commandRequest.commandName() + " " + (commandRequest.argument() == null ?
                    "" : commandRequest.argument()));
            delegateExecution(objectOutputStream, commandRequest, clientChannel);
        }
    }

    private void delegateAuthorization(ObjectOutputStream objectOutputStream, AuthRequest request,
                                       SocketChannel clientChannel) {
        Thread thread = new Thread(new Authorizer(request, objectOutputStream, clientChannel));
        thread.start();
    }

    private void delegateExecution(ObjectOutputStream objectOutputStream, CommandRequest request,
                                   SocketChannel clientChannel) {
        Thread thread = new Thread(new Executor(commandManager, objectOutputStream, request, clientChannel));
        thread.start();
    }
}
