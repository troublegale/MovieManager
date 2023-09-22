package letsgo.lab6.server;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.EntityManager;
import letsgo.lab6.server.managers.FileManager;

import java.util.NoSuchElementException;

public class ServerMain {

    private static CollectionManager collectionManager;

    public static void main(String[] args) {
        String filePath = System.getenv("MOVIES_PATH");
        if (filePath == null) {
            System.out.println("Переменная MOVIES_PATH не задана. Выход из программы.");
            System.exit(1);
        }
        FileManager.setFilePath(filePath);
        int port = 5928;
        collectionManager = FileManager.readCollectionFromFile();
        Runtime.getRuntime().addShutdownHook(new Thread(ServerMain::exit));
        EntityManager.setNextID(collectionManager.getMaxID());
        TCPServer server = new TCPServer(port, collectionManager);
        try {
            server.run();
        } catch (NoSuchElementException e) {
            FileManager.writeCollectionIntoFile(collectionManager);
            System.out.println("Выход из программы.");
            System.exit(0);
        }
    }

    private static void exit() {
        collectionManager.save();
        System.out.println("Выход из программы.");
    }

}
