package letsgo.lab6.client;

import letsgo.lab6.common.network.Request;
import letsgo.lab6.common.network.Response;

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

    public void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRequest(Request request) throws IOException {
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        return (Response) objectInputStream.readObject();
    }

    public void disconnect() throws IOException {
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

    public Response communicateWithServer(Request request) throws IOException, ClassNotFoundException {
        connect();
        sendRequest(request);
        Response response = receiveResponse();
        disconnect();
        return response;
    }

}
