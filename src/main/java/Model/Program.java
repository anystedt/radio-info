package Model;

import java.time.LocalDateTime;

public class Program {

    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;
    private LocalDateTime start;
    private LocalDateTime end;


    public Program(){ }

    public void setTitle(String title){ this.title = title;}

    public void setSubtitle(String subtitle){ this.subtitle = subtitle;}

    public void setDescription(String description) { this.description = description;}

    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl;}

    public void setStart(String start){
        start = start.substring(0, start.length() - 1);
        this.start = LocalDateTime.parse(start);
    }

    public void setEnd(String end){
        end = end.substring(0, end.length() - 1);
        this.end = LocalDateTime.parse(end);
    }

    public String toString(){
        return title + " " + start + " " + end + imageUrl +"\n";
    }
}
