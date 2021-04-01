package gui;


import javax.swing.*;

public class TaskTrackerGUI extends JFrame {


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
