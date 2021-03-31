package gui.listeners;

import exception.TaskNotFoundException;
import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDurationListener implements ActionListener, DocumentListener {

    private TaskListGUI gui;

    //EFFECTS: constructs an EditDurationListener
    public EditDurationListener(TaskListGUI gui) {
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: edits the duration in selected task according to what is entered in the duration text field
    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();
        JTextField newDuration = gui.getNewDuration();

        int index = list.getSelectedIndex();
        task = tl.getTaskByName(listModel.get(index).toString());
        try {
            tl.editTaskDur(task, Integer.parseInt(newDuration.getText()));
        } catch (TaskNotFoundException exception) {
            System.out.println("Task not found");
        }
        newDuration.setText("");
    }

    //required by DocumentListener
    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
