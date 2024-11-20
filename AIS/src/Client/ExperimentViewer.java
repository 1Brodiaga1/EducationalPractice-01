package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Logger;

public class ExperimentViewer extends JDialog {
    private static final Logger LOGGER = Logger.getLogger(ExperimentViewer.class.getName());
    private JTable table;
    private DefaultTableModel tableModel;
    private final Client client;

    public ExperimentViewer(Client client) {
        super(client, "Experiment Data Viewer", true);
        this.client = client;
        setupUI();
    }

    private void setupUI() {
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(getOwner());

        // Create table with empty model
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> loadData());

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadData();
    }

    private void loadData() {
        try {
            String result = client.executeQueryForTable("SELECT|SELECT * FROM experiments");
            updateTableFromResult(result);
        } catch (Exception e) {
            LOGGER.severe("Error loading data: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error loading data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableFromResult(String result) {
        // Clear existing data
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        String[] rows = result.split("\n");
        if (rows.length < 2) return; // No data or headers

        // Set column headers (first row)
        String[] headers = rows[0].split("\t");
        for (String header : headers) {
            if (!header.trim().isEmpty()) {
                tableModel.addColumn(header.trim());
            }
        }

        // Add data rows (skip header row and separator row)
        for (int i = 2; i < rows.length; i++) {
            String[] data = rows[i].split("\t");
            Vector<String> rowData = new Vector<>();
            for (String item : data) {
                rowData.add(item.trim());
            }
            if (!rowData.isEmpty()) {
                tableModel.addRow(rowData);
            }
        }
    }
}