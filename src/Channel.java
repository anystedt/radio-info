public class Channel {

    private Integer id;
    private String name;
    private String imageUrl;
    private String scheduleUrl;

    public Channel(){}

    public void setId(Integer id){ this.id = id;}

    public Integer getId(){ return id;}

    public void setName(String name){this.name = name;}

    public void setImage(String imageUrl){this.imageUrl = imageUrl;}

    public String toString(){
        return id+" "+name+" "+imageUrl;
    }
}
