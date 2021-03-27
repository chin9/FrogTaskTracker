package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditNameListener implements ActionListener, DocumentListener {

    private TaskListGUI gui;

    //EFFECTS: constructs an EditNameListener
    public EditNameListener(TaskListGUI gui) {
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: edits the name in selected task according to what is entered in the name text field
    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();
        JTextField newTaskName = gui.getNewTaskName();

        int index = list.getSelectedIndex();
        task = tl.getTaskByName(listModel.get(index).toString());

        String text = newTaskName.getText();
        task.setName(text);
        listModel.set(index, text);
        newTaskName.setText("");
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
