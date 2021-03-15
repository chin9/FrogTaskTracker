package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;
import persistence.JsonReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LoadListener implements ActionListener {

    private static final String JSON_STORE = "./data/tasklist.json";

    private TaskListGUI gui;
    private JsonReader jsonReader;

    public LoadListener(TaskListGUI gui) {
        this.gui = gui;
        jsonReader = new JsonReader(JSON_STORE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loadTaskList();
        gui.getListModel().clear();
        for (Task t : gui.getTl().getTasks()) {
            gui.getListModel().addElement(t.getTaskName());
        }

    }


    // MODIFIES: this
    // EFFECTS: loads task list from file
    public void loadTaskList() {
        try {
            TaskList tl;
            tl = jsonReader.read();
            gui.setTl(tl);
            System.out.println("Loaded saved list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
