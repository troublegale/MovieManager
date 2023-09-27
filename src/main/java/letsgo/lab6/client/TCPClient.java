package letsgo.lab6.client;

import letsgo.lab6.common.network.AuthRequest;
import letsgo.lab6.common.network.AuthResponse;
import letsgo.lab6.common.network.CommandRequest;
import letsgo.lab6.common.network.CommandResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {

    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public TCPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    private void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void sendRequest(CommandRequest commandRequest) throws IOException {
        objectOutputStream.writeObject(commandRequest);
        objectOutputStream.flush();
    }

    private void sendRequest(AuthRequest authRequest) throws IOException {
        objectOutputStream.writeObject(authRequest);
        objectOutputStream.flush();
    }

    private CommandResponse receiveCommandResponse() throws IOException, ClassNotFoundException {
        return (CommandResponse) objectInputStream.readObject();
    }

    private AuthResponse receiveAuthResponse() throws IOException, ClassNotFoundException {
        return (AuthResponse) objectInputStream.readObject();
    }

    private void disconnect() throws IOException {
        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
        if (objectInputStream != null) {
            objectInputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    public CommandResponse getCommandResponse(CommandRequest commandRequest)
            throws IOException, ClassNotFoundException {
        connect();
        sendRequest(commandRequest);
        CommandResponse commandResponse = receiveCommandResponse();
        disconnect();
        return commandResponse;
    }

    public AuthResponse authorize(AuthRequest authRequest) throws IOException, ClassNotFoundException {
        connect();
        sendRequest(authRequest);
        AuthResponse authResponse = receiveAuthResponse();
        disconnect();
        return authResponse;
    }

}
