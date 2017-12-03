/**
 * Created on 17/11/17
 * File: APIRetriever.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class responsible for retrieving the channels and programs from
 * the SR-api. Gets all the channels and the essential programs with
 * help from a TimeHolder(that keeps track of the times) and stores
 * these in a list.
 */

package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class APIRetriever {

    private List<Channel> listOfChannels;

    public APIRetriever(){}

    /**
     * Returns a list containing the channels. For each channel the
     * information and the tableau for that channel has been extracted.
     * @return a list containing the channels.
     * @throws IOException if the connection fails
     * @throws SAXException if the XML parser fails
     * @throws ParserConfigurationException if configuration error occurs
     */
    public List<Channel> getChannels() throws
            ParserConfigurationException, SAXException, IOException {
        URL url = new URL("http://api.sr.se/api/v2/channels/?pagination=false");
        NodeList xml = parseXML(url.openStream(), "channel");
        listOfChannels = setChannelContent(xml);

        return listOfChannels;
    }

    /**
     * Returns a list containing channels holding information about
     * the channels retrieved from a given node list
     * @param xml the node list containing information about the
     *            channels
     * @return a list of channels
     * @throws IOException if the connection fails
     * @throws SAXException if the XML parser fails
     * @throws ParserConfigurationException if configuration error occurs
     */
    public List<Channel> setChannelContent(NodeList xml)
            throws IOException, SAXException,
            ParserConfigurationException {
        //Creates a timeholder that keeps track of the limits for
        // the tableau.
        TimeHolder timeHolder = new TimeHolder();
        listOfChannels = new ArrayList<>();

        //Goes through the list of nodes and creates all channels
        //and the tableau for each channel.
        for(int i = 0; i < xml.getLength(); i++){
            Channel channel = new Channel();
            Node node = xml.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eChannel = (Element) node;

                channel.setId(parseInt(eChannel.getAttribute("id")));
                channel.setName(eChannel.getAttribute("name"));
                channel.setImageUrl(getTag(eChannel, "image"));
                channel.setTableau(parseTableau(channel.getId().toString(), timeHolder));
                listOfChannels.add(channel);
            }
        }
        return listOfChannels;
    }

    /**
     * Uses a inputstream and a tag name to retrieve all the tags
     * with the given name. Returns a node list containing all the
     * extracted nodes.
     * @param input
     * @param tagName
     * @return a node list containing the extracted nodes.
     * @throws IOException if the connection fails
     * @throws SAXException if the XML parser fails
     * @throws ParserConfigurationException if configuration error occurs
     */
    public NodeList parseXML(InputStream input, String tagName)
            throws IOException, ParserConfigurationException,
            SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(input);
        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName(tagName);
    }

    /**
     * Returns the content of a element tag if it exists, otherwise
     * return null.
     * @param element the element
     * @param tagName the tag name for the tag of interest.
     * @return the content of the tag if it exists, otherwise return
     * null.
     */
    private String getTag(Element element, String tagName){
        NodeList nl = element.getElementsByTagName(tagName);
        if(nl.getLength() != 0){
            return nl.item(0).getTextContent();
        } else{
            return null;
        }
    }

    /**
     * Gets the programs for a specific channel.
     * @param channelId the id of the channel
     * @param timeHolder the timeholder keeping track of the limits
     *                   of the tableau.
     * @return the list of programs
     * @throws IOException if the connection fails
     * @throws SAXException if the XML parser fails
     * @throws ParserConfigurationException if configuration error occurs
     */
    private List<Program> parseTableau(String channelId, TimeHolder timeHolder)
            throws ParserConfigurationException, SAXException,
            IOException {
        NodeList xmlPrograms = parseXML(getTableauUrl(channelId,
                timeHolder).openStream(), "scheduledepisode");
        List<Program> channelTableau = new ArrayList<>();

        //Goes through all the programs in the channel's tableau
        for(int i = 0; i < xmlPrograms.getLength(); i++){
            Node node = xmlPrograms.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eProgram = (Element) node;

                //If the given program starts within the limits of
                //the tableau, add it to the list of programs
                if(belongsToSchedule(getTag(eProgram, "starttimeutc"), timeHolder)){
                    Program program = getProgramInfo(eProgram);
                    channelTableau.add(program);
                }
            }
        }

        return channelTableau;
    }

    /**
     * Returns a string containing the url for retrieving all programs
     * for a specific channel.
     * @param channelId the id of the channel.
     * @param timeHolder the timeholder keeping track of the limits
     *                   for the tableau.
     * @return a url.
     * @throws MalformedURLException if the url fails.
     */
    private URL getTableauUrl(String channelId, TimeHolder timeHolder) throws MalformedURLException {

        return new URL("http://api.sr.se/v2/scheduledepisodes?channelid="
                + channelId + "&fromdate=" + timeHolder.getStartDateString()
                + "&todate=" + timeHolder.getEndDateString() +
                "&pagination=false");
    }

    /**
     * Returns true if the program belongs to the tableau.
     * @param startTime the start time of the program.
     * @param timeHolder the timeholder.
     * @return true if the program belongs to the tableau, otherwise
     * false.
     */
    public boolean belongsToSchedule(String startTime, TimeHolder timeHolder){
        return !timeHolder.isBeforeTableauStart(startTime) &&
                !timeHolder.isAfterTableauEnd(startTime);
    }

    /**
     * Gets the program information from a given element and returns
     * a program.
     * @param eProgram the program element.
     * @return the program containing all essential information.
     */
    private Program getProgramInfo(Element eProgram){
        Program program = new Program();

        program.setTitle(getTag(eProgram, "title"));
        program.setSubtitle(getTag(eProgram, "subtitle"));
        program.setDescription(getTag(eProgram, "description"));
        program.setImageUrl(getTag(eProgram, "imageurl"));
        program.setStart(getTag(eProgram, "starttimeutc"));
        program.setEnd(getTag(eProgram, "endtimeutc"));

        return program;
    }
}