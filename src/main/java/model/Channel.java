/**
 * Created on 17/11/17
 * File: Channel.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing a channel. Contains information about the
 * channel such as id, name, image and a list containing the program
 * tableau.
 */

package model;

import java.util.List;

public class Channel {

    private Integer id;
    private String name;
    private String imageUrl;
    private List<Program> tableau;

    public Channel(){}

    /**
     * Sets the id.
     * @param id the id of the channel.
     */
    public void setId(Integer id){ this.id = id; }

    /**
     * Returns the id.
     * @return the id of the channel
     */
    public Integer getId(){ return id; }

    /**
     * Sets the name.
     * @param name the name of the channel
     */
    public void setName(String name){ this.name = name; }

    /**
     * Returns the name.
     * @return the name of the channel.
     */
    public String getName(){ return name; }

    /**
     * Sets the image url.
     * @param image the image url of the channel.
     */
    public void setImage(String image){ this.imageUrl = image; }

    /**
     * Returns the image url
     * @return the image url of the channel
     */
    public String getImage(){ return imageUrl; }

    /**
     * Sets the tableau.
     * @param tableau the tableau of the channel
     */
    public void setTableau(List<Program> tableau){ this.tableau = tableau; }

    /**
     * Returns the tableau.
     * @return the tableau of the channel.
     */
    public List getTableau(){ return tableau; }
}
