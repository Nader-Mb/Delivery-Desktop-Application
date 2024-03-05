package models;

import com.mysql.cj.jdbc.Blob;

public class Campaign {
    private int id,number,goal;
    private float current;
    private String title,associationName,campaignType,description;
    private Blob image;


    public Campaign(int id, int number, int goal, String title, String associationName, String campaignType, String description,Blob image,float current) {
        this.id = id;
        this.number = number;
        this.goal = goal;
        this.title = title;
        this.associationName = associationName;
        this.campaignType = campaignType;
        this.description = description;
        this.image=image;
        this.current=current;
    }

    public Campaign(int number, int goal, String title, String associationName, String campaignType, String description,float current) {
        this.number = number;
        this.goal = goal;
        this.title = title;
        this.associationName = associationName;
        this.campaignType = campaignType;
        this.description = description;
        this.current=current;

    }
    public Campaign(int id){
        this.id=id;

    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public Campaign() {
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getGoal() {
        return goal;
    }

    public String getTitle() {
        return title;
    }

    public String getAssociationName() {
        return associationName;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {

                return String.format("%-18s   %-51s   %-43s  %-37d  %-35s  %-25d   %-15s ", title, associationName, campaignType,goal,current,number,description);
            }



}
