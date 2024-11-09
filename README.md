# Task Manager - DSA Project

This project is a task management application that uses key DSA concepts to manage tasks efficiently:

### 1. **Priority Queue**
   - **Where**: Used in the `TaskManager` class as `taskQueue`.
   - **How**: Stores tasks in order of priority, with higher-priority tasks at the front of the queue. If two tasks have the same priority, they are ordered by due date.
   - **Purpose**: Ensures that tasks are efficiently managed by priority, allowing quick retrieval of the highest-priority task.

### 2. **Stack**
   - **Where**: Used in the `TaskManager` class with `undoStack`, `redoStack`, and `actionHistory`.
   - **How**: `undoStack` stores tasks for undo operations, while `redoStack` holds tasks for redo. `actionHistory` logs all actions taken (add, remove, edit).
   - **Purpose**: Provides Last-In-First-Out (LIFO) functionality, enabling quick and easy implementation of undo and redo actions.

### 3. **Doubly Linked List**
   - **Where**: Implemented in the `TaskHistory` class using `TaskHistory$Node` nodes.
   - **How**: Each `TaskHistory$Node` contains a reference to a `Task` and pointers to the previous and next nodes, forming a doubly-linked structure.
   - **Purpose**: Allows efficient traversal in both directions and quick additions or deletions from any position, which is useful for tracking and modifying task history.

### 4. **Iterator**
   - **Where**: Used throughout the `TaskManager` and `TaskHistory` classes to iterate over tasks and history nodes.
   - **How**: Allows traversal of collections (e.g., lists, priority queue) in a simple and controlled manner.
   - **Purpose**: Enables easy access to each task or history entry, especially when displaying all tasks or task history in the UI.

### 5. **Sorting (Comparator)**
   - **Where**: Implemented in `TaskManager` with `sortTasksByPriority`, `sortTasksByDueDate`, and `sortTasksByCategory`.
   - **How**: Uses `Comparator` and `Stream API` to sort tasks based on specified attributes.
   - **Purpose**: Allows users to view tasks in a specified order, improving usability and task organization.

### 6. **Sequential Search**
   - **Where**: Used in `TaskManager`’s `searchTasks` method and `getTaskByName` method.
   - **How**: Iterates through the list of tasks to find tasks that match the keyword or specific name.
   - **Purpose**: Enables finding tasks based on keyword matching for name, description, or category, enhancing search functionality.

### Summary of DSA Concepts and Their Roles

- **Priority Queue**: Efficient task prioritization.
- **Stack**: Simplifies undo/redo operations.
- **Doubly Linked List**: Efficient history tracking with bidirectional traversal.
- **Iterator**: Smooth collection traversal.
- **Sorting (Comparator)**: Organized task display by attributes.
- **Sequential Search**: Keyword-based search for tasks.

Together, these DSA concepts allow for an efficient, feature-rich task management application that supports task prioritization, search, sorting, history tracking, and undo/redo functionalities.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.
