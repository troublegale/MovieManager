package letsgo.lab6.server;

import letsgo.lab6.server.managers.FileManager;

public class ServerMain {

    public static void main(String[] args) {
        String filePath = System.getenv("MOVIES_PATH");
        FileManager.setFilePath(filePath);

    }
}
