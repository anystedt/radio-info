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
import java.util.Date;

import static java.lang.Integer.parseInt;

public class APIRetriever {

    public APIRetriever(){}

    public void getAPI(){
        NodeList xmlDoc = getXML("http://api.sr.se/api/v2/channels/?pagination=false", "channel");
        getChannels(xmlDoc);
    }

    public NodeList getXML(String stringUrl, String tagName){
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
        for(int i = 0; i < xml.getLength(); i++){
            Channel channel = new Channel();
            Node node = xml.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eChannel = (Element) node;
                channel.setId(parseInt(eChannel.getAttribute("id")));
                channel.setName(eChannel.getAttribute("name"));
                channel.setImage(eChannel.getElementsByTagName("image").item(0).getTextContent());
                getPrograms(channel.getId().toString());
            }
        }
    }

    public void getPrograms(String channelId){
        NodeList xmlPrograms = getXML("http://api.sr.se/v2/scheduledepisodes?channelid=" + channelId + "&date=2017-11-17&pagination=false", "scheduledepisode");

        for(int i = 0; i < xmlPrograms.getLength(); i++){
            Program program = new Program();
            Node node = xmlPrograms.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element eProgram = (Element) node;

                //Ska kolla om det givna programmet är inom tidsramen för de program som ska visas.
                if(true){

                }
                program.setTitle(eProgram.getElementsByTagName("title").item(0).getTextContent());

            }
        }
    }

    public boolean inCurrentSchedule(){

        Date currentDate = new Date();

        return false;
    }
}