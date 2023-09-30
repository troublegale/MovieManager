package letsgo.lab6.server.runnables;

import letsgo.lab6.common.network.CommandRequest;
import letsgo.lab6.common.network.CommandResponse;
import letsgo.lab6.server.managers.handlers.CommandManager;
import letsgo.lab6.server.utility.CachedThreadPoolStorage;

import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Executor implements Runnable {

    private final CommandManager commandManager;
    private final ObjectOutputStream objectOutputStream;
    private final String commandName;
    private final String argument;
    private final String username;
    private final SocketChannel clientChannel;

    public Executor(CommandManager commandManager, ObjectOutputStream objectOutputStream, CommandRequest commandRequest,
                    SocketChannel clientChannel) {
        this.commandManager = commandManager;
        this.objectOutputStream = objectOutputStream;
        commandName = commandRequest.commandName();
        argument = commandRequest.argument();
        username = commandRequest.username();
        this.clientChannel = clientChannel;
    }

    @Override
    public void run() {
        String message = commandManager.execute(commandName, argument, username);
        CommandResponse commandResponse = new CommandResponse(message);
        delegateResponseSending(commandResponse);
    }

    private void delegateResponseSending(CommandResponse commandResponse) {
        ExecutorService cachedThreadPool = CachedThreadPoolStorage.getThreadPool();
        cachedThreadPool.execute(new Sender(commandResponse, objectOutputStream, clientChannel));
    }
}
