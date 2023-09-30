package letsgo.lab6.server.runnables;

import letsgo.lab6.common.network.AuthResponse;
import letsgo.lab6.common.network.CommandResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class Sender implements Runnable {

    private final CommandResponse commandResponse;
    private final AuthResponse authResponse;
    private final ObjectOutputStream objectOutputStream;
    private final SocketChannel clientChannel;

    public Sender(CommandResponse commandResponse, ObjectOutputStream objectOutputStream, SocketChannel clientChannel) {
        this.commandResponse = commandResponse;
        authResponse = null;
        this.objectOutputStream = objectOutputStream;
        this.clientChannel = clientChannel;
    }

    public Sender(AuthResponse authResponse, ObjectOutputStream objectOutputStream, SocketChannel clientChannel) {
        commandResponse = null;
        this.authResponse = authResponse;
        this.objectOutputStream = objectOutputStream;
        this.clientChannel = clientChannel;
    }

    @Override
    public void run() {
        try {
            if (authResponse != null) {
                objectOutputStream.writeObject(authResponse);
                System.out.println("Отправлен ответ: " + authResponse.message());
            } else if (commandResponse != null) {
                objectOutputStream.writeObject(commandResponse);
                System.out.println("Отправлен ответ:\n" + commandResponse.message());
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("Не удалось отправить ответ.");
        }
    }
}
