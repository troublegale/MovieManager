package letsgo.lab6.server;

import letsgo.lab6.server.managers.CollectionManager;
import letsgo.lab6.server.managers.handlers.CommandManager;
import letsgo.lab6.server.runnables.Receiver;
import letsgo.lab6.server.utility.CachedThreadPoolStorage;
import letsgo.lab6.server.utility.ServerConsole;

import java.util.concurrent.ExecutorService;

public class TCPServer {

    private final int port;
    private final CommandManager commandManager;

    public TCPServer(int port, CollectionManager collectionManager) {
        this.port = port;
        commandManager = new CommandManager(collectionManager);
    }

    public void run() {
        ExecutorService cachedThreadPool = CachedThreadPoolStorage.getThreadPool();
        cachedThreadPool.execute(new Receiver(port, commandManager));
        ServerConsole.handleServerInput();
        System.exit(0);
    }

}
