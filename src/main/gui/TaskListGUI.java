package gui;

import model.Task;
import model.TaskList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TaskListGUI extends JPanel implements ListSelectionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String JSON_STORE = "./data/tasklist.json";
    private final String addString = "Add";

    private JList list;
    private DefaultListModel listModel;
    private JPanel panel;
    private TaskList tl = new TaskList();
    private JTextField newTaskName = new JTextField(10);
    private JTextField newSubject = new JTextField(10);
    private JTextField newType = new JTextField(10);
    private JTextField newDuration = new JTextField(10);
    private JTextField newDescription = new JTextField(10);
    private JButton addButton;
    private JButton saveButton;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    public TaskListGUI() {
        loadTaskList();
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(panel);

        listModel = new DefaultListModel();
        addLists();

        addButton = new JButton(addString);
        makeAddButton(addButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener(saveButton));


        createPanel();

        add(addButton);
        add(saveButton);
        add(newTaskName);
        add(newSubject);
        add(newType);
        add(newDuration);
        add(newDescription);
    }

    private void createPanel() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(newTaskName);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    private void makeAddButton(JButton addButton) {
        AddListener addListener = new AddListener(addButton);

        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
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

        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();

    }

    public void createJTextField(JTextField jtf, AddListener al) {
        jtf = new JTextField(10);
        jtf.addActionListener(al);
        jtf.getDocument().addDocumentListener(al);
    }

    class SaveListener implements ActionListener {
        private JButton button;

        public SaveListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveTaskList();
        }
    }

    // EFFECTS: saves the task list to file
    private void saveTaskList() {
        try {
            jsonWriter.open();
            jsonWriter.write(tl);
            jsonWriter.close();
            System.out.println("Saved task list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }


    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        Task task;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
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

            task = new Task(newTaskName.getText());
            task.setSubject(newSubject.getText());
            task.setDuration(Integer.parseInt(newDuration.getText()));
            task.setType(newType.getText());
            task.setDescription(newDescription.getText());
            tl.addTask(task);

            listModel.insertElementAt(newTaskName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            newTaskName.requestFocusInWindow();
            newTaskName.setText("");
            newSubject.setText("");
            newType.setText("");
            newDuration.setText("");
            newDescription.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
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

    public void addLists() {
        for (Task t : tl.getTasks()) {
            listModel.addElement(t.getTaskName());
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: loads task list from file
    private void loadTaskList() {
        try {
            tl = jsonReader.read();
            System.out.println("Loaded saved list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


}
