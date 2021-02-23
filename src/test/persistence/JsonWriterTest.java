package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            TaskList tl = new TaskList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTaskList() {
        try {
            TaskList tl = new TaskList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTaskList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTaskList.json");
            tl = reader.read();
            assertEquals(1, tl.getFrogWeight());
            assertEquals(0, tl.getTasks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTaskList() {
        Task t0 = new Task("Phase 2");
        t0.setSubject("CPSC 210");
        t0.setType("Project");
        t0.setDuration(200);
        t0.setDescription("Data Persistence");

        Task t1 = new Task("Assignment 5");
        t1.setSubject("MATH 101");
        t1.setType("Assignment");
        t1.setDuration(90);
        t1.setDescription("Integration By Parts");

        try {
            TaskList tl = new TaskList();
            tl.addTask(t0);
            tl.addTask(t1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTaskList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTaskList.json");
            tl = reader.read();
            assertEquals(1, tl.getFrogWeight());
            List<Task> tasks = tl.getTasks();
            assertEquals(2, tasks.size());
            checkTask("Phase 2", "CPSC 210", "Project", 200, "Data Persistence",
                    tasks.get(0));
            checkTask("Assignment 5", "MATH 101", "Assignment", 90, "Integration By Parts",
                    tasks.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
