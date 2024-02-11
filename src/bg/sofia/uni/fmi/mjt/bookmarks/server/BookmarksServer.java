package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.FileIO;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.FileSystemStorage;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarksServer {

    private static final int SERVER_PORT = 4444;
    private static final String STORAGE_DIR = "./storage";

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Storage storage;
        try {
            storage = new FileSystemStorage(new FileIO(STORAGE_DIR));
        } catch (IOException e) {
            ClientRequestHandler.writeException(e, -1);
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            Socket clientSocket;

            while (true) {

                clientSocket = serverSocket.accept();

                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket, storage);
                executor.execute(clientHandler); // use a thread pool to launch a thread
            }

        } catch (IOException e) {
            ClientRequestHandler.writeException(e, -1);
        }
    }

}