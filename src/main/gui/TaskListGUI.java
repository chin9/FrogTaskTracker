package gui;

import gui.listeners.*;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class TaskListGUI extends JPanel implements ListSelectionListener, WindowListener {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

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
    private ImageIcon frogImage1;
    private ImageIcon frogImage2;
    private ImageIcon frogImage3;
    private JLabel weightLabel;
    private JLabel frogLabel;

    //EFFECTS: constructs the TaskListGUI
    public TaskListGUI(JFrame parent) {
        parent.addWindowListener(this);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(panel);

        askToLoad();
        listModel = new DefaultListModel();

        createImagePanel();
        addLists();

        createButtons();
        createButtonPanel();
        createTextPanel();

        add(textPanel, BorderLayout.SOUTH);
    }

    //MODIFIES: this
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

    //MODIFIES: this
    //EFFECTS: create the panel that contains all the buttons
    private void createButtonPanel() {
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

    //MODIFIES: this
    //EFFECTS: create a panel that includes the text fields and their labels
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

    //MODIFIES: this
    //EFFECTS: create a panel for the frog images
    private void createImagePanel() {
        JPanel imagePane = new JPanel();
        imagePane.setLayout(new BoxLayout(imagePane, BoxLayout.PAGE_AXIS));
        imagePane.setAlignmentY(CENTER_ALIGNMENT);

        frogImage1 = new ImageIcon("./data/froggy.jpeg");
        frogImage2 = new ImageIcon("./data/froggy2.jpeg");
        frogImage3 = new ImageIcon("./data/froggy3.jpeg");
        /* images taken from:
           - froggy.jpeg: https://www.facebook.com/101584028338173/posts/peppa-pig-frog-sticker-new-peppa-pig-frog-sticker
             is-now-out-in-my-store-click-t/111198367376739/
           - froggy2.jpeg: https://peppapig.fandom.com/wiki/Frog
           - froggy3.jpeg: https://www.pinterest.com/pin/714383559636071168/
         */
        weightLabel = new JLabel(tl.getFrogWeight() + " kg!");
        frogLabel = new JLabel();
        updateFrogLabel();
        imagePane.add(frogLabel);
        imagePane.add(weightLabel);
        add(imagePane, BorderLayout.BEFORE_FIRST_LINE);
    }

    //MODIFIES: this
    //EFFECTS: make the button for add function
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

    //MODIFIES: this
    //EFFECTS: initialize the "description" text field, adding listeners for "add" and "edit"
    private void initializeDescriptionTextField(AddListener addListener) {
        newDescription = new JTextField(10);
        newDescription.addActionListener(addListener);
        newDescription.addActionListener(editDescriptionListener);
        newDescription.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the "duration" text field, adding listeners for "add" and "edit"
    private void initializeDurationTextField(AddListener addListener) {
        newDuration = new JTextField(10);
        newDuration.addActionListener(addListener);
        newDuration.addActionListener(editDurationListener);
        newDuration.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the "type" text field, adding listeners for "add" and "edit"
    private void initializeTypeTextField(AddListener addListener) {
        newType = new JTextField(10);
        newType.addActionListener(addListener);
        newType.addActionListener(editTypeListener);
        newType.addActionListener(filterByTypeListener);
        newType.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the "subject" text field, adding listeners for "add" and "edit"
    private void initializeSubjectTextField(AddListener addListener) {
        newSubject = new JTextField(10);
        newSubject.addActionListener(addListener);
        newSubject.addActionListener(editSubjectListener);
        newSubject.addActionListener(filterBySubjectListener);
        newSubject.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: initialize the "name" text field, adding listeners for "add" and "edit"
    private void initializeNameTextField(AddListener addListener) {
        newTaskName = new JTextField(10);
        newTaskName.addActionListener(addListener);
        newTaskName.addActionListener(editNameListener);
        newTaskName.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
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
    //EFFECTS: add the task names of each task in tl to a scrolling panel, add the scrolling panel to the main panel
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

    //MODIFIES: this
    //EFFECTS: call setEnabled on delete button given a boolean value
    public void setDeleteButtonEnabled(boolean b) {
        deleteButton.setEnabled(b);
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

    @Override
    //required by WindowListener
    public void windowOpened(WindowEvent e) {

    }

    //MODIFIES: this
    //EFFECTS: when the window closes, ask user if they want to save current file
    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to save your file?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            SaveListener saveListener = new SaveListener(this);
            saveListener.saveTaskList();
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: set frog image according to frog's weight
    public void updateFrogLabel() {
        int i = tl.getFrogWeight() % 3;
        if (i == 0) {
            frogLabel.setIcon(frogImage1);
        } else if (i == 1) {
            frogLabel.setIcon(frogImage2);
        } else if (i == 2) {
            frogLabel.setIcon(frogImage3);
        }
    }

    //MODIFIES: this
    //EFFECTS: update the frog weight displayed
    public void updateWeightLabel() {
        weightLabel.setText(tl.getFrogWeight() + " kg!");
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
    //required by WindowListener
    public void windowClosed(WindowEvent e) {

    }

    @Override
    //required by WindowListener
    public void windowIconified(WindowEvent e) {

    }

    @Override
    //required by WindowListener
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    //required by WindowListener
    public void windowActivated(WindowEvent e) {

    }

    @Override
    //required by WindowListener
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    //required by ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {

    }
}
