package letsgo.lab6.server;

import letsgo.lab6.common.network.Request;
import letsgo.lab6.common.network.Response;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.CommandManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TCPServer {

    private final int port;
    private final CommandManager commandManager;
    private final ServerConsole console;

    public TCPServer(int port, CollectionManager collectionManager) {
        this.port = port;
        this.console = new ServerConsole(collectionManager);
        commandManager = new CommandManager(collectionManager);
    }

    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Сервер запущен.");

            do {
                try (SocketChannel clientChannel = serverSocketChannel.accept()) {
                    if (clientChannel != null) {
                        handleClient(clientChannel);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при обработке клиента.");
                }
            } while (!console.handleServerInput());
        } catch (IOException e) {
            System.out.println("Ошибка при открытии сетевого канала.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка.");
            System.exit(1);
        }
    }

    private void handleClient(SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(clientChannel.socket().getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientChannel.socket().getOutputStream())) {

            Request request = (Request) objectInputStream.readObject();
            System.out.println("Получен запрос: " + request.commandName() + " " +
                    (request.argument() == null ? "" : request.argument()) + "\n");
            String responseMessage = commandManager.execute(request.commandName(), request.argument());
            Response response = new Response(responseMessage);
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
            System.out.println("Отправлен ответ: " + responseMessage);
        }
    }
}