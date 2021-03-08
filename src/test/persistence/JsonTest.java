package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.*;

// Test for Json
// CITATION: JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkTask(String name, String sub, String type, int dur, String des, Task t) {
        assertEquals(name, t.getTaskName());
        assertEquals(sub, t.getSubject());
        assertEquals(type, t.getType());
        assertEquals(dur, t.getDuration());
        assertEquals(des, t.getDescription());
    }
}
