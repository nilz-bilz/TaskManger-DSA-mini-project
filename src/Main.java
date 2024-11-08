import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Enhanced Task Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BoxLayout for organizing UI components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Task input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Task Name
        JLabel taskNameLabel = new JLabel("Task Name:");
        JTextField taskNameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(taskNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(taskNameField, gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(descriptionField, gbc);

        // Priority ComboBox
        JLabel priorityLabel = new JLabel("Priority:");
        String[] priorities = { "1", "2", "3", "4", "5" };
        JComboBox<String> priorityComboBox = new JComboBox<>(priorities);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(priorityLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(priorityComboBox, gbc);

        // Due Date
        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dueDateField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(dueDateLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dueDateField, gbc);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = { "Work", "Personal", "Urgent" };
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(categoryComboBox, gbc);

        // Progress Slider
        JLabel progressLabel = new JLabel("Progress:");
        JSlider progressSlider = new JSlider(0, 100, 0);
        progressSlider.setMajorTickSpacing(25);
        progressSlider.setMinorTickSpacing(5);
        progressSlider.setPaintTicks(true);
        progressSlider.setPaintLabels(true);
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(progressLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(progressSlider, gbc);

        // Archive checkbox
        JCheckBox archiveCheckBox = new JCheckBox("Archive Task");
        gbc.gridx = 1;
        gbc.gridy = 6;
        inputPanel.add(archiveCheckBox, gbc);

        // Task display area
        JTextArea taskListArea = new JTextArea(10, 30);
        taskListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskListArea);

        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton addTaskButton = new JButton("Add Task");
        JButton removeTaskButton = new JButton("Remove Highest Priority Task");
        JButton editTaskButton = new JButton("Edit Task");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        // Search field and button
        JTextField searchField = new JTextField(10); // Set the search field to a smaller width
        JButton searchButton = new JButton("Search");

        // Panel to hold search components and center them
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Add space between field and button
        searchPanel.add(searchButton);
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the search panel

        // Add buttons to button panel
        buttonPanel.add(addTaskButton);
        buttonPanel.add(removeTaskButton);
        buttonPanel.add(editTaskButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        // Sort options (remove this section if you don't need it)
        JPanel sortPanel = new JPanel();
        JLabel sortLabel = new JLabel("Sort by:");
        String[] sortOptions = { "Priority", "Due Date", "Category" };
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        sortPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the sort panel

        // Adding Action Listeners for Buttons
        addTaskButton.addActionListener(e -> {
            try {
                String taskName = taskNameField.getText();
                String description = descriptionField.getText();
                int priority = Integer.parseInt((String) priorityComboBox.getSelectedItem());
                LocalDate dueDate = LocalDate.parse(dueDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String category = (String) categoryComboBox.getSelectedItem();
                Task newTask = new Task(taskName, description, priority, dueDate, category);

                if (archiveCheckBox.isSelected()) {
                    newTask.setArchived(true);
                }

                newTask.setProgress(progressSlider.getValue());
                taskManager.addTask(taskName, description, priority, dueDate, category);
                taskListArea.setText(taskManager.getAllTasks());

                // Clear input fields after adding the task
                taskNameField.setText("");
                descriptionField.setText("");
                dueDateField.setText("");
                priorityComboBox.setSelectedIndex(0);
                categoryComboBox.setSelectedIndex(0);
                progressSlider.setValue(0);
                archiveCheckBox.setSelected(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding task. Please check input values.");
            }
        });

        // Remove Highest Priority Task
        removeTaskButton.addActionListener(e -> {
            taskManager.removeHighestPriorityTask();
            taskListArea.setText(taskManager.getAllTasks());
        });

        // Edit Task Button
        editTaskButton.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog(frame, "Enter the name of the task to edit:");
            Task taskToEdit = taskManager.getTaskByName(taskName);

            if (taskToEdit != null) {
                String newDescription = JOptionPane.showInputDialog(frame, "Enter new description:",
                        taskToEdit.getDescription());
                int newPriority = Integer
                        .parseInt(JOptionPane.showInputDialog(frame, "Enter new priority:", taskToEdit.getPriority()));
                LocalDate newDueDate = LocalDate.parse(JOptionPane.showInputDialog(frame,
                        "Enter new due date (YYYY-MM-DD):", taskToEdit.getDueDate().toString()));
                String newCategory = (String) JOptionPane.showInputDialog(frame, "Select new category:", "Category",
                        JOptionPane.QUESTION_MESSAGE, null, categories, taskToEdit.getCategory());
                int newProgress = Integer.parseInt(
                        JOptionPane.showInputDialog(frame, "Enter new progress (0-100):", taskToEdit.getProgress()));

                taskToEdit.setDescription(newDescription);
                taskToEdit.setPriority(newPriority);
                taskToEdit.setDueDate(newDueDate);
                taskToEdit.setCategory(newCategory);
                taskToEdit.setProgress(newProgress);

                taskManager.updateTask(taskToEdit);
                taskListArea.setText(taskManager.getAllTasks());
            } else {
                JOptionPane.showMessageDialog(frame, "Task not found.");
            }
        });

        // Undo Button
        undoButton.addActionListener(e -> {
            taskManager.undo();
            taskListArea.setText(taskManager.getAllTasks());
        });

        // Redo Button
        redoButton.addActionListener(e -> {
            taskManager.redo();
            taskListArea.setText(taskManager.getAllTasks());
        });

        // Search Button
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();
            if (keyword != null && !keyword.trim().isEmpty()) {
                List<Task> searchResults = taskManager.searchTasks(keyword);

                StringBuilder sb = new StringBuilder();
                for (Task task : searchResults) {
                    sb.append(task).append(" (Progress: ").append(task.getProgress()).append("%)\n");
                }
                taskListArea.setText(sb.toString());
            } else {
                taskListArea.setText("No search keyword entered.");
            }
        });

        // Sort ComboBox
        sortComboBox.addActionListener(e -> {
            List<Task> sortedTasks;
            switch ((String) sortComboBox.getSelectedItem()) {
                case "Priority":
                    sortedTasks = taskManager.sortTasksByPriority();
                    break;
                case "Due Date":
                    sortedTasks = taskManager.sortTasksByDueDate();
                    break;
                case "Category":
                    sortedTasks = taskManager.sortTasksByCategory();
                    break;
                default:
                    sortedTasks = taskManager.sortTasksByPriority();
            }
            StringBuilder sb = new StringBuilder();
            for (Task task : sortedTasks) {
                sb.append(task).append(" (Progress: ").append(task.getProgress()).append("%)\n");
            }
            taskListArea.setText(sb.toString());
        });

        // Add components to main panel
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);
        mainPanel.add(searchPanel); // Add the search panel containing the search field and button
        mainPanel.add(sortPanel); // Optional: Add sort panel if you need sorting functionality

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
