package letsgo.lab6.server;

import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.databaseManagers.DDLManager;
import letsgo.lab6.server.managers.databaseManagers.dml.MovieDMLManager;
import letsgo.lab6.server.utility.DatabaseConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ServerMain {

    public static void main(String[] args) {

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(ServerMain::exit));
            getDatabaseConfiguration();
            DDLManager.createTables();
            int port = 33506;
            ArrayDeque<Movie> movieDeque = (ArrayDeque<Movie>) Collections.synchronizedCollection(new ArrayDeque<Movie>());
            movieDeque.addAll(MovieDMLManager.getMovies());
            CollectionManager collectionManager = new CollectionManager(movieDeque);
            TCPServer server = new TCPServer(port, collectionManager);
            try {
                server.run();
            } catch (NoSuchElementException e) {
                exit();
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exit();
            System.exit(1);
        }
    }

    private static void getDatabaseConfiguration() {
        String credentials = System.getenv("CREDENTIALS");
        if (credentials == null) {
            System.out.println("Не удалось получить данные для доступа к БД, переменная CREDENTIALS не задана.");
            throw new RuntimeException();
        }
        try (Scanner scanner = new Scanner(new File(credentials))) {
            DatabaseConfiguration.setDatabaseURL(scanner.nextLine().trim());
            DatabaseConfiguration.setDatabaseLogin(scanner.nextLine().trim());
            DatabaseConfiguration.setDatabasePassword(scanner.nextLine().trim());
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось получить данные для доступа к БД из файла.");
            throw new RuntimeException();
        } catch (NoSuchElementException e) {
            System.out.println("Неверный формат файла с данными для доступа к БД.");
            throw new RuntimeException();
        }
    }

    private static void exit() {
        System.out.println("Завершение программы.");
    }

}
