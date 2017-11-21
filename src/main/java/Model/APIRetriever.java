package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class APIRetriever {

    public APIRetriever(){}

    public void getAPI(){
        NodeList xmlDoc = parseXML("http://api.sr.se/api/v2/channels/?pagination=false", "channel");
        getChannels(xmlDoc);
    }

    public NodeList parseXML(String stringUrl, String tagName){
        NodeList xml = null;

        try {
            URL url = new URL(stringUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            doc.getDocumentElement().normalize();
            xml = doc.getElementsByTagName(tagName);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return xml;
    }

    public void getChannels(NodeList xml){

        //Create a timeholder that holds the current time and the
        //limits for the tableaux.
        TimeHolder timeHolder = new TimeHolder();

        //Goes through the list of nodes and creates all the channels
        //and the tableau for each channel.
        for(int i = 0; i < xml.getLength(); i++){
            Channel channel = new Channel();
            Node node = xml.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eChannel = (Element) node;

                channel.setId(parseInt(eChannel.getAttribute("id")));
                channel.setName(eChannel.getAttribute("name"));
                channel.setImage(getTag(eChannel, "image"));
                channel.setTableau(parseTableau(channel.getId().toString(), timeHolder));
            }
        }
    }

    public String getTag(Element element, String tagName){
        NodeList nl = element.getElementsByTagName(tagName);

        if(nl.getLength() != 0){
            return nl.item(0).getTextContent();
        } else{
            return null;
        }
    }

    public List<Program> parseTableau(String channelId, TimeHolder timeHolder){
        NodeList xmlPrograms = parseXML(getTableauUrl(channelId, timeHolder),
                "scheduledepisode");
        List<Program> channelTableau = new ArrayList<>();

        //Goes through all the programs in the channels tableau
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

    public String getTableauUrl(String channelId, TimeHolder timeHolder){
        return "http://api.sr.se/v2/scheduledepisodes?channelid="
                + channelId + "&fromdate=" + timeHolder.getStartDateString()
                + "&todate=" + timeHolder.getEndDateString() +
                "&pagination=false";
    }

    public boolean belongsToSchedule(String startTime, TimeHolder timeHolder){
        return !timeHolder.isBeforeTableauStart(startTime) &&
                !timeHolder.isAfterTableauEnd(startTime);
    }

    public Program getProgramInfo(Element eProgram){
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