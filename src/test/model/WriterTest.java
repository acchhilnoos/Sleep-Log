package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    @Test
    void invalidFileTest() {
        JsonWriter testWriter = new JsonWriter("./data/invalid.json");
        try {
            testWriter.open();
            testWriter.close();
            fail();
        } catch (FileNotFoundException e) {

        }
    }

    @Test
    void emptyLogTest() {
        JsonWriter testWriter = new JsonWriter("./data/emptyLogWriterTest.json");
        SleepLog testLog = new SleepLog("[EMPTY]");
        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
            fail();
        }
        testWriter.write(testLog);
        testWriter.close();

        JsonReader testReader = new JsonReader("./data/emptyLogWriterTest.json");
        testLog = testReader.read();
        assertEquals("[EMPTY]", testLog.getUsername());
        assertEquals(0, testLog.size());
    }

    @Test
    void writerTest() {
        JsonWriter testWriter = new JsonWriter("./data/writerTest.json");
        SleepLog testLog = new SleepLog("[USERNAME]");
        testLog.add(new NightSlept(new Time(23,30), new Time(9,25)));
        testLog.add(new NightSlept(new Time(22,50), new Time(7,15)));
        testLog.get(1).addInterruption(new Interruption(new Time(5,15), new Time(5,20)));
        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
            fail();
        }
        testWriter.write(testLog);
        testWriter.close();

        JsonReader testReader = new JsonReader("./data/writerTest.json");
        testLog = testReader.read();
        assertEquals("[USERNAME]", testLog.getUsername());
        assertEquals(2, testLog.size());
        assertEquals(1, testLog.get(1).getNumInterruptions());
    }
}
