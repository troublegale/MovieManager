package letsgo.lab6.client;

import letsgo.lab6.client.io.Console;

public class ClientMain {

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        Console console = new Console(client);
        console.start();
    }

}
