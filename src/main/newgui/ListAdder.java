package newgui;

import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ListAdder implements ListSelectionListener {

    TaskTracker tt;
    TaskList tl;
    DefaultListModel listModel;
    JList list;

    public ListAdder(TaskTracker tt, TaskList tl, DefaultListModel listModel, JList list) {
        this.tt = tt;
        this.tl = tl;
        this.listModel = listModel;
        this.list = list;
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
        tt.add(listScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
