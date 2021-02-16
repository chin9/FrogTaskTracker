package ui;

import model.Task;
import model.TaskList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Task Tracker Application
public class TrackerApp {
    private static final String JSON_STORE = "./data/tasklist.json";

    private TaskList myTL;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the Task Tracker application
    public TrackerApp() throws FileNotFoundException {
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
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: displays actions to be completed
    private void displayMenu() {
        System.out.println("What do you want to do?");
        System.out.println("\ta -> add a task");
        System.out.println("\tr -> remove a task");
        System.out.println("\ti -> information about a task");
        System.out.println("\te -> edit a task");
        System.out.println("\td -> display all tasks");
        System.out.println("\tf -> filter tasks");
        System.out.println("\tt -> display total time recorded");
        System.out.println("\ts -> save current list to file");
        System.out.println("\tl -> load saved file");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: completes action according to input command
    private void processCommand(String c) {
        if (c.equals("a")) {
            addTask();
        } else if (c.equals("r")) {
            removeTask();

        } else if (c.equals("i")) {
            displayTaskInformation();
        } else if (c.equals("e")) {
            editTask();
        } else if (c.equals("d")) {
            displayTaskNames();
        } else if (c.equals("f")) {
            chooseFilter();
        } else if (c.equals("t")) {
            displayTimeRecorded();
        } else if (c.equals("s")) {
            saveTaskList();
        } else if (c.equals("l")) {
            loadTaskList();
        } else {
            System.out.println("Invalid input.");
        }

    }

    private void addTask() {
        Task t0 = createTask();
        myTL.addTask(t0);
        displayFrogWeight();
    }


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

    //REQUIRES: task entered is part of displayed list, no duplicated task names in list
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
        displayFrogWeight();
    }

    //REQUIRES: task entered is part of displayed list
    //EFFECTS: display information about a certain task
    private void displayTaskInformation() {
        String name;
        Task task;

        System.out.println("These are your tasks: ");
        System.out.println(myTL.displayAllTaskNames());
        System.out.println("Enter name of task: ");
        name = input.nextLine();
        task = myTL.getTaskByName(name);
        System.out.println("\n" + task.toString() + "\n");
    }

    //REQUIRES: task entered is part of displayed list
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
        myTL.editTaskName(task, name);
        System.out.println("Name changed to: " + name);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's subject to entered subject
    private void editSubject(Task task) {
        String sub;
        System.out.println("What do you want to change the subject to?");
        sub = input.nextLine();
        myTL.editTaskSub(task, sub);
        System.out.println("Subject changed to: " + sub);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's type to entered type
    private void editType(Task task) {
        String type;
        System.out.println("What do you want to change the type to?");
        type = input.nextLine();
        myTL.editTaskType(task, type);
        System.out.println("Type changed to: " + type);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's duration to entered duration, if input is not int, enter again
    private void editDuration(Task task) {
        int dur;
        System.out.println("What do you want to change the duration to?");
        dur = Integer.parseInt(input.nextLine());
        myTL.editTaskDur(task, dur);
        System.out.println("Duration changed to: " + dur);
    }

    //MODIFIES: task
    //EFFECTS: changes the given task's description to entered description
    private void editDescription(Task task) {
        String des;
        System.out.println("What do you want to change the description to?");
        des = input.nextLine();
        myTL.editTaskDescription(task, des);
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

    //EFFECTS: displays different ways the tasks can be filtered
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

    //EFFECTS: display total time recorded
    private void displayTimeRecorded() {
        System.out.println("Total time recorded: " + myTL.displayTotalDuration() + " minutes.");
    }

    //EFFECTS: displays the weight of frog
    private void displayFrogWeight() {
        System.out.println("Your frog is " + myTL.getFrogWeight() + " kg!");
    }

    // EFFECTS: saves the task list to file
    private void saveTaskList() {
        try {
            jsonWriter.open();
            jsonWriter.write(myTL);
            jsonWriter.close();
            System.out.println("Saved task list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads task list from file
    private void loadTaskList() {
        try {
            myTL = jsonReader.read();
            System.out.println("Loaded saved list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
