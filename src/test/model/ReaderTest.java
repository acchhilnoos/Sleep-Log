package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    void invalidFileTest() {
        JsonReader testReader = new JsonReader("./data/invalid.json");
        assertNull(testReader.read());
    }

    @Test
    void emptyLogTest() {
        JsonReader testReader = new JsonReader("./data/emptyLogReaderTest.json");
        SleepLog emptyTest = testReader.read();
        assertEquals("[EMPTY]", emptyTest.getUsername());
        assertEquals(0, emptyTest.size());
    }

    @Test
    void readerTest() {
        JsonReader testReader = new JsonReader("./data/readerTest.json");
        SleepLog readTest = testReader.read();
        assertEquals("[TEST]", readTest.getUsername());
        assertEquals(2, readTest.size());
        assertEquals(1, readTest.get(1).getNumInterruptions());
        assertEquals(5, readTest.get(1).getInterruptions().get(0).getDuration());
    }
}
