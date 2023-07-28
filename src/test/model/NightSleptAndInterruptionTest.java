package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NightSleptAndInterruptionTest {
    private NightSlept testNight1;
    private NightSlept testNight2;
    private Time testNight1Start;
    private Time testNight1End;
    private Time testNight2Start;
    private Time testNight2End;
    private Timespan night2Span;
    private Timespan inter3Span;
    private Timespan inter4Span;

    private Interruption testInter1;
    private Interruption testInter2;
    private Interruption testInter3;
    private Interruption testInter4;

    private List<Interruption> testInterruptions;

    private JSONObject nightObj1;
    private JSONObject nightObj2;

    @BeforeEach
    void runBefore() {
        testNight1Start = new Time(23, 45);
        testNight1End = new Time(8,30);
        testNight2Start = new Time(3, 20);
        testNight2End = new Time(9,20);
        night2Span = new Timespan(testNight2Start, testNight2End);
        inter3Span = new Timespan(new Time(4,30), new Time(4,40));
        inter4Span = new Timespan(new Time(4,35), new Time(4,45));

        testInter1 = new Interruption(new Time(23,40), new Time(23,50));
        testInter2 = new Interruption(new Time(8,25), new Time(8,35));
        testInter3 = new Interruption(inter3Span);
        testInter4 = new Interruption(inter4Span);

        testInterruptions = new ArrayList<>();
        testInterruptions.add(testInter3);

        testNight1 = new NightSlept(testNight1Start, testNight1End);
        testNight2 = new NightSlept(night2Span);

        nightObj1 = testNight1.toJson();
        nightObj2 = testNight2.toJson();
    }

    @Test
    void testConstructor() {
        assertEquals(23, testNight1.getStart().getHour());
        assertEquals(45, testNight1.getStart().getMinute());
        assertEquals(8, testNight1.getEnd().getHour());
        assertEquals(30, testNight1.getEnd().getMinute());
        assertEquals(0, testNight1.getNumInterruptions());
        assertEquals("23:45-08:30 (0)", testNight1.toString());

        assertEquals(3, testNight2.getStart().getHour());
        assertEquals(20, testNight2.getStart().getMinute());
        assertEquals(9, testNight2.getEnd().getHour());
        assertEquals(20, testNight2.getEnd().getMinute());
        assertEquals(0, testNight2.getNumInterruptions());
        assertEquals("03:20-09:20 (0)", testNight2.toString());
    }

    @Test
    void testInterruptionsAndDuration() {
        assertNull(testNight1.removeLastInterruption());
        assertEquals(525, testNight1.getDuration());
        assertEquals(0, testNight1.getNumInterruptions());

        assertTrue(testNight1.addInterruption(testInter1));
        assertTrue(testNight1.addInterruption(testInter2));
        assertTrue(testNight1.addInterruption(testInter3));
        assertFalse(testNight1.addInterruption(testInter4));

        assertEquals(23, testNight1.getStart().getHour());
        assertEquals(50, testNight1.getStart().getMinute());
        assertEquals(8, testNight1.getEnd().getHour());
        assertEquals(25, testNight1.getEnd().getMinute());

        assertEquals(505, testNight1.getDuration());
        assertEquals(1, testNight1.getNumInterruptions());
        assertEquals(testInterruptions, testNight1.getInterruptions());

        assertEquals(testInter3, testNight1.removeLastInterruption());
        assertEquals(515, testNight1.getDuration());
        assertEquals(0, testNight1.getNumInterruptions());
    }

    @Test
    void jsonTest() {
        assertEquals("{\"interruptions\":[],\"start\":\"23:45\",\"end\":\"08:30\"}", nightObj1.toString());
        assertEquals("{\"interruptions\":[],\"start\":\"03:20\",\"end\":\"09:20\"}", nightObj2.toString());
        testNight2.addInterruption(testInter3);
        nightObj2 = testNight2.toJson();
        assertEquals("{\"interruptions\":[{\"start\":\"04:30\",\"end\":\"04:40\"}]," +
                "\"start\":\"03:20\",\"end\":\"09:20\"}", nightObj2.toString());
    }
}