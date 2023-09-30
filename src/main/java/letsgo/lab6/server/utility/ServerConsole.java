package letsgo.lab6.server.utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConsole {

    public static void handleServerInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                if (!input.isBlank()) {
                    if (input.equalsIgnoreCase("exit")) {
                        break;
                    } else {
                        System.out.println("Сервер можно оставить введя 'exit'.");
                    }
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

}
