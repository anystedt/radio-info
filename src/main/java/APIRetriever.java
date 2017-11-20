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

        TimeHolder timeHolder = new TimeHolder();

        for(int i = 0; i < xml.getLength(); i++){
            Channel channel = new Channel();
            Node node = xml.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eChannel = (Element) node;
                channel.setId(parseInt(eChannel.getAttribute("id")));
                channel.setName(eChannel.getAttribute("name"));
                channel.setImage(eChannel.getElementsByTagName("image").item(0).getTextContent());
                getPrograms(channel.getId().toString(), timeHolder);
            }
        }
    }

    public void getPrograms(String channelId, TimeHolder timeHolder){
        NodeList xmlPrograms = parseXML("http://api.sr.se/v2/scheduledepisodes?channelid=" + channelId + "&fromdate=" + timeHolder.getStartDateString() + "&todate=" + timeHolder.getEndDateString() + "&pagination=false", "scheduledepisode");

        for(int i = 0; i < xmlPrograms.getLength(); i++){
            Program program = new Program();
            Node node = xmlPrograms.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eProgram = (Element) node;

                //Ska kolla om det givna programmet är inom tidsramen för de program som ska visas.
                if(belongsToSchedule(eProgram.getElementsByTagName("starttimeutc").item(0).getTextContent(), timeHolder)){
                    System.out.println(eProgram.getElementsByTagName("starttimeutc").item(0).getTextContent());
                }

                program.setTitle(eProgram.getElementsByTagName("title").item(0).getTextContent());
            }
        }

        System.out.println("\n\n");
    }

    public boolean belongsToSchedule(String startTime, TimeHolder timeHolder){
        if(!timeHolder.isBeforeScheduleStart(startTime) &&
                !timeHolder.isAfterScheduleEnd(startTime)){
            return true;
        } else {
            return false;
        }
    }
}