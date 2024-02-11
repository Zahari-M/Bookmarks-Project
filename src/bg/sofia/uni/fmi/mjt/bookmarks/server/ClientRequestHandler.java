package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.ServerResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.commands.Command;
import bg.sofia.uni.fmi.mjt.bookmarks.server.commands.LoginCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.commands.RegisterCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {
    private static Gson gson = new Gson();
    private static final int GUEST = -1;
    private Storage storage;
    private int userID = GUEST;
    private Socket socket;

    public ClientRequestHandler(Socket socket, Storage storage) {
        this.socket = socket;
        this.storage = storage;
    }

    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                ServerResponse response = getResponse(inputLine);
                out.println(gson.toJson(response));
            }
        } catch (IOException e) {
            writeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                writeException(e);
            }
        }

    }

    private ServerResponse getResponse(String inputLine) {
        try {
            Command command = Command.newCommand(inputLine);
            if (command == null) {
                throw new UnsupportedOperationException("Unknown command.");
            }
            if (userID == GUEST && !Command.logInCommand(command)) {
                throw new UnsupportedOperationException("You are not logged in.");
            }
            command.execute(storage, userID);
            if (Command.logInCommand(command)) {
                userID = command.getUserID();
            }
            return command.getServerResponse();
        } catch (Exception e) {
            writeException(e);
            return new ServerResponse(e.getMessage(), null);
        }
    }

    public static synchronized void writeException(Exception e) {

    }

}