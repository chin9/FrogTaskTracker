package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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


}
