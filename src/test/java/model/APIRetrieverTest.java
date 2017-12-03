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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    /**
     * Tests that an exception is thrown when the given url is not valid.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test(expected =  MalformedURLException.class)
    public void testMalformedURLException() throws IOException,
            ParserConfigurationException, SAXException {
        apiRetriever.parseXML(new URL("URL").openStream(),
                "tagName");
    }

    /**
     * Tests that something is retrieved when the url is valid.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void shouldReturnNotNullChannelsXML() throws IOException,
            ParserConfigurationException, SAXException {
        assertNotNull(apiRetriever.parseXML(new FileInputStream
                ("src/test/java/model/assets/channels.xml"),
                "channel"));
    }

    /**
     * Tests that the number of retrieved channels is the same as
     * number of channels in file.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void shouldReturnAllChannels() throws IOException,
            ParserConfigurationException, SAXException {
        assertEquals(apiRetriever.parseXML(new FileInputStream
                ("src/test/java/model/assets/channels.xml"),
                "channel").getLength(), 10);
    }

    /**
     * Tests that a list with one node is returned when there is only
     * one tag with the given name.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void shouldReturnOneNode() throws IOException, ParserConfigurationException, SAXException {
        assertEquals(apiRetriever.parseXML(new FileInputStream
                        ("src/test/java/model/assets/channels.xml"),
                "sr").getLength(), 1);
    }

    /**
     * Test that the number of retrieved nodes is zero when a invalid
     * tag name is used.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void shouldNotReturnAny() throws IOException,
            ParserConfigurationException, SAXException {
        assertEquals(apiRetriever.parseXML(new FileInputStream
                ("src/test/java/model/assets/channels.xml"),
                "hej").getLength(), 0);
    }

    /**
     * Tests that the list of channels is not null when channel content
     * is extracted.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void shouldReturnChannelList() throws IOException,
            ParserConfigurationException, SAXException {
        NodeList xml = apiRetriever.parseXML(new FileInputStream
                ("src/test/java/model/assets/channels.xml"),
                "channel");
        assertNotNull(apiRetriever.setChannelContent(xml));
    }

    /**
     * Tests that all channels are stored in the list when retrieving
     * the content of the channels.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void shouldReturnAllChannelList() throws ParserConfigurationException, SAXException, IOException {
        NodeList xml = apiRetriever.parseXML(new FileInputStream
                        ("src/test/java/model/assets/channels.xml"),
                "channel");
        assertEquals(apiRetriever.setChannelContent(xml).size(), 10);
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