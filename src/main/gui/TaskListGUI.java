package gui;

import gui.listeners.*;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TaskListGUI extends JPanel implements ListSelectionListener, DocumentListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 300;

    private JList list;
    private DefaultListModel listModel;
    private JPanel panel;
    private JPanel textPanel;
    private TaskList tl = new TaskList();
    private JTextField newTaskName;
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
    private DeleteListener deleteListener = new DeleteListener(this);
    private LoadListener loadListener = new LoadListener(this);
    private EditNameListener editNameListener = new EditNameListener(this);
    private EditSubjectListener editSubjectListener = new EditSubjectListener(this);
    private EditTypeListener editTypeListener = new EditTypeListener(this);
    private EditDurationListener editDurationListener = new EditDurationListener(this);
    private EditDescriptionListener editDescriptionListener = new EditDescriptionListener(this);
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
        saveButton.addActionListener(new SaveListener(this));

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
        editNameButton.addActionListener(editNameListener);

        editSubjectButton = new JButton("Edit Subject");
        editSubjectButton.addActionListener(editSubjectListener);

        editTypeButton = new JButton("Edit Type");
        editTypeButton.addActionListener(editTypeListener);

        editDurationButton = new JButton("Edit Duration");
        editDurationButton.addActionListener(editDurationListener);

        editDescriptionButton = new JButton("Edit Description");
        editDescriptionButton.addActionListener(editDescriptionListener);
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


    public void resetTextFields() {
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

    public void setDeleteButtonEnabled(boolean b) {
        deleteButton.setEnabled(b);
    }


    @Override
    //required by ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {

    }

    //getters
    public DefaultListModel getListModel() {
        return listModel;
    }

    public TaskList getTl() {
        return tl;
    }

    public JList getList() {
        return list;
    }

    public JTextField getNewTaskName() {
        return newTaskName;
    }

    public JTextField getNewSubject() {
        return newSubject;
    }

    public JTextField getNewType() {
        return newType;
    }

    public JTextField getNewDuration() {
        return newDuration;
    }

    public JTextField getNewDescription() {
        return newDescription;
    }

    //setters
    public void setTl(TaskList tl) {
        this.tl = tl;
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


}
