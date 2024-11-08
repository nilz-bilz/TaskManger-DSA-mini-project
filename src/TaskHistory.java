// TaskHistory.java
public class TaskHistory {
    private Node head;
    private Node tail;

    // Node class to store each task
    private class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task) {
            this.task = task;
        }
    }

    // Method to add a task to the history
    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Method to remove a task from the history
    public void removeTask(Task task) {
        Node current = head;
        while (current != null) {
            if (current.task.equals(task)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
                if (current == head) {
                    head = current.next;
                }
                if (current == tail) {
                    tail = current.prev;
                }
                break;
            }
            current = current.next;
        }
    }

    // Method to print the task history
    public void printHistory() {
        Node current = head;
        while (current != null) {
            System.out.println(current.task.getTaskName() + " - " + current.task.getDescription());
            current = current.next;
        }
    }
}
