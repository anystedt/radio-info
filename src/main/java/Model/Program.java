package Model;

import java.time.LocalDateTime;

public class Program {

    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean hasBeenSent;


    public Program(){ }

    public void setTitle(String title){ this.title = title;}

    public String getTitle(){return title;}

    public void setSubtitle(String subtitle){ this.subtitle = subtitle;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl;}

    public void setHasBeenSent(Boolean hasBeenSent){
        this.hasBeenSent = hasBeenSent;
    }

    public Boolean getHasBeenSent(){ return hasBeenSent;}

    public void setStart(String start){
        start = start.substring(0, start.length() - 1);
        this.start = LocalDateTime.parse(start);
    }

    public LocalDateTime getStart(){return start;}

    public void setEnd(String end){
        end = end.substring(0, end.length() - 1);
        this.end = LocalDateTime.parse(end);
    }

    public LocalDateTime getEnd(){return end;}

    public String toString(){
        return title + " " + start + " " + end + imageUrl +"\n";
    }
}
