package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event testEvent;
    private Date testDate;
    
    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.
    
    @BeforeEach
    public void runBefore() {
        testEvent = new Event("Added a drink to the menu");   // (1)
        testDate = Calendar.getInstance().getTime();   // (2)
    }
    
    @Test
    public void testEvent() {
        assertEquals("Added a drink to the menu", testEvent.getDescription());
        assertEquals(testDate, testEvent.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(testDate.toString() + "\n" + "Added a drink to the menu", testEvent.toString());
    }
}
