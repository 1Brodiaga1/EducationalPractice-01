package Server;
// Server.java
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 5000;
    private ServerSocket serverSocket;
    private Database database;

    public Server() {
        try {
            // Configure logger
            FileHandler fileHandler = new FileHandler("server.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            serverSocket = new ServerSocket(PORT);
            database = new Database();
            LOGGER.info("Server started on port " + PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("New client connected from " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, database).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Error starting server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
                if (database != null) database.closeConnection();
            } catch (IOException e) {
                LOGGER.severe("Error closing server resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}