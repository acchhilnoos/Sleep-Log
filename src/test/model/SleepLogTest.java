package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SleepLogTest {
    SleepLog testLog1;
    SleepLog testLog2;

    NightSlept testNight1;
    NightSlept testNight2;
    NightSlept testNight3;
    NightSlept testNight4;

    @BeforeEach
    void runBefore() {
        testLog1 = new SleepLog("log1");
        testLog2 = new SleepLog("log2");

        testNight1 = new NightSlept(new Time(23,45), new Time(10,15));
        testNight2 = new NightSlept(new Time(22,30), new Time(7,30));
        testNight3 = new NightSlept(new Time(20,50), new Time(8,45));
        testNight4 = new NightSlept(new Time(21,10), new Time(6,20));
    }

    @Test
    void testConstructor() {
        assertEquals("log1", testLog1.getUsername());
        assertEquals(0, testLog1.getLog().size());
        assertEquals(testLog1.getLog().size(), testLog1.size());
        assertEquals("log2", testLog2 .getUsername());
        assertEquals(0, testLog2.getLog().size());
        assertEquals(testLog2.getLog().size(), testLog2.size());
    }

    @Test
    void testNightFns() {
        testLog1.add(testNight1);
        testLog2.add(testNight2);
        testLog2.add(testNight3);
        testLog2.add(testNight4);

        assertEquals(1, testLog1.size());
        assertEquals(3, testLog2.size());

        assertEquals(testNight1, testLog1.get(0));
        assertEquals(testNight2, testLog2.get(0));
        assertEquals(testNight3, testLog2.get(1));
        assertEquals(testNight4, testLog2.get(2));

        try {
            testLog1.get(1);
            fail();
            testLog2.get(3);
            fail();
        } catch (IndexOutOfBoundsException e) {

        }
        assertEquals(testNight1, testLog1.remove(0));
        assertEquals(0, testLog1.size());

        assertEquals(2, testLog2.indexOf(testNight4));
    }

    @Test
    void testJson() {
        testLog1.add(testNight1);
        testLog2.add(testNight2);
        testLog2.add(testNight3);
        testLog2.add(testNight4);

        assertEquals("{\"nights\":[{\"interruptions\":[],\"start\":\"23:45\",\"end\":\"10:15\"}]," +
                "\"name\":\"log1\"}", testLog1.toJson().toString());

        assertEquals("log2", testLog2.toJson().getString("name"));
        assertEquals(3, testLog2.toJson().getJSONArray("nights").length());
    }
}
