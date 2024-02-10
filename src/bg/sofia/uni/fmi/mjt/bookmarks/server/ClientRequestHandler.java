package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.ServerResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.commands.Command;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {
    private static Gson gson = new Gson();

    private Storage storage;
    private int userID = -1;
    private Socket socket;

    public ClientRequestHandler(Socket socket, Storage storage) {
        this.socket = socket;
        this.storage = storage;
    }

    @Override
    public void run() {

        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(gson.toJson(getResponse(inputLine))); // send response back to the client
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private ServerResponse getResponse(String inputLine) {
        try {
            Command command = Command.newCommand(inputLine);

        } catch (Exception e) {
            writeException(e);
            return new ServerResponse(e.getMessage(), null);
        }
        return null;
    }

    private void writeException(Exception e) {

    }

}