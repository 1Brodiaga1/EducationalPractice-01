package Server;
// Server Side
// Database.java
import java.sql.*;
import java.util.logging.*;

public class Database {
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/laboratory?autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "кщще";

    private Connection connection;

    public Database() {
        try {
            // Configure logger
            FileHandler fileHandler = new FileHandler("database.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Database connection established successfully");
        } catch (Exception e) {
            LOGGER.severe("Error initializing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized ResultSet executeQuery(String query) throws SQLException {
        LOGGER.info("Executing query: " + query);
        ensureConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            LOGGER.info("Query executed successfully");
            return resultSet;
        } catch (SQLException e) {
            LOGGER.severe("Error executing query: " + e.getMessage());
            if (statement != null) statement.close();
            throw e;
        }
    }

    public synchronized int executeUpdate(String query) throws SQLException {
        LOGGER.info("Executing update: " + query);
        ensureConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int result = statement.executeUpdate(query);
            LOGGER.info("Update executed successfully, affected rows: " + result);
            return result;
        } catch (SQLException e) {
            LOGGER.severe("Error executing update: " + e.getMessage());
            throw e;
        } finally {
            if (statement != null) statement.close();
        }
    }

    private void ensureConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                LOGGER.info("Reopening database connection");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            LOGGER.severe("Failed to reestablish database connection: " + e.getMessage());
            throw e;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Database connection closed successfully");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}