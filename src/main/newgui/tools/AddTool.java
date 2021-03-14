package newgui.tools;

import model.Task;
import model.TaskList;
import newgui.TaskTracker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTool extends Tool {

    private AddToolClickHandler addListener;
    private JTextField newTaskName;
    private JTextField newSubject;
    private JTextField newType;
    private JTextField newDuration;
    private JTextField newDescription;

    public AddTool(TaskTracker tracker, JComponent parent) {
        super(tracker, parent);
        addListener = new AddToolClickHandler();

    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add");
        addToParent(parent);
        initializeTextFields();

    }

    //MODIFIES: this
    //EFFECTS: initializes the text fields
    private void initializeTextFields() {
        newTaskName = new JTextField(10);
        newTaskName.addActionListener(addListener);
        newTaskName.getDocument().addDocumentListener(addListener);
        newSubject = new JTextField(10);
        newSubject.addActionListener(addListener);
        newSubject.getDocument().addDocumentListener(addListener);
        newType = new JTextField(10);
        newType.addActionListener(addListener);
        newType.getDocument().addDocumentListener(addListener);
        newDuration = new JTextField(10);
        newDuration.addActionListener(addListener);
        newDuration.getDocument().addDocumentListener(addListener);
        newDescription = new JTextField(10);
        newDescription.addActionListener(addListener);
        newDescription.getDocument().addDocumentListener(addListener);

        tracker.addToTextPanel(newTaskName);
        tracker.addToTextPanel(newSubject);
        tracker.addToTextPanel(newType);
        tracker.addToTextPanel(newDuration);
        tracker.addToTextPanel(newDescription);

    }

    @Override
    protected void addListener() {
        button.addActionListener(addListener);
    }

    private class AddToolClickHandler implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;

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

            int index = tracker.getList().getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            addTask();

            tracker.getListModel().insertElementAt(newTaskName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.

            resetTextFields();

            //Select the new item and make it visible.
            tracker.getList().setSelectedIndex(index);
            tracker.getList().ensureIndexIsVisible(index);
        }

        private void addTask() {
            Task task = new Task(newTaskName.getText());
            task.setSubject(newSubject.getText());
            task.setDuration(Integer.parseInt(newDuration.getText()));
            task.setType(newType.getText());
            task.setDescription(newDescription.getText());
            tracker.getTl().addTask(task);
        }

        protected boolean alreadyInList(String name) {
            for (Task t : tracker.getTl().getTasks()) {
                if (t.getTaskName().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        public void resetTextFields() {
            newTaskName.requestFocusInWindow();
            newTaskName.setText("");
            newSubject.setText("");
            newType.setText("");
            newDuration.setText("");
            newDescription.setText("");
        }
    }
}
