import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String taskName;
    private String description;
    private int priority;
    private LocalDate dueDate;
    private String category;
    private int progress; // Progress percentage from 0 to 100
    private boolean isArchived;
    private List<Task> dependencies;
    private String notes;
    private List<File> attachments;

    public Task(String taskName, String description, int priority, LocalDate dueDate, String category) {
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.category = category;
        this.progress = 0;
        this.isArchived = false;
        this.dependencies = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    // Getters and Setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public void addDependency(Task task) {
        dependencies.add(task);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void addAttachment(File file) {
        attachments.add(file);
    }

    @Override
    public String toString() {
        return taskName + " - " + description + " (Priority: " + priority + ", Due: " + dueDate + ")";
    }
}
