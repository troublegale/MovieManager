package letsgo.lab6.server;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.FileManager;

public class ServerMain {

    public static void main(String[] args) {
        String filePath = System.getenv("MOVIES_PATH");
        if (filePath == null) {
            System.out.println("Переменная MOVIES_PATH не задана. Выход из программы.");
            System.exit(1);
        }
        FileManager.setFilePath(filePath);
        CollectionManager collectionManager = FileManager.readCollectionFromFile();
        int port = 5928;
        TCPServer server = new TCPServer(port, collectionManager);
        server.start();
    }
}
