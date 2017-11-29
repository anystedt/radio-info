/**
 * Created on 17/11/17
 * File: APIRetrieverTest.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Tests if the testable methods in APIRetriever returns the expected
 * value. Uses when needed the class TimeHolder to know the limits
 * of the tableau.
 */

package model;

import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class APIRetrieverTest {

    private APIRetriever apiRetriever;
    private TimeHolder timeHolder;

    @Before
    public void setUp(){
        apiRetriever = new APIRetriever();
        timeHolder = new TimeHolder();
    }

    @Test(expected =  MalformedURLException.class)
    public void testMalformedURLException(){
        apiRetriever.parseXML("URL", "tagName");
    }

    /**
     * Tests that the method returns true if the given time is within
     * the limits of the tableau.
     */
    @Test
    public void shouldReturnTrueInSchedule(){
        assertTrue(apiRetriever.belongsToSchedule(
                LocalDateTime.now().toString()+"Z", timeHolder));
    }

    /**
     * Tests that the method returns false if the given time is
     * before the start of the tableau.
     */
    @Test
    public void shouldReturnFalseBeforeSchedule(){
        assertFalse(apiRetriever.belongsToSchedule(
                LocalDateTime.now().minusHours(13).toString()+"Z",
                timeHolder));
    }

    /**
     * Tests that the method returns false if the given time is after
     * the end of the tableau.
     */
    @Test
    public void shouldReturnFalseAfterSchedule(){
        assertFalse(apiRetriever.belongsToSchedule(
                LocalDateTime.now().plusHours(13).toString()+"Z",
                timeHolder));
    }

}