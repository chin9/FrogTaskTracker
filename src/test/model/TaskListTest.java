package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Test for TaskList
public class TaskListTest {

    private TaskList taskList;
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        t1 = new Task("Assignment 1");
        t2 = new Task("Peer Review Worksheet");
        t3 = new Task("Quiz 2B");

        t1.setType("Homework");
        t2.setType("Homework");
        t3.setType("Quiz");

        t1.setSubject("CPSC 121");
        t2.setSubject("SCIE 113");
        t3.setSubject("CPSC 121");

        t1.setDuration(120);
        t2.setDuration(180);
        t3.setDuration(200);

        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);
    }

    @Test
    public void testAddTask() {
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);
        assertEquals(6, taskList.getTasks().size());
        assertTrue(taskList.getTasks().contains(t1));
        assertEquals(2, taskList.getFrogWeight());
    }

    @Test
    public void testRemoveTask() {
        taskList.addTask(t1);
        taskList.addTask(t3);
        assertEquals(5, taskList.getTasks().size());
        assertEquals(2, taskList.getFrogWeight());
        taskList.removeTask(t2);
        assertEquals(4, taskList.getTasks().size());
        assertTrue(taskList.getTasks().contains(t1));
        assertTrue(taskList.getTasks().contains(t3));
        assertEquals(1, taskList.getFrogWeight());
    }

    @Test
    public void testGetTask() {
        assertEquals(t1, taskList.getTask(0));
        assertEquals(t2, taskList.getTask(1));
        assertEquals(t3, taskList.getTask(2));

    }

    @Test
    public void testDisplayAllTaskNames() {

        assertEquals("Assignment 1\nPeer Review Worksheet\nQuiz 2B\n", taskList.displayAllTaskNames());
    }

    @Test
    public void testDisplayTaskOfType() {

        assertEquals("Assignment 1\nPeer Review Worksheet\n", taskList.displayTasksOfType("Homework"));
    }

    @Test
    public void testDisplayTaskOfSubject() {

        assertEquals("Assignment 1\nQuiz 2B\n", taskList.displayTasksOfSubject("CPSC 121"));
    }

    @Test
    public void testDisplayTotalDur() {
        assertEquals(500, taskList.displayTotalDuration());
    }

    @Test
    public void testGetTaskByNameContains() {
        assertEquals(t2, taskList.getTaskByName("Peer Review Worksheet"));
    }

    @Test
    public void testGetTaskByNameNone() {
        assertNull(taskList.getTaskByName("Reading Break"));
    }

    @Test
    public void testEditTaskName() {
        taskList.editTaskName(t2, "Speaker Series Worksheet");
        assertEquals("Speaker Series Worksheet", taskList.getTask(1).getTaskName());
    }

    @Test
    public void testEditTaskSub() {
        taskList.editTaskSub(t3, "MATH 101");
        assertEquals("MATH 101",taskList.getTask(2).getSubject());
    }

    @Test
    public void testEditTaskType() {
        taskList.editTaskType(t3, "Miscellaneous");
        assertEquals("Miscellaneous", taskList.getTask(2).getType());
    }

    @Test
    public void testEditTaskDur() {
        taskList.editTaskDur(t2, 100);
        assertEquals(100, taskList.getTask(1).getDuration());
    }

    @Test
    public void testEditTaskDescription() {
        taskList.editTaskDescription(t3, "Quiz on Propositional Logic");
        assertEquals("Quiz on Propositional Logic", taskList.getTask(2).getDescription());
    }

    @Test
    public void testTasksToJson() {
        JSONArray json = new JSONArray();
        json.put(t1.toJson());
        json.put(t2.toJson());
        json.put(t3.toJson());

        assertEquals(json.toString(), taskList.tasksToJson().toString());

    }

    @Test
    public void testToJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(t1.toJson());
        jsonArray.put(t2.toJson());
        jsonArray.put(t3.toJson());

        json.put("tl", jsonArray);
        json.put("frogWeight", 1);

        assertEquals(json.toString(), taskList.toJson().toString());

    }


}
