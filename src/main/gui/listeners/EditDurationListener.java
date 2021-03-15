package gui.listeners;

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

    public EditDurationListener(TaskListGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();
        JTextField newDuration = gui.getNewDuration();

        int index = list.getSelectedIndex();
        task = tl.getTaskByName(listModel.get(index).toString());
        task.setDuration(Integer.parseInt(newDuration.getText()));
        newDuration.setText("");
    }

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
