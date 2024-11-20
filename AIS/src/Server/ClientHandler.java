package Server;
// ClientHandler.java
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.logging.*;

class ClientHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private Socket clientSocket;
    private Database database;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, Database database) {
        this.clientSocket = socket;
        this.database = database;
        try {
            // Configure logger
            FileHandler fileHandler = new FileHandler("client_handler.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            LOGGER.info("ClientHandler initialized for socket: " + socket.getInetAddress());
        } catch (IOException e) {
            LOGGER.severe("Error initializing ClientHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            LOGGER.info("ClientHandler thread started for socket: " + clientSocket.getInetAddress());
            while (true) {
                String request = (String) in.readObject();
                String[] parts = request.split("\\|");
                String command = parts[0];
                String data = parts.length > 1 ? parts[1] : "";

                LOGGER.info("Received request - Command: " + command + ", Data: " + data);

                switch (command) {
                    case "SELECT":
                        handleSelect(data);
                        break;
                    case "INSERT":
                        handleInsert(data);
                        break;
                    case "UPDATE":
                        handleUpdate(data);
                        break;
                    case "DELETE":
                        handleDelete(data);
                        break;
                    case "SEARCH":
                        handleSearch(data);
                        break;
                    default:
                        LOGGER.warning("Unknown command received: " + command);
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Error in ClientHandler thread: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                LOGGER.severe("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleSelect(String query) throws SQLException, IOException {
        LOGGER.info("Executing SELECT query: " + query);
        ResultSet rs = database.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        StringBuilder result = new StringBuilder();
        // Add column headers
        for (int i = 1; i <= columnCount; i++) {
            result.append(metaData.getColumnName(i)).append("\t");
        }
        result.append("\n").append("-".repeat(50)).append("\n");

        // Add data rows
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.append(rs.getString(i)).append("\t");
            }
            result.append("\n");
        }
        out.writeObject(result.toString());
        LOGGER.info("SELECT query completed successfully");
    }

    private void handleInsert(String data) throws SQLException, IOException {
        LOGGER.info("Executing INSERT: " + data);
        int result = database.executeUpdate(data);
        out.writeObject("Inserted " + result + " records");
        LOGGER.info("INSERT completed, " + result + " records inserted");
    }

    private void handleUpdate(String data) throws SQLException, IOException {
        LOGGER.info("Executing UPDATE: " + data);
        int result = database.executeUpdate(data);
        out.writeObject("Updated " + result + " records");
        LOGGER.info("UPDATE completed, " + result + " records updated");
    }

    private void handleDelete(String data) throws SQLException, IOException {
        LOGGER.info("Executing DELETE: " + data);
        int result = database.executeUpdate(data);
        out.writeObject("Deleted " + result + " records");
        LOGGER.info("DELETE completed, " + result + " records deleted");
    }

    private void handleSearch(String data) throws SQLException, IOException {
        LOGGER.info("Executing SEARCH query: " + data);
        ResultSet rs = database.executeQuery(data);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        StringBuilder result = new StringBuilder();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.append(rs.getString(i)).append("\t");
            }
            result.append("\n");
        }
        out.writeObject(result.toString());
        LOGGER.info("SEARCH query completed successfully");
    }
}