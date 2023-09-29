package letsgo.lab6.server;

import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.handlers.CommandManager;
import letsgo.lab6.server.runnables.Authorizer;
import letsgo.lab6.server.utility.ServerConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TCPServer {

    private final int port;
    private final CommandManager commandManager;;

    public TCPServer(int port, CollectionManager collectionManager) {
        this.port = port;;
        commandManager = new CommandManager(collectionManager);
    }

    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Сервер запущен.");

        } catch (IOException e) {
            System.out.println("Ошибка при открытии сетевого канала.");
            System.exit(1);
        }
    }

}