package letsgo.lab6.client;

import letsgo.lab6.client.io.Console;

public class ClientMain {

    public static void main(String[] args) {
        int port = 5928;
        TCPClient client = new TCPClient("localhost", port);
        Console console = new Console(client);
        console.start();
    }

}
