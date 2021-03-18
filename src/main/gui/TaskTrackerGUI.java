package gui;

import model.TaskList;

import javax.swing.*;

public class TaskTrackerGUI extends JFrame {

    private TaskList tl;
    private TaskListGUI tlg;

    public TaskTrackerGUI() {
        super("Task Tracker UI");
        JComponent newContentPane = new TaskListGUI(this);
        newContentPane.setOpaque(true); //content panes must be opaque
        setContentPane(newContentPane);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new TaskTrackerGUI();
    }
}
