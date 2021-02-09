package model;

// Represents a task completed having a name, subject, type, duration (in minutes), and short description
public class Task {
    private String taskName;
    private String subject;
    private String type;
    private int duration;
    private String description;

    public Task(String n) {
        taskName = n;
    }

    //MODIFIES: this
    //EFFECTS: changes the task name to n
    public void editName(String n) {
        taskName = n;
    }

    //MODIFIES: this
    //EFFECTS: changes the subject to s
    public void editSubject(String s) {
        subject = s;
    }

    //MODIFIES: this
    //EFFECTS: changes the type to t
    public void editType(String t) {
        type = t;
    }

    //MODIFIES: this
    //EFFECTS: changes the duration to d in minutes
    public void editDuration(int d) {
        duration = d;
    }

    //MODIFIES: this
    //EFFECTS: changes the description to d
    public void editDescription(String d) {
        description = d;
    }

    // getters
    public String getTaskName() {
        return taskName;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

}
