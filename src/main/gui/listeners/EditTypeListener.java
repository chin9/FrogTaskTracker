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

public class EditTypeListener implements ActionListener, DocumentListener {

    private TaskListGUI gui;

    //EFFECTS: constructs an EditTypeListener
    public EditTypeListener(TaskListGUI gui) {
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: edits the type in selected task according to what is entered in the type text field
    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();
        JTextField newType = gui.getNewType();

        int index = list.getSelectedIndex();
        task = tl.getTaskByName(listModel.get(index).toString());
        try {
            tl.editTaskType(task, newType.getText());
        } catch (TaskNotFoundException exception) {
            System.out.println("Task not found");
        }
        newType.setText("");
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
