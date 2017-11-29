/**
 * Created on 21/11/17
 * File: TimeHolderTest.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Tests that all the testable methods in TimeHolder returns the
 * expected value. An instance of the class is used to do the
 * evaluation.
 */
package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeHolderTest {

    private TimeHolder timeHolder;

    @Before
    public void setUp(){
        timeHolder = new TimeHolder();
    }

    /**
     * Tests that the start of the tableau is set 12 hours before
     * the current time.
     */
    @Test
    public void shouldReturnTrueGetStart(){
        assertEquals(LocalDateTime.now().minusHours(12).toLocalDate()
                .toString(), timeHolder.getStartDateString());
    }

    /**
     * Tests that the end of the tableau ois set to 12 hours after
     * the current time.
     */
    @Test
    public void shouldReturnTrueGetEnd(){
        assertEquals(LocalDateTime.now().plusHours(12).toLocalDate()
                .toString(), timeHolder.getEndDateString());
    }

    /**
     * Tests that the method returns true if the given time is before
     * the start of the tableau.
     */
    @Test
    public void shouldReturnTrueBeforeTableauStart(){
        assertTrue(timeHolder.isBeforeTableauStart(
                LocalDateTime.now().minusHours(13).toString()+"Z"));
    }

    /**
     * Tests that the method returns false of the given time is after
     * the start of the tableau.
     */
    @Test
    public void shouldReturnFalseAfterTableauStart(){
        assertFalse(timeHolder.isBeforeTableauStart(LocalDateTime.now().toString()+"Z"));
    }

    /**
     * Tests that the method returns false if the given time is before
     * the end of the tableau.
     */
    @Test
    public void shouldReturnFalseBeforeTableauEnd(){
        assertFalse(timeHolder.isAfterTableauEnd(LocalDateTime.now().toString()+"Z"));
    }

    /**
     * Tests that the method returns true if the given time is after
     * the end of the tableau.
     */
    @Test
    public void shouldReturnTrueAfterTableauEnd(){
        assertTrue(timeHolder.isAfterTableauEnd(LocalDateTime.now().plusHours(13).toString()+"Z"));
    }
}