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
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TaskListGUI extends JPanel implements ListSelectionListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 300;
    private static final String JSON_STORE = "./data/tasklist.json";

    private JList list;
    private DefaultListModel listModel;
    private JPanel panel;
    private JPanel textPanel;
    private TaskList tl = new TaskList();
    private JTextField newTaskName = new JTextField(10);
    private JTextField newSubject = new JTextField(10);
    private JTextField newType = new JTextField(10);
    private JTextField newDuration = new JTextField(10);
    private JTextField newDescription = new JTextField(10);
    private JButton addButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton deleteButton;
    private JButton editNameButton;
    private JButton editSubjectButton;
    private JButton editTypeButton;
    private JButton editDurationButton;
    private JButton editDescriptionButton;
    private JButton filterBySubjectButton;
    private JButton filterByTypeButton;
    private JButton showDetailsButton;
    private JScrollPane listScrollPane;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private DeleteListener deleteListener = new DeleteListener();
    private LoadListener loadListener = new LoadListener();
    private EditNameListener editNameListener = new EditNameListener();
    private EditSubjectListener editSubjectListener = new EditSubjectListener();
    private EditTypeListener editTypeListener = new EditTypeListener();
    private EditDurationListener editDurationListener = new EditDurationListener();
    private EditDescriptionListener editDescriptionListener = new EditDescriptionListener();
    private FilterBySubjectListener filterBySubjectListener = new FilterBySubjectListener(this);
    private FilterByTypeListener filterByTypeListener = new FilterByTypeListener(this);
    private ShowDetailsListener showDetailsListener = new ShowDetailsListener(this);

    public TaskListGUI() {

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(panel);

        listModel = new DefaultListModel();
        addLists();

        createButtons();

        createPanel();
        createTextPanel();

        add(textPanel, BorderLayout.SOUTH);
    }

    private void createButtons() {
        addButton = new JButton("Add");
        makeAddButton(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(deleteListener);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton("Load");
        loadButton.addActionListener(loadListener);

        initializeEditButtons();

        initializeFilterButtons();

        showDetailsButton = new JButton("Show Details");
        showDetailsButton.addActionListener(showDetailsListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the filter buttons, adding listeners to each of them
    private void initializeFilterButtons() {
        filterBySubjectButton = new JButton("Filter By Subject");
        filterBySubjectButton.addActionListener(filterBySubjectListener);

        filterByTypeButton = new JButton("Filter By Type");
        filterByTypeButton.addActionListener(filterByTypeListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the edit buttons, adding listeners to each of them
    private void initializeEditButtons() {
        editNameButton = new JButton("Edit Name");
        editNameButton.addActionListener(new EditNameListener());

        editSubjectButton = new JButton("Edit Subject");
        editSubjectButton.addActionListener(new EditSubjectListener());

        editTypeButton = new JButton("Edit Type");
        editTypeButton.addActionListener(new EditTypeListener());

        editDurationButton = new JButton("Edit Duration");
        editDurationButton.addActionListener(new EditDurationListener());

        editDescriptionButton = new JButton("Edit Description");
        editDescriptionButton.addActionListener(new EditDescriptionListener());
    }

    private void createPanel() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.Y_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(addButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(deleteButton);
        buttonPane.add(editNameButton);
        buttonPane.add(editSubjectButton);
        buttonPane.add(editTypeButton);
        buttonPane.add(editDurationButton);
        buttonPane.add(editDescriptionButton);
        buttonPane.add(filterBySubjectButton);
        buttonPane.add(filterByTypeButton);
        buttonPane.add(showDetailsButton);
        add(buttonPane);
    }

    private void createTextPanel() {
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setSize(new Dimension(50, 100));
        textPanel.add(new JLabel("Name"));
        textPanel.add(newTaskName);
        textPanel.add(new JLabel("Subject"));
        textPanel.add(newSubject);
        textPanel.add(new JLabel("Type"));
        textPanel.add(newType);
        textPanel.add(new JLabel("Duration"));
        textPanel.add(newDuration);
        textPanel.add(new JLabel("Description"));
        textPanel.add(newDescription);
    }

    private void makeAddButton(JButton addButton) {
        AddListener addListener = new AddListener(addButton);

        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        initializeNameTextField(addListener);
        initializeSubjectTextField(addListener);
        initializeTypeTextField(addListener);
        initializeDurationTextField(addListener);
        initializeDescriptionTextField(addListener);


    }

    private void initializeDescriptionTextField(AddListener addListener) {
        newDescription = new JTextField(10);
        newDescription.addActionListener(addListener);
        newDescription.addActionListener(editDescriptionListener);
        newDescription.getDocument().addDocumentListener(addListener);
    }

    private void initializeDurationTextField(AddListener addListener) {
        newDuration = new JTextField(10);
        newDuration.addActionListener(addListener);
        newDuration.addActionListener(editDurationListener);
        newDuration.getDocument().addDocumentListener(addListener);
    }

    private void initializeTypeTextField(AddListener addListener) {
        newType = new JTextField(10);
        newType.addActionListener(addListener);
        newType.addActionListener(editTypeListener);
        newType.addActionListener(filterByTypeListener);
        newType.getDocument().addDocumentListener(addListener);
    }

    private void initializeSubjectTextField(AddListener addListener) {
        newSubject = new JTextField(10);
        newSubject.addActionListener(addListener);
        newSubject.addActionListener(editSubjectListener);
        newSubject.addActionListener(filterBySubjectListener);
        newSubject.getDocument().addDocumentListener(addListener);
    }

    private void initializeNameTextField(AddListener addListener) {
        newTaskName = new JTextField(10);
        newTaskName.addActionListener(addListener);
        newTaskName.addActionListener(editNameListener);
        newTaskName.getDocument().addDocumentListener(addListener);
    }


    class SaveListener implements ActionListener {

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

            addTask();

            listModel.insertElementAt(newTaskName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.

            resetTextFields();

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

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();

            for (Task t : tl.getTasks()) {
                if (listModel.get(index).equals(t.getTaskName())) {
                    tl.removeTask(t);
                    break;
                }
            }
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    private class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loadTaskList();
            listModel.clear();
            for (Task t : tl.getTasks()) {
                listModel.addElement(t.getTaskName());
            }

        }
    }

    private class EditNameListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task task;
            int index = list.getSelectedIndex();
            task = tl.getTaskByName(listModel.get(index).toString());
            task.setName(newTaskName.getText());
            listModel.set(index, newTaskName.getText());
            newTaskName.setText("");
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

    private class EditSubjectListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task task;
            int index = list.getSelectedIndex();
            task = tl.getTaskByName(listModel.get(index).toString());
            task.setSubject(newSubject.getText());
            newSubject.setText("");
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

    private class EditTypeListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task task;
            int index = list.getSelectedIndex();
            task = tl.getTaskByName(listModel.get(index).toString());
            task.setType(newType.getText());
            newType.setText("");
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

    private class EditDurationListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task task;
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

    private class EditDescriptionListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task task;
            int index = list.getSelectedIndex();
            task = tl.getTaskByName(listModel.get(index).toString());
            task.setDescription(newDescription.getText());
            newDescription.setText("");
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

    private class FilterBySubjectListener implements ActionListener, DocumentListener {

        JPanel parent;

        public FilterBySubjectListener(JPanel parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listModel = new DefaultListModel();
            JList list;

            for (Task t : tl.getTasks()) {
                if (t.getSubject().equals(newSubject.getText())) {
                    listModel.addElement(t.getTaskName());
                }
            }
            list = new JList(listModel);
            list.setVisibleRowCount(5);
            JScrollPane listScrollPane = new JScrollPane(list);
            add(listScrollPane, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(parent, listScrollPane, "Subject: " + newSubject.getText(),
                    JOptionPane.INFORMATION_MESSAGE);
            newSubject.setText("");
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

    private class FilterByTypeListener implements ActionListener, DocumentListener {

        JPanel parent;

        public FilterByTypeListener(JPanel parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listModel = new DefaultListModel();
            JList list;

            for (Task t : tl.getTasks()) {
                if (t.getType().equals(newType.getText())) {
                    listModel.addElement(t.getTaskName());
                }
            }
            list = new JList(listModel);
            list.setVisibleRowCount(4);
            JScrollPane listScrollPane = new JScrollPane(list);
            add(listScrollPane, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(parent, listScrollPane, "Type: " + newType.getText(),
                    JOptionPane.INFORMATION_MESSAGE);

            newType.setText("");
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

    private class ShowDetailsListener implements ActionListener {

        JPanel parent;

        public ShowDetailsListener(JPanel parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            for (Task t : tl.getTasks()) {
                if (listModel.get(index).equals(t.getTaskName())) {
                    //create pop-up of details
                    JOptionPane.showMessageDialog(parent, "Name: " + t.getTaskName() + "\nSubject: "
                            + t.getSubject() + "\nType: " + t.getType() + "\nDuration: " + t.getDuration()
                            + "\nDescription: " + t.getDescription(), "Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void resetTextFields() {
        newTaskName.requestFocusInWindow();
        newTaskName.setText("");
        newSubject.setText("");
        newType.setText("");
        newDuration.setText("");
        newDescription.setText("");
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

        listScrollPane = new JScrollPane(list);
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
