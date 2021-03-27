package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteListener implements ActionListener {

    TaskListGUI gui;

    //EFFECTS: constructs a DeleteListener
    public DeleteListener(TaskListGUI parent) {
        this.gui = parent;
    }

    //CITATION: ListDemo / https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    //MODIFIES: this
    //EFFECTS: delete the selected task from the task list
    public void actionPerformed(ActionEvent e) {

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

        if (size == 0) {
            gui.setDeleteButtonEnabled(false);
        } else {
            if (index == listModel.getSize()) {

                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            updateGUI();
        }
    }

    //MODIFIES: this
    //EFFECTS: updates the gui's image and weight label
    private void updateGUI() {
        gui.updateFrogLabel();
        gui.updateWeightLabel();
    }

}
