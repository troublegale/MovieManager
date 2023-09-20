package letsgo.lab6.client;

import letsgo.lab6.common.network.Request;
import letsgo.lab6.common.network.Response;

public class TCPClient {

    public Response communicateWithServer(Request request) {
        return new Response("sent:\ncommand: " + request.commandName() + "\narg: " + request.argument());
    }

}
