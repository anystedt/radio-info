package Model;

import java.util.List;

public class Channel {

    private Integer id;
    private String name;
    private String imageUrl;
    private List<Program> tableau;

    public Channel(){}

    public void setId(Integer id){ this.id = id;}

    public Integer getId(){ return id;}

    public void setName(String name){this.name = name;}

    public String getName(){return name;}

    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}

    public String getImageUrl(){ return imageUrl;}

    public void setTableau(List<Program> tableau){ this.tableau = tableau;}

    public String toString(){
        return id + " " + name + "\n";
    }
}
