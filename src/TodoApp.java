import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {
    private ArrayList<Task> tasks;
    private final Scanner scanner;
    private static final String FILE_NAME = "tasks.txt";

    public TodoApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadTasks();
    }

    public void run() {
        while(true) {
            printMenu();
            String input = scanner.next();
            try{
                int choice = Integer.parseInt(input);
                if(choice > 0 && choice <= 5) {
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            addTask();
                            break;
                        case 2:
                            viewTasks();
                            break;
                        case 3:
                            markTask();
                            break;
                        case 4:
                            removeTask();
                            break;
                        case 5:
                            saveTasks();
                            System.out.println("Exiting...");
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Todo App Menu ---");
        System.out.println("1. Add new task");
        System.out.println("2. Display tasks");
        System.out.println("3. Mark task as complete");
        System.out.println("4. Remove task");
        System.out.println("5. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private void addTask() {
        System.out.print("Enter a task: ");
        String description = scanner.nextLine();
        tasks.add(new Task(description));
        System.out.println("Task added successfully!");
    }

    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found...");
        } else {
            for(int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private void markTask() {
        viewTasks();
        if(!tasks.isEmpty()) {
            while(true){
                System.out.print("Choose task to be marked as completed: ");
                String input = scanner.next();
                try {
                    int taskNum = Integer.parseInt(input);
                    if (taskNum > 0 && taskNum <= tasks.size()) {
                        tasks.get(taskNum - 1).setCompleted(true);
                        String description = tasks.get(taskNum - 1).getDescription();
                        System.out.println("'" + description + "' marked as completed!");
                        break;
                    } else {
                        System.out.println("Task number " + taskNum + " does not exist.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("'" + input + "' is not a number.");
                }

            }

        }
    }

    private void removeTask() {
        viewTasks();
        if(!tasks.isEmpty()) {
            while(true) {
                System.out.print("Choose task to be removed: ");
                String input = scanner.next();
                try {
                    int taskNum = Integer.parseInt(input);
                    if (taskNum > 0 && taskNum <= tasks.size()) {
                        String description = tasks.get(taskNum - 1).getDescription();
                        tasks.remove(taskNum - 1);
                        System.out.println("Task '" + description + "' deleted successfully!");
                        break;
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("'" + input + "' is not a number.");
                }

            }
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
            System.out.println("Tasks saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " +e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTasks() {
        File file = new File(FILE_NAME);
        if(file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                tasks = (ArrayList<Task>) ois.readObject();
                System.out.println("Tasks loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
                tasks = new ArrayList<>();
            }
        } else {
            System.out.println("No saved tasks were found. Starting with an empty list.");
        }
    }
}
