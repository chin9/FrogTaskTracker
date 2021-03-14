package newgui;

import model.Task;
import model.TaskList;
import newgui.tools.AddTool;
import newgui.tools.Tool;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TaskTracker extends JFrame implements ListSelectionListener{

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/tasklist.json";

    private JsonReader jsonReader;


    private TaskList tl;
    private DefaultListModel listModel;
    private JList list;
    private ListAdder listAdder;
    private List<Tool> tools;
    private JPanel textPanel;


    public TaskTracker() {
        super("Task Tracker");
        initializeFields();
        loadTaskList();
        addLists();
        initializeGraphics();

    }

    //MODIFIES: this
    //EFFECTS: initializes the fields
    private void initializeFields() {
        jsonReader = new JsonReader(JSON_STORE);
        tl = new TaskList();
        listModel = new DefaultListModel();
        list = new JList(listModel);
        textPanel = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createTools();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTools() {
        JPanel toolArea = new JPanel();
        JPanel listArea = new JPanel();

        toolArea.setLayout(new GridLayout(0, 1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        textPanel.setLayout(new GridLayout(1, 0));
        textPanel.setSize(new Dimension(0, 5));
        add(textPanel, BorderLayout.NORTH);

        listArea.setSize(new Dimension(0, 0));
        add(listArea, BorderLayout.CENTER);

        AddTool addTool = new AddTool(this, toolArea);

        listArea.add(list);

    }

    public void addToTextPanel(JTextField f) {
        textPanel.add(f);
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

    public void addLists() {
        for (Task t : tl.getTasks()) {
            listModel.addElement(t.getTaskName());
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    //getters
    public TaskList getTl() {
        return tl;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public JList getList() {
        return list;
    }

    public static void main(String[] args) {
        new TaskTracker();
    }


}
