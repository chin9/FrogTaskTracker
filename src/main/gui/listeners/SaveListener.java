package gui.listeners;

import gui.TaskListGUI;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveListener implements ActionListener {

    private static final String JSON_STORE = "./data/tasklist.json";

    private TaskListGUI parent;
    private JsonWriter jsonWriter;

    public SaveListener(TaskListGUI parent) {
        this.parent = parent;
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveTaskList();
    }


    // EFFECTS: saves the task list to file
    public void saveTaskList() {
        try {
            jsonWriter.open();
            jsonWriter.write(parent.getTl());
            jsonWriter.close();
            System.out.println("Saved task list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }
}
