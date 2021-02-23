package model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test for Task
public class TaskTest {

    private Task myTask;

    @BeforeEach
    public void runBefore() {
        myTask = new Task("Assignment 1");
    }

    @Test
    public void testSetName() {
        myTask.setName("Assignment 2");
        assertEquals("Assignment 2", myTask.getTaskName());
    }

    @Test
    public void testSetSubject() {
        myTask.setSubject("MATH 101");
        assertEquals("MATH 101", myTask.getSubject());
    }

    @Test
    public void testSetType() {
        myTask.setType("Homework");
        assertEquals("Homework", myTask.getType());

    }

    @Test
    public void testSetDuration() {
        myTask.setDuration(60);
        assertEquals(60, myTask.getDuration());
    }

    @Test
    public void testSetDescription() {
        myTask.setDescription("Practiced converting between Riemann sum and integrals.");
        assertEquals("Practiced converting between Riemann sum and integrals.", myTask.getDescription());
    }

    @Test
    public void testToString() {
        myTask.setSubject("MATH 101");
        myTask.setType("Homework");
        myTask.setDuration(180);
        myTask.setDescription("Integral and Riemann Sum");

        assertEquals("Name: Assignment 1\n" +
                        "Subject: MATH 101\n" +
                        "Type: Homework\n" +
                        "Duration: 180 minutes\n" +
                        "Description: Integral and Riemann Sum",
                myTask.toString());
    }



}
