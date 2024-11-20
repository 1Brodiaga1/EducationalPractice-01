package Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.*;

public class DialogForms {
    private static final Logger LOGGER = Logger.getLogger(DialogForms.class.getName());
    private JFrame parentFrame;
    private Client client;

    public DialogForms(JFrame parent, Client clientInstance) {
        try {
            FileHandler fileHandler = new FileHandler("dialog_forms.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            this.parentFrame = parent;
            this.client = clientInstance;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAddExperimentDialog() {
        JDialog dialog = new JDialog(parentFrame, "Добавить эксперимент", true);
        dialog.setLayout(new GridLayout(8, 2, 10, 10));
        dialog.setSize(400, 500);

        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea();
        JTextField researcherField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"Планируется", "В процессе", "Завершен", "Приостановлен"}
        );
        JDatePicker startDatePicker = new JDatePicker();
        JDatePicker endDatePicker = new JDatePicker();

        dialog.add(new JLabel("Название эксперимента:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Описание:"));
        dialog.add(new JScrollPane(descriptionArea));
        dialog.add(new JLabel("Исследователь:"));
        dialog.add(researcherField);
        dialog.add(new JLabel("Статус:"));
        dialog.add(statusCombo);
        dialog.add(new JLabel("Дата начала:"));
        dialog.add(startDatePicker);
        dialog.add(new JLabel("Дата окончания:"));
        dialog.add(endDatePicker);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> {
            String query = String.format(
                    "INSERT INTO experiments (name, description, start_date, end_date, status, researcher) " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                    nameField.getText(),
                    descriptionArea.getText(),
                    startDatePicker.getDate(),
                    endDatePicker.getDate(),
                    statusCombo.getSelectedItem(),
                    researcherField.getText()
            );
            client.executeQuery("INSERT|" + query);
            dialog.dispose();
        });

        dialog.add(saveButton);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public void showUpdateExperimentDialog() {
        JDialog dialog = new JDialog(parentFrame, "Обновить эксперимент", true);
        dialog.setLayout(new GridLayout(9, 2, 10, 10));
        dialog.setSize(400, 550);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea();
        JTextField researcherField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"Планируется", "В процессе", "Завершен", "Приостановлен"}
        );
        JDatePicker startDatePicker = new JDatePicker();
        JDatePicker endDatePicker = new JDatePicker();

        dialog.add(new JLabel("ID эксперимента:"));
        dialog.add(idField);
        dialog.add(new JLabel("Название эксперимента:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Описание:"));
        dialog.add(new JScrollPane(descriptionArea));
        dialog.add(new JLabel("Исследователь:"));
        dialog.add(researcherField);
        dialog.add(new JLabel("Статус:"));
        dialog.add(statusCombo);
        dialog.add(new JLabel("Дата начала:"));
        dialog.add(startDatePicker);
        dialog.add(new JLabel("Дата окончания:"));
        dialog.add(endDatePicker);

        JButton updateButton = new JButton("Обновить");
        updateButton.addActionListener(e -> {
            String query = String.format(
                    "UPDATE experiments SET " +
                            "name='%s', description='%s', start_date='%s', end_date='%s', " +
                            "status='%s', researcher='%s' WHERE id=%s",
                    nameField.getText(),
                    descriptionArea.getText(),
                    startDatePicker.getDate(),
                    endDatePicker.getDate(),
                    statusCombo.getSelectedItem(),
                    researcherField.getText(),
                    idField.getText()
            );
            client.executeQuery("UPDATE|" + query);
            dialog.dispose();
        });

        dialog.add(updateButton);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public void showDeleteExperimentDialog() {
        String idToDelete = JOptionPane.showInputDialog(
                parentFrame,
                "Введите ID эксперимента для удаления:",
                "Удаление эксперимента",
                JOptionPane.QUESTION_MESSAGE
        );

        if (idToDelete != null && !idToDelete.trim().isEmpty()) {
            String query = "DELETE FROM experiments WHERE id = " + idToDelete;
            client.executeQuery("DELETE|" + query);
        }
    }

    public void showSearchExperimentDialog() {
        JDialog dialog = new JDialog(parentFrame, "Поиск эксперимента", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.setSize(400, 300);

        JTextField nameField = new JTextField();
        JTextField researcherField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"Все", "Планируется", "В процессе", "Завершен", "Приостановлен"}
        );
        JDatePicker startDatePicker = new JDatePicker();
        JDatePicker endDatePicker = new JDatePicker();

        dialog.add(new JLabel("Название эксперимента:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Исследователь:"));
        dialog.add(researcherField);
        dialog.add(new JLabel("Статус:"));
        dialog.add(statusCombo);
        dialog.add(new JLabel("Дата начала (от):"));
        dialog.add(startDatePicker);
        dialog.add(new JLabel("Дата окончания (до):"));
        dialog.add(endDatePicker);

        JButton searchButton = new JButton("Найти");
        searchButton.addActionListener(e -> {
            StringBuilder query = new StringBuilder("SELECT * FROM experiments WHERE 1=1");

            if (!nameField.getText().isEmpty()) {
                query.append(" AND name LIKE '%").append(nameField.getText()).append("%'");
            }
            if (!researcherField.getText().isEmpty()) {
                query.append(" AND researcher LIKE '%").append(researcherField.getText()).append("%'");
            }
            if (!"Все".equals(statusCombo.getSelectedItem())) {
                query.append(" AND status = '").append(statusCombo.getSelectedItem()).append("'");
            }
            if (startDatePicker.getDate() != null) {
                query.append(" AND start_date >= '").append(startDatePicker.getDate()).append("'");
            }
            if (endDatePicker.getDate() != null) {
                query.append(" AND end_date <= '").append(endDatePicker.getDate()).append("'");
            }

            client.executeQuery("SEARCH|" + query.toString());
            dialog.dispose();
        });

        dialog.add(searchButton);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    // Вспомогательный класс для выбора даты
    private class JDatePicker extends JPanel {
        private JComboBox<Integer> dayCombo, monthCombo, yearCombo;

        public JDatePicker() {
            setLayout(new FlowLayout());

            // Заполнение комбобоксов
            dayCombo = new JComboBox<>();
            for (int i = 1; i <= 31; i++) dayCombo.addItem(i);

            monthCombo = new JComboBox<>();
            for (int i = 1; i <= 12; i++) monthCombo.addItem(i);

            yearCombo = new JComboBox<>();
            int currentYear = LocalDate.now().getYear();
            for (int i = currentYear - 20; i <= currentYear + 20; i++) {
                yearCombo.addItem(i);
            }
            yearCombo.setSelectedItem(currentYear);

            add(new JLabel("День:"));
            add(dayCombo);
            add(new JLabel("Месяц:"));
            add(monthCombo);
            add(new JLabel("Год:"));
            add(yearCombo);
        }

        public String getDate() {
            return yearCombo.getSelectedItem() + "-" +
                    monthCombo.getSelectedItem() + "-" +
                    dayCombo.getSelectedItem();
        }
    }
}