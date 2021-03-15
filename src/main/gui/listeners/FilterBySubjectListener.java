package gui.listeners;

import gui.TaskListGUI;
import model.Task;
import model.TaskList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterBySubjectListener implements ActionListener, DocumentListener {


    TaskListGUI gui;

    public FilterBySubjectListener(TaskListGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        TaskList tl = gui.getTl();
        JTextField newSubject = gui.getNewSubject();

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
        gui.add(listScrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(gui, listScrollPane, "Subject: " + newSubject.getText(),
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
