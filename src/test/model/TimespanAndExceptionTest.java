package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TimespanAndExceptionTest {
    private Timespan testSpan1;
    private Timespan testSpan2;
    private Timespan testSpan3;

    private Time testSpan1Start;
    private Time testSpan1End;
    private Time testSpan2Start;
    private Time testSpan2End;
    private Time testSpan3Start;
    private Time testSpan3End;

    @BeforeEach
    void runBefore() {
        testSpan1Start = new Time(9,00);
        testSpan1End = new Time(12,00);
        testSpan2Start = new Time(11,30);
        testSpan2End = new Time(11,15);
        testSpan3Start = new Time(23,45);
        testSpan3End = new Time(1,20);

        testSpan1 = new Timespan(testSpan1Start, testSpan1End);
        testSpan2 = new Timespan(testSpan2Start, testSpan2End);
        testSpan3 = new Timespan(testSpan3Start, testSpan3End);
    }

    @Test
    void testConstructor() {
        assertEquals(testSpan1Start, testSpan1.getStart());
        assertEquals(testSpan1End, testSpan1.getEnd());
        assertEquals(180, testSpan1.getDuration());
        assertEquals("09:00-12:00", testSpan1.toString());

        assertEquals(testSpan2Start, testSpan2.getStart());
        assertEquals(testSpan2End, testSpan2.getEnd());
        assertEquals(1425, testSpan2.getDuration());
        assertEquals("11:30-11:15", testSpan2.toString());

        assertEquals(testSpan3Start, testSpan3.getStart());
        assertEquals(testSpan3End, testSpan3.getEnd());
        assertEquals(95, testSpan3.getDuration());
        assertEquals("23:45-01:20", testSpan3.toString());
    }

    @Test
    void testEditAndOverlapAndDuration() {
        assertEquals(180, testSpan1.getDuration());
        assertEquals(1425, testSpan2.getDuration());
        assertEquals(95, testSpan3.getDuration());

        try {
            testSpan1.edit(testSpan1Start, testSpan1Start);
            fail();
        } catch (IllegalTimespanException e) {
            assertEquals(180, testSpan1.getDuration());
        }
        testSpan1.edit(testSpan2Start, testSpan1End);
        assertEquals(30, testSpan1.getDuration());

        assertEquals(testSpan1.toString(), testSpan1.overlaps(testSpan2).toString());
        assertEquals(testSpan1.toString(), testSpan2.overlaps(testSpan1).toString());

        testSpan2.edit(testSpan2End, testSpan2Start);
        assertNull(testSpan2.overlaps(testSpan3));
        assertNull(testSpan3.overlaps(testSpan2));

        testSpan1.edit(testSpan1Start, new Time(23,50));
        assertEquals(890, testSpan1.getDuration());
        assertEquals(5, testSpan3.overlaps(testSpan1).getDuration());
        assertEquals(5, testSpan1.overlaps(testSpan3).getDuration());
    }
}
