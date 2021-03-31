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

public class EditSubjectListener implements ActionListener, DocumentListener {

    private TaskListGUI gui;

    //EFFECTS: constructs an EditSubjectListener
    public EditSubjectListener(TaskListGUI gui) {
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: edits the subject in selected task according to what is entered in the subject text field
    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();
        JTextField newSubject = gui.getNewSubject();

        int index = list.getSelectedIndex();
        task = tl.getTaskByName(listModel.get(index).toString());
        try {
            tl.editTaskSub(task, newSubject.getText());
        } catch (TaskNotFoundException exception) {
            System.out.println("Task not found");
        }
        newSubject.setText("");
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
