package Client;
// Client Side
// Client.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Client extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTextArea resultArea;
    private JTextField queryField;

    public Client() {
        super("Laboratory Information System");
        try {
            // Configure logger
            FileHandler fileHandler = new FileHandler("client.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            setupGUI();
            connectToServer();
        } catch (IOException e) {
            LOGGER.severe("Error setting up logging: " + e.getMessage());
            e.printStackTrace();
        }
    }

    String executeQueryForTable(String query) throws IOException, ClassNotFoundException {
        out.writeObject(query);
        return (String) in.readObject();
    }

    private void setupGUI() {
        LOGGER.info("Setting up client GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Top Panel with buttons
        JPanel topPanel = new JPanel();
        JButton viewButton = new JButton("View Data");
        JButton addButton = new JButton("Add Record");
        JButton updateButton = new JButton("Update Record");
        JButton deleteButton = new JButton("Delete Record");
        JButton searchButton = new JButton("Search Record");
        JButton saveButton = new JButton("Save to File");

        JButton clearButton = new JButton("Clear");
        clearButton.setForeground(Color.RED); // Optionally make it red to stand out

        topPanel.add(viewButton);
        topPanel.add(addButton);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);
        topPanel.add(searchButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        saveButton.addActionListener(e -> saveToFile());

        clearButton.addActionListener(e -> {
            LOGGER.info("Clearing result area");
            resultArea.setText(""); // Очищаем текстовую область
        });

        DialogForms dialogForms = new DialogForms(this, this);

        viewButton.addActionListener(e -> {
            LOGGER.info("Opening experiment viewer");
            ExperimentViewer viewer = new ExperimentViewer(this);
            viewer.setVisible(true);
        });
        addButton.addActionListener(e -> dialogForms.showAddExperimentDialog());
        updateButton.addActionListener(e -> dialogForms.showUpdateExperimentDialog());
        deleteButton.addActionListener(e -> dialogForms.showDeleteExperimentDialog());
        searchButton.addActionListener(e -> dialogForms.showSearchExperimentDialog());
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            LOGGER.info("Connected to server: " + SERVER_ADDRESS + ":" + SERVER_PORT);
        } catch (IOException e) {
            LOGGER.severe("Could not connect to server: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not connect to server!");
        }
    }

    void executeQuery(String query) {
        try {
            LOGGER.info("Executing query: " + query);
            out.writeObject(query);
            String result = (String) in.readObject();
            resultArea.setText(result);
            LOGGER.info("Query executed successfully");
            System.out.println("Query executed: " + query);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            LOGGER.severe("Error executing query: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error executing query!");
        }
    }

    void saveToFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileWriter writer = new FileWriter(file);
                writer.write(resultArea.getText());
                writer.close();
                LOGGER.info("Results saved to file: " + file.getAbsolutePath());
                System.out.println("Results saved to file: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.severe("Error saving to file: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving to file!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client().setVisible(true);
        });
    }
}