package gui.listeners;

import exception.TaskNotFoundException;
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

        removeTaskFromTaskList(tl, listModel, index);

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
    //EFFECTS: removes selected task from tl
    private void removeTaskFromTaskList(TaskList tl, DefaultListModel listModel, int index) {
        for (Task t : tl.getTasks()) {
            if (listModel.get(index).equals(t.getTaskName())) {
                try {
                    tl.removeTask(t);
                } catch (TaskNotFoundException exception) {
                    System.out.println("Task not found!");
                }
                break;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: updates the gui's image and weight label
    private void updateGUI() {
        gui.updateFrogLabel();
        gui.updateWeightLabel();
    }

}
