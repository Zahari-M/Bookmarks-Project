package bg.sofia.uni.fmi.mjt.bookmarks.client;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.ServerResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BookmarksClient {

    private static final int SERVER_PORT = 4444;
    private static Gson gson = new Gson();
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Enter command: ");
                String message = scanner.nextLine();

                if ("quit".equals(message)) {
                    break;
                }

                writer.println(message);
                String reply = reader.readLine();
                ServerResponse response = gson.fromJson(reply, ServerResponse.class);
                response.print(System.out);
            }
        } catch (IOException e) {
            System.out.println("There is a problem with the network communication");
        }
    }
}