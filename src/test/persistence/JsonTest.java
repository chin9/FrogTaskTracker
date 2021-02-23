package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo
public class JsonTest {
    protected void checkTask(String name, String sub, String type, int dur, String des, Task t) {
        assertEquals(name, t.getTaskName());
        assertEquals(sub, t.getSubject());
        assertEquals(type, t.getType());
        assertEquals(dur, t.getDuration());
        assertEquals(des, t.getDescription());
    }
}
