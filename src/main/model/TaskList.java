package model;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private List<Task> tl;

    public TaskList() {
        tl = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given task to list
    public void addTask(Task t) {
        tl.add(t);
    }

    //REQUIRES: t must be a task in the list
    //MODIFIES: this
    //EFFECTS: removes given task from list
    public void removeTask(Task t) {
        tl.remove(t);
    }

    //EFFECTS: returns task given index number
    public Task getTask(int i) {
        return tl.get(i);
    }

    //EFFECTS: returns all the task names in the list
    public String displayAllTaskNames() {

        String taskNames = "";

        for (Task t : tl) {
            taskNames = taskNames + t.getTaskName() + "\n";
        }
        return taskNames;
    }

    //REQUIRES: type must be an existing type in list
    //EFFECTS: returns the names of all the tasks of given type
    public String displayTasksOfType(String type) {

        String taskNames = "";

        for (Task t : tl) {
            if (t.getType().equals(type)) {
                taskNames = taskNames + t.getTaskName() + "\n";
            }
        }
        return taskNames;
    }

    //REQUIRES: subject must be an existing subject in list
    //EFFECTS: returns the names of all the tasks of given subject
    public String displayTasksOfSubject(String sub) {
        String taskNames = "";

        for (Task t : tl) {
            if (t.getSubject().equals(sub)) {
                taskNames = taskNames + t.getTaskName() + "\n";
            }
        }
        return taskNames;
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: this, t //if I only change t, am i changing this??
    //EFFECTS: changes the given task's name to n
    public void editTaskName(Task t, String n) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.editName(n);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: this, t
    //EFFECTS: changes the given task's subject to sub
    public void editTaskSub(Task t, String sub) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.editSubject(sub);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: this, t
    //EFFECTS: changes the given task's type to t
    public void editTaskType(Task t, String type) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.editType(type);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: this, t
    //EFFECTS: changes the given task's duration to d
    public void editTaskDur(Task t, int d) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.editDuration(d);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: this, t
    //EFFECTS: changes the given task's description to d
    public void editTaskDescription(Task t, String d) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.editDescription(d);
            }
        }
    }

    //getter
    public List<Task> getTasks() {
        return tl;
    }


}
