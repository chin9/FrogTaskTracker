package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of tasks, including weight of frog fed by the completed tasks
public class TaskList implements Writable {

    private List<Task> tl;
    private int frogWeight;

    public TaskList() {
        tl = new ArrayList<>();
        frogWeight = 1;
    }

    //MODIFIES: this
    //EFFECTS: adds given task to list
    //         adjust frog's weight: +1 for every five tasks added (1 + size/5)
    public void addTask(Task t) {
        tl.add(t);
        frogWeight = 1 + tl.size() / 5;
    }

    //REQUIRES: t must be a task in the list
    //MODIFIES: this
    //EFFECTS: removes given task from list
    //         adjust frog's weight; -1 for every five tasks removed
    public void removeTask(Task t) {
        tl.remove(t);
        frogWeight = 1 + tl.size() / 5;
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

    //EFFECTS: returns total duration worked
    public int displayTotalDuration() {
        int totalDur = 0;
        for (Task t : tl) {
            totalDur += t.getDuration();
        }
        return totalDur;
    }

    //EFFECTS: returns the task with given name, if task doesn't exist, return null
    public Task getTaskByName(String n) {
        for (Task t : tl) {
            if (n.equals(t.getTaskName())) {
                return t;
            }
        }
        return null;
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: t
    //EFFECTS: changes the given task's name to n
    public void editTaskName(Task t, String n) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.setName(n);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: t
    //EFFECTS: changes the given task's subject to sub
    public void editTaskSub(Task t, String sub) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.setSubject(sub);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: t
    //EFFECTS: changes the given task's type to t
    public void editTaskType(Task t, String type) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.setType(type);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: t
    //EFFECTS: changes the given task's duration to d
    public void editTaskDur(Task t, int d) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.setDuration(d);
            }
        }
    }

    //REQUIRES: given task must exist in list
    //MODIFIES: t
    //EFFECTS: changes the given task's description to d
    public void editTaskDescription(Task t, String d) {
        for (Task task : tl) {
            if (task.equals(t)) {
                task.setDescription(d);
            }
        }
    }

    //getters
    public List<Task> getTasks() {
        return tl;
    }

    public int getFrogWeight() {
        return frogWeight;
    }


    @Override
    // EFFECTS: returns this as JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("frogWeight", frogWeight);
        json.put("tl", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks in this task list as a JSON array
    public JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : tl) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
