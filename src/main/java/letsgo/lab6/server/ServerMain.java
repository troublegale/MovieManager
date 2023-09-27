package letsgo.lab6.server;

import letsgo.lab6.server.database.DatabaseConfiguration;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.EntityManager;
import letsgo.lab6.server.managers.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerMain {

    private static CollectionManager collectionManager;

    public static void main(String[] args) {
        try {
            DatabaseConfiguration databaseConfiguration = getDatabaseConfiguration();
            int port = 33506;
            collectionManager = new CollectionManager();
            TCPServer server = new TCPServer(port, collectionManager);
            try {
                server.run();
            } catch (NoSuchElementException e) {
                System.out.println("Выход из программы.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Выход из программы.");
            System.exit(1);
        }
    }


    private static DatabaseConfiguration getDatabaseConfiguration() {
        String credentials = System.getenv("CREDENTIALS");
        if (credentials == null) {
            System.out.println("Не удалось получить данные для доступа к БД, переменная CREDENTIALS не задана.");
            throw new RuntimeException();
        }
        try (Scanner scanner = new Scanner(new File(credentials))) {
            return new DatabaseConfiguration(
                    scanner.nextLine().trim(), scanner.nextLine().trim(), scanner.nextLine().trim());
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось получить данные для доступа к БД из файла.");
            throw new RuntimeException();
        } catch (NoSuchElementException e) {
            System.out.println("Неверный формат файла с данными для доступа к БД.");
            throw new RuntimeException();
        }
    }

}
