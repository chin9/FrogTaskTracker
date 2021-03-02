package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test for JsonReader
// CITATION: JsonSerializationDemo
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TaskList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTaskList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTaskList.json");
        try {
            TaskList tl = reader.read();
            assertEquals("", tl.displayAllTaskNames());
            assertEquals(0, tl.getTasks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTaskList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTaskList.json");
        try {
            TaskList tl = reader.read();
            assertEquals(1, tl.getFrogWeight());
            List<Task> tasks = tl.getTasks();
            assertEquals(2, tasks.size());
            checkTask("Phase 2", "CPSC 210", "Project", 200, "Data Persistence",
                    tasks.get(0));
            checkTask("Assignment 5", "MATH 101", "Assignment", 90, "Integration By Parts",
                    tasks.get(1));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
