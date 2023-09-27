package letsgo.lab6.server;

import letsgo.lab6.server.managers.CollectionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConsole {

    private final CollectionManager collectionManager;

    public ServerConsole(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean handleServerInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            if (System.in.available() > 0) {
                String input = scanner.nextLine().trim();
                switch (input.toLowerCase()) {
                    case "save" -> {
                        return false;
                    }
                    case "exit" -> {
                        System.out.println("Выход из программы.");
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("Можно использовать только команды 'save' и 'exit'.");
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

}
