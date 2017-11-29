/**
 * Created on 17/11/17
 * File: Program.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing a program. Contains information about the
 * program such as title, description, start time and end time.
 */

package model;

import java.time.LocalDateTime;

public class Program {

    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;
    private LocalDateTime start;
    private LocalDateTime end;

    public Program(){ }

    /**
     * Sets the title.
     * @param title the title of the program.
     */
    public void setTitle(String title){ this.title = title; }

    /**
     * Returns the title.
     * @return the title of the program.
     */
    public String getTitle(){ return title; }

    /**
     * Sets the subtitle.
     * @param subtitle the subtitle of the program.
     */
    public void setSubtitle(String subtitle){ this.subtitle = subtitle; }

    /**
     * Returns the subtitle.
     * @return the subtitle of the program.
     */
    public String getSubtitle(){ return subtitle; }

    /**
     * Sets the description
     * @param description the description of the program.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Returns the description.
     * @return the description of the program.
     */
    public String getDescription(){ return description; }

    /**
     * Sets the image url.
     * @param imageUrl the image url of the program.
     */
    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }

    /**
     *  Returns the image url.
     * @return the image url of the program.
     */
    public String getImageUrl(){ return imageUrl; }

    /**
     * Sets the start time.
     * @param start the start time of the program.
     */
    public void setStart(String start){
        start = start.substring(0, start.length() - 1);
        this.start = LocalDateTime.parse(start);
    }

    /**
     * Returns the start time.
     * @return the start time of the program.
     */
    public LocalDateTime getStart(){ return start; }

    /**
     * Sets the end time.
     * @param end the end time of the program.
     */
    public void setEnd(String end){
        end = end.substring(0, end.length() - 1);
        this.end = LocalDateTime.parse(end);
    }

    /**
     * Returns the end time.
     * @return the end time of the program.
     */
    public LocalDateTime getEnd(){ return end; }
}
