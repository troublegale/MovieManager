package letsgo.lab6.server;

import letsgo.lab6.common.network.Request;
import letsgo.lab6.common.network.Response;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.CommandManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private final int port;
    private final CommandManager commandManager;

    public TCPServer(int port, CollectionManager collectionManager) {
        this.port = port;
        commandManager = new CommandManager(collectionManager);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                handleClient(objectInputStream, objectOutputStream);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при открытии сетевого канала.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестная ошибка.");
            System.exit(1);
        }
    }

    private void handleClient(ObjectInputStream objectInputStream,
                              ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        Request request = (Request) objectInputStream.readObject();
        System.out.println("Received request: " + request.commandName() + " " + request.argument());
        String responseMessage = commandManager.execute(request.commandName(), request.argument());
        Response response = new Response(responseMessage);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        System.out.println("Sent response: " + responseMessage);
    }
}