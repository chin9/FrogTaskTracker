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


    //setters
    public void setName(String n) {
        taskName = n;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public void setType(String t) {
        type = t;
    }

    public void setDuration(int d) {
        duration = d;
    }

    public void setDescription(String d) {
        description = d;
    }

    //getters
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

    //EFFECTS: returns String representation of Task
    public String toString() {
        return "Name: " + taskName + "\nSubject: " + subject + "\nType: " + type + "\nDuration: " + duration
                + " minutes" + "\nDescription: " + description;
    }

}
