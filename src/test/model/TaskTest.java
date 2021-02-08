package model;

import model.Task;
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
    public void testEditName() {
        myTask.editName("Assignment 2");
        assertEquals("Assignment 2", myTask.getTaskName());
    }

    @Test
    public void testEditSubject() {
        myTask.editSubject("MATH 101");
        assertEquals("MATH 101", myTask.getSubject());
    }

    @Test
    public void testEditType() {
        myTask.editType("Homework");
        assertEquals("Homework", myTask.getType());

    }

    @Test
    public void testEditDuration() {
        myTask.editDuration(60);
        assertEquals(60, myTask.getDuration());
    }

    @Test
    public void testEditDescription() {
        myTask.editDescription("Practiced converting between Riemann sum and integrals.");
        assertEquals("Practiced converting between Riemann sum and integrals.", myTask.getDescription());
    }


}
