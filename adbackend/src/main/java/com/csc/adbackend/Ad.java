package com.csc.adbackend;

public class Ad {
    private int id;
    private String mainText;
    private String subText;
    private String imagePath;
    private String url;
    private Campaign campaign;

    /* CONSTRUCTORS */

    public Ad(int id, String mainText, String subText, String imagePath, String url, Campaign campaign) {
        this.id = id;
        this.mainText = mainText;
        this.subText = subText;
        this.imagePath = imagePath;
        this.url = url;
        this.campaign = campaign;
    }

    public Ad() {
        this.id = -1;
        this.mainText = "";
        this.subText = "";
        this.imagePath = "";
        this.url = "";
        this.campaign = null;
    }

    /* GETTER & SETTERS */

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getMainText() {
        return this.mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return this.subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public Campaign getCampaign() {
        return this.campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
