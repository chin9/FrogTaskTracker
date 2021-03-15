package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteListener implements ActionListener {

    TaskListGUI gui;

    public DeleteListener(TaskListGUI parent) {
        this.gui = parent;
    }

    public void actionPerformed(ActionEvent e) {
        //This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.

        JList list = gui.getList();
        TaskList tl = gui.getTl();
        DefaultListModel listModel = gui.getListModel();

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
            gui.setDeleteButtonEnabled(false);
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
