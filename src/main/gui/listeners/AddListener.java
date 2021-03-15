package gui.listeners;

import gui.TaskListGUI;
import javafx.scene.input.InputMethodTextRun;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddListener implements ActionListener, DocumentListener {

    private boolean alreadyEnabled = false;
    private JButton button;
    Task task;
    TaskList tl;
    TaskListGUI gui;
    JTextField newTaskName;
    JList list;
    DefaultListModel listModel;
    private JTextField newSubject;
    private JTextField newDuration;
    private JTextField newType;
    private JTextField newDescription;


    public AddListener(JButton button, TaskListGUI gui) {
        this.button = button;
        this.gui = gui;
        tl = gui.getTl();
        newTaskName = gui.getNewTaskName();
        newSubject = gui.getNewSubject();
        newDuration = gui.getNewDuration();
        newType = gui.getNewType();
        newDescription = gui.getNewDescription();
        list = gui.getList();
        listModel = gui.getListModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String name = newTaskName.getText();

        //User didn't type in a unique name...
        if (name.equals("") || alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            newTaskName.requestFocusInWindow();
            newTaskName.selectAll();
            return;
        }

        int index = list.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        addTask();

        listModel.addElement(newTaskName.getText());

        //Reset the text field.

        gui.resetTextFields();

        //Select the new item and make it visible.
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }


    private void addTask() {
        task = new Task(newTaskName.getText());
        task.setSubject(newSubject.getText());
        task.setDuration(Integer.parseInt(newDuration.getText()));
        task.setType(newType.getText());
        task.setDescription(newDescription.getText());
        tl.addTask(task);
    }

    protected boolean alreadyInList(String name) {
        for (Task t : tl.getTasks()) {
            if (t.getTaskName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    //Required by DocumentListener.
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    //Required by DocumentListener.
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    //Required by DocumentListener.
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
