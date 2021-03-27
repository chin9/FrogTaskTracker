package gui;

import model.TaskList;

import javax.swing.*;

public class TaskTrackerGUI extends JFrame {

    private TaskList tl;
    private TaskListGUI tlg;

    //EFFECTS: constructs the main window
    public TaskTrackerGUI() {
        super("Task Tracker UI");
        JComponent newContentPane = new TaskListGUI(this);
        newContentPane.setOpaque(true);
        setContentPane(newContentPane);

        //Display the window.
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new TaskTrackerGUI();
    }
}
