import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class TaskManager {
    private PriorityQueue<Task> taskQueue;
    private Stack<Task> undoStack;
    private Stack<Task> redoStack;
    private List<Task> completedTasks;
    private Stack<String> actionHistory;

    public TaskManager() {
        taskQueue = new PriorityQueue<>((t1, t2) -> {
            int priorityComparison = Integer.compare(t2.getPriority(), t1.getPriority());
            if (priorityComparison == 0) {
                return t1.getDueDate().compareTo(t2.getDueDate());
            }
            return priorityComparison;
        });
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        completedTasks = new ArrayList<>();
        actionHistory = new Stack<>();
    }

    public void addTask(String taskName, String description, int priority, LocalDate dueDate, String category) {
        Task newTask = new Task(taskName, description, priority, dueDate, category);
        taskQueue.offer(newTask);
        undoStack.push(newTask);
        redoStack.clear();
        actionHistory.push("Added task: " + taskName);
    }

    public void removeHighestPriorityTask() {
        if (!taskQueue.isEmpty()) {
            Task removedTask = taskQueue.poll();
            undoStack.push(removedTask);
            redoStack.clear();
            actionHistory.push("Removed task: " + removedTask.getTaskName());
        }
    }

    public void updateTask(Task updatedTask) {
        taskQueue.removeIf(t -> t.getTaskName().equals(updatedTask.getTaskName()));
        taskQueue.offer(updatedTask);
        undoStack.push(updatedTask);
        redoStack.clear();
        actionHistory.push("Updated task: " + updatedTask.getTaskName());
    }

    public Task getTaskByName(String taskName) {
        for (Task task : taskQueue) {
            if (task.getTaskName().equals(taskName)) {
                return task;
            }
        }
        return null;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Task lastAction = undoStack.pop();
            redoStack.push(lastAction);
            if (taskQueue.contains(lastAction)) {
                taskQueue.remove(lastAction);
            } else {
                taskQueue.offer(lastAction);
            }
            actionHistory.push("Undo action on task: " + lastAction.getTaskName());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Task lastUndoneTask = redoStack.pop();
            taskQueue.offer(lastUndoneTask);
            undoStack.push(lastUndoneTask);
            actionHistory.push("Redo action on task: " + lastUndoneTask.getTaskName());
        }
    }

    // Method to sort tasks by priority
    public List<Task> sortTasksByPriority() {
        return taskQueue.stream()
                .sorted((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()))
                .collect(Collectors.toList());
    }

    // Method to sort tasks by due date
    public List<Task> sortTasksByDueDate() {
        return taskQueue.stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    // Method to sort tasks by category
    public List<Task> sortTasksByCategory() {
        return taskQueue.stream()
                .sorted(Comparator.comparing(Task::getCategory))
                .collect(Collectors.toList());
    }

    // Search tasks by keyword in name, description, or category
    public List<Task> searchTasks(String keyword) {
        return taskQueue.stream()
                .filter(task -> task.getTaskName().contains(keyword) ||
                        task.getDescription().contains(keyword) ||
                        task.getCategory().contains(keyword))
                .collect(Collectors.toList());
    }

    public void archiveTask(Task task) {
        task.setArchived(true);
        taskQueue.remove(task);
        actionHistory.push("Archived task: " + task.getTaskName());
    }

    public void markTaskAsComplete(Task task) {
        taskQueue.remove(task);
        completedTasks.add(task);
        actionHistory.push("Completed task: " + task.getTaskName());
    }

    public String getAllTasks() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskQueue) {
            if (!task.isArchived()) {
                sb.append(task).append(" (Progress: ").append(task.getProgress()).append("%)\n");
            }
        }
        return sb.toString();
    }

    public void printActionHistory() {
        actionHistory.forEach(System.out::println);
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }
}
