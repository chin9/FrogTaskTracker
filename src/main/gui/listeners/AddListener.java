package gui.listeners;

import gui.TaskListGUI;
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
    JList list;
    DefaultListModel listModel;


    //EFFECTS: constructs an AddListener
    public AddListener(TaskListGUI gui) {
        this.button = gui.getAddButton();
        this.gui = gui;
        tl = gui.getTl();
        list = gui.getList();
        listModel = gui.getListModel();
    }

    //CITATION: ListDemo / https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    //MODIFIES: this
    //EFFECTS: add a task to tl and display it according to the information entered in the text fields
    @Override
    public void actionPerformed(ActionEvent e) {

        JTextField newTaskName = gui.getNewTaskName();
        JTextField newSubject = gui.getNewSubject();
        JTextField newDuration = gui.getNewDuration();
        JTextField newType = gui.getNewType();
        JTextField newDescription = gui.getNewDescription();

        String name = newTaskName.getText();

        //User didn't type in a unique name...
        if (name.equals("") || alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            newTaskName.requestFocusInWindow();
            newTaskName.selectAll();
            return;
        }

        int index = list.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {
            index++;
        }

        addTask(newTaskName.getText(), newSubject.getText(), newType.getText(), newDuration.getText(),
                newDescription.getText());



        updateGUI();

        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }

    //MODIFIES: this
    //EFFECTS: update the display by resetting text fields and updating image and weight display
    private void updateGUI() {
        gui.resetTextFields();
        gui.updateFrogLabel();
        gui.updateWeightLabel();
    }

    //MODIFIES: this
    //EFFECTS: add a task with corresponding name, subject, type, duration, and description
    private void addTask(String name, String subject, String type, String duration, String description) {
        task = new Task(name);
        task.setSubject(subject);
        task.setDuration(Integer.parseInt(duration));
        task.setType(type);
        task.setDescription(description);
        tl.addTask(task);
        listModel.addElement(name);
    }

    //EFFECTS: if a task with given name is in the list, return true, otherwise return false
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

    //MODIFIES: this
    //EFFECTS: enable the button
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: if the name text field is empty, do not enable button, otherwise enable button
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
