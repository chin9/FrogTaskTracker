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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class TaskListGUI extends JPanel implements ListSelectionListener, WindowListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 300;

    private JList list;
    private DefaultListModel listModel;
    private JFrame parent;
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

    public TaskListGUI(JFrame parent) {
        parent.addWindowListener(this);
        panel = new JPanel();

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(panel);

        askToLoad();

        listModel = new DefaultListModel();

        addLists();

        createButtons();

        createPanel();
        createTextPanel();

        add(textPanel, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: creates a pop-up window which asks whether the user wants to load previous file
    private void askToLoad() {

        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to load your saved file?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            loadListener.loadTaskList();
        }

    }

    //EFFECTS: creates all the buttons in the panel
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
        AddListener addListener = new AddListener(this);
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

    //EFFECTS: resets all the text fields to a blank field
    public void resetTextFields() {
        newTaskName.requestFocusInWindow();
        newTaskName.setText("");
        newSubject.setText("");
        newType.setText("");
        newDuration.setText("");
        newDescription.setText("");
    }

    //MODIFIES: this
    //EFFECTS: add the task names of each task in list to a scrolling panel, add the scrolling panel to the main panel
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
        listScrollPane.setPreferredSize(new Dimension(200, 200));
        add(listScrollPane, BorderLayout.CENTER);
    }

    //EFFECTS: call setEnabled on delete button given a boolean value
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

    public JButton getAddButton() {
        return addButton;
    }

    //setters
    public void setTl(TaskList tl) {
        this.tl = tl;
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to save your file?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            SaveListener saveListener = new SaveListener(this);
            saveListener.saveTaskList();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
