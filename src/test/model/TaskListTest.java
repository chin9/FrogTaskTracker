package model;

import exception.SubjectNotFoundException;
import exception.TaskNotFoundException;
import exception.TypeNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.Subject;

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
    public void testRemoveTaskTaskFound() {
        taskList.addTask(t1);
        taskList.addTask(t3);
        assertEquals(5, taskList.getTasks().size());
        assertEquals(2, taskList.getFrogWeight());
        try {
            taskList.removeTask(t2);
        } catch (TaskNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
        assertEquals(4, taskList.getTasks().size());
        assertTrue(taskList.getTasks().contains(t1));
        assertTrue(taskList.getTasks().contains(t3));
        assertEquals(1, taskList.getFrogWeight());
    }

    @Test
    public void testRemoveTaskNotFound() {
        try {
            taskList.removeTask(new Task("non-existent task"));
            fail("supposed to throw an exception");
        } catch (TaskNotFoundException exception) {
            //pass
        }
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
    public void testDisplayTaskOfTypeTypeFound() {

        try {
            assertEquals("Assignment 1\nPeer Review Worksheet\n", taskList.displayTasksOfType("Homework"));
        } catch (TypeNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
    }

    @Test
    public void testDisplayTaskOfTypeNotFound() {
        try {
            taskList.displayTasksOfType("non-existent type");
            fail("supposed to throw exception");
        } catch (TypeNotFoundException e) {
            //pass
        }
    }

    @Test
    public void testDisplayTaskOfSubjectSubjectFound() {

        try {
            assertEquals("Assignment 1\nQuiz 2B\n", taskList.displayTasksOfSubject("CPSC 121"));
        } catch (SubjectNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
    }

    @Test
    public void testDisplayTaskOfSubjectNotFound() {
        try {
            taskList.displayTasksOfSubject("non-existent subject");
            fail("supposed to throw an exception");
        } catch (SubjectNotFoundException e) {
            //pass
        }
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
    public void testEditTaskNameFound() {
        try {
            taskList.editTaskName(t2, "Speaker Series Worksheet");
        } catch (TaskNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
        assertEquals("Speaker Series Worksheet", taskList.getTask(1).getTaskName());
    }

    @Test
    public void testEditTaskNameNotFound() {
        try {
            taskList.editTaskName(new Task("non-existent task"), "Speaker Series Worksheet");
            fail("supposed to throw exception");
        } catch (TaskNotFoundException exception) {
            //pass
        }
    }

    @Test
    public void testEditTaskSubFound() {
        try {
            taskList.editTaskSub(t3, "MATH 101");
        } catch (TaskNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
        assertEquals("MATH 101",taskList.getTask(2).getSubject());
    }

    @Test
    public void testEditTaskSubNotFound() {
        try {
            taskList.editTaskSub(new Task("non-existent task"), "Speaker Series Worksheet");
            fail("supposed to throw an exception");
        } catch (TaskNotFoundException e) {
            //pass
        }
    }

    @Test
    public void testEditTaskTypeFound() {
        try {
            taskList.editTaskType(t3, "Miscellaneous");
        } catch (TaskNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
        assertEquals("Miscellaneous", taskList.getTask(2).getType());
    }

    @Test
    public void testEditTaskTypeNotFound() {
        try {
            taskList.editTaskType(new Task("non-existent task"), "Speaker Series Worksheet");
            fail("supposed to throw an exception");
        } catch (TaskNotFoundException exception) {
            //pass
        }
    }

    @Test
    public void testEditTaskDurFound() {
        try {
            taskList.editTaskDur(t2, 100);
        } catch (TaskNotFoundException e) {
            fail("Not supposed to throw this exception");
        }
        assertEquals(100, taskList.getTask(1).getDuration());
    }

    @Test
    public void testEditTaskDurNotFound() {
        try {
            taskList.editTaskDur(new Task("non-existent task"), 1);
            fail("supposed to throw an exception");
        } catch (TaskNotFoundException exception) {
            //pass
        }
    }

    @Test
    public void testEditTaskDescriptionFound() {
        try {
            taskList.editTaskDescription(t3, "Quiz on Propositional Logic");
        } catch (TaskNotFoundException exception) {
            fail("Not supposed to throw this exception");
        }
        assertEquals("Quiz on Propositional Logic", taskList.getTask(2).getDescription());
    }

    @Test
    public void testEditTaskDescriptionNotFound() {
        try {
            taskList.editTaskDescription(new Task("non-existent task"), "hahaha");
            fail("supposed to throw an exception");
        } catch (TaskNotFoundException exception) {
            //pass
        }
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
