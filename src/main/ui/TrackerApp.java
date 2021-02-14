package ui;

import model.Task;
import model.TaskList;

import java.util.Scanner;

// Task Application
public class TrackerApp {

    private TaskList myTL;
    private Scanner input;

    //EFFECTS: runs the Task Tracker application
    public TrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes tracker
    private void init() {
        myTL = new TaskList();
        input = new Scanner(System.in);
    }

    //EFFECTS: displays actions to be completed
    private void displayMenu() {
        System.out.println("What do you want to do?");
        System.out.println("\ta -> add a task");
        System.out.println("\tr -> remove a task");
        System.out.println("\te -> edit a task");
        System.out.println("\td -> display all tasks");
        System.out.println("\tf -> filter tasks");
        System.out.println("\ts -> filter tasks by subject");
        System.out.println("\tt -> filter tasks by type");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: completes action according to input command
    private void processCommand(String c) {
        if (c.equals("a")) {
            Task t0 = createTask();
            myTL.addTask(t0);
            displayFrogWeight();
        } else if (c.equals("r")) {
            removeTask();
            displayFrogWeight();
        } else if (c.equals("e")) {
            editTask();
        } else if (c.equals("d")) {
            displayTaskNames();
        } else if (c.equals("f")) {
            chooseFilter();
        } else {
            System.out.println("Invalid input.");
        }

    }

    //MODIFIES: this
    //EFFECTS: creates a new task
    private Task createTask() {
        Task task;
        String name;
        String sub;
        String type;
        int dur;
        String description;

        System.out.println("Input the task completed: ");
        name = input.nextLine();
        task = new Task(name);

        System.out.println("Subject: ");
        sub = input.nextLine();
        task.setSubject(sub);

        System.out.println("Type: ");
        type = input.nextLine();
        task.setType(type);

        System.out.println("Duration: ");
        checkForInteger();
        dur = Integer.parseInt(input.nextLine());
        task.setDuration(dur);

        System.out.println("Description: ");
        description = input.nextLine();
        task.setDescription(description);

        return task;
    }

    //EFFECTS: if next input isn't integer, ask for an integer
    private void checkForInteger() {
        while (!input.hasNextInt()) {
            System.out.println("Please enter an integer!");
            input.nextLine();
        }
    }

    //MODIFIES: this
    //EFFECTS: removes entered task from list
    private void removeTask() {
        String name;
        Task task;

        System.out.println("These are your tasks: ");
        System.out.println(myTL.displayAllTaskNames());
        System.out.println("Enter the name of task you want to remove: ");
        name = input.nextLine();
        task = myTL.getTaskByName(name);
        myTL.removeTask(task);
        System.out.println("The task " + name + " is removed.");
    }

    //MODIFIES: this
    //EFFECTS: edits entered task by name, subject, type, duration, or description
    private void editTask() {
        String name;
        String editOption;
        Task task;

        System.out.println("These are your tasks: ");
        System.out.println(myTL.displayAllTaskNames());
        System.out.println("Enter the name of task you want to edit: ");
        name = input.nextLine();
        task = myTL.getTaskByName(name);
        displayEditOptions();
        editOption = input.nextLine();

        if (editOption.equals("n")) {
            editName(task);
        } else if (editOption.equals("s")) {
            editSubject(task);
        } else if (editOption.equals("t")) {
            editType(task);
        } else if (editOption.equals("dur")) {
            editDuration(task);
        } else if (editOption.equals("des")) {
            editDescription(task);
        }
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's name to entered name
    private void editName(Task task) {
        String name;
        System.out.println("What do you want to change the name to?");
        name = input.nextLine();
        task.setName(name);
        System.out.println("Name changed to: " + name);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's subject to entered subject
    private void editSubject(Task task) {
        String sub;
        System.out.println("What do you want to change the subject to?");
        sub = input.nextLine();
        task.setSubject(sub);
        System.out.println("Subject changed to: " + sub);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's type to entered type
    private void editType(Task task) {
        String type;
        System.out.println("What do you want to change the type to?");
        type = input.nextLine();
        task.setType(type);
        System.out.println("Type changed to: " + type);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's duration to entered duration, if input is not int, enter again
    private void editDuration(Task task) {
        int dur;
        System.out.println("What do you want to change the duration to?");
        dur = Integer.parseInt(input.nextLine());
        task.setDuration(dur);
        System.out.println("Duration changed to: " + dur);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's description to entered description
    private void editDescription(Task task) {
        String des;
        System.out.println("What do you want to change the description to?");
        des = input.nextLine();
        task.setDescription(des);
        System.out.println("Description changed to: " + des);
    }

    //EFFECTS: display the properties that can be changed
    public void displayEditOptions() {
        System.out.println("Do you want to edit by: ");
        System.out.println("\tn -> name");
        System.out.println("\ts -> subject");
        System.out.println("\tt -> type");
        System.out.println("\tdur -> duration");
        System.out.println("\tdes -> description");
    }

    //EFFECTS: filter tasks by subject if "s" inputted, by type if "t" inputted
    private void chooseFilter() {
        String next;
        filterMenu();
        next = input.nextLine();
        if (next.equals("s")) {
            displayBySubject();
        } else if (next.equals("t")) {
            displayByType();
        } else {
            System.out.println("Invalid input.");
        }
    }

    //EFFECTS: displays diffferent ways the tasks can be filtered
    private void filterMenu() {
        System.out.println("What do you want to filter by?");
        System.out.println("\ts -> subject");
        System.out.println("\tt -> type");
    }

    //EFFECTS: displays the names of all tasks completed
    private void displayTaskNames() {
        System.out.println("You have completed: ");
        System.out.println(myTL.displayAllTaskNames());
    }

    //EFFECTS: displays the names of all tasks completed of entered subject
    private void displayBySubject() {
        String sub;
        System.out.println("Enter subject: ");
        sub = input.nextLine();
        System.out.println("For the subject " + sub + ", you have completed: ");
        System.out.println(myTL.displayTasksOfSubject(sub));
    }

    //EFFECTS: displays the names of all tasks completed of entered type
    private void displayByType() {
        String type;
        System.out.println("Enter type: ");
        type = input.nextLine();
        System.out.println("For the type " + type + ", you have completed: ");
        System.out.println(myTL.displayTasksOfType(type));
    }

    //EFFECTS: displays the weight of frog
    private void displayFrogWeight() {
        System.out.println("Task added. \nYour frog is " + myTL.getFrogWeight() + " kg!");
    }
}
