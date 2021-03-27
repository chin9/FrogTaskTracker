package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowDetailsListener implements ActionListener {

    TaskListGUI gui;

    //EFFECTS: constructs a ShowDetailsListener
    public ShowDetailsListener(TaskListGUI gui) {
        this.gui = gui;
    }

    //EFFECTS: in a pop-up window, show all the properties of the selected task
    @Override
    public void actionPerformed(ActionEvent e) {
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();

        int index = list.getSelectedIndex();
        for (Task t : tl.getTasks()) {
            if (listModel.get(index).equals(t.getTaskName())) {
                //create pop-up of details
                JOptionPane.showMessageDialog(gui, "Name: " + t.getTaskName() + "\nSubject: "
                        + t.getSubject() + "\nType: " + t.getType() + "\nDuration: " + t.getDuration()
                        + "\nDescription: " + t.getDescription(), "Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
