package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeAndExceptionTest {
    Time testTime1;
    Time testTime2;
    Time testTime3;
    Time testTime4;
    Time testTime5;
    Time testTime6;

    @BeforeEach
    void runBefore() {
        testTime1 = new Time(22, 45);
        testTime2 = new Time(22, 15);
        try {
            testTime3 = new Time(24, 00);
            fail();
            testTime4 = new Time(12, 60);
            fail();
        } catch (IllegalTimeException e) {
            testTime3 = new Time(23, 30);
            testTime4 = new Time(22, 50);
        }
        testTime5 = new Time(1,9);
        testTime6 = new Time(4, 00);
    }

    @Test
    void testConstructor() {
        assertEquals(22, testTime1.getHour());
        assertEquals(45, testTime1.getMinute());
        assertEquals("22:45", testTime1.toString());
        assertEquals(1365, testTime1.toMinutes());

        assertEquals(22, testTime2.getHour());
        assertEquals(15, testTime2.getMinute());

        assertEquals(23, testTime3.getHour());
        assertEquals(30, testTime3.getMinute());

        assertEquals(22, testTime4.getHour());
        assertEquals(50, testTime4.getMinute());

        assertEquals("01:09", testTime5.toString());
        assertEquals("04:00", testTime6.toString());
    }

    @Test
    void testComparisons() {
        assertTrue(testTime1.earlier(testTime3));
        assertTrue(testTime3.later(testTime1));

        assertTrue(testTime1.earlier(testTime4));
        assertTrue(testTime4.later(testTime1));

        assertTrue(testTime1.later(testTime2));
        assertTrue(testTime2.earlier(testTime1));

        assertTrue(testTime3.sameTime(new Time(23,30)));
        assertFalse(testTime3.earlier(new Time(23,30)));
        assertFalse(testTime3.later(new Time(23,30)));
    }
}
