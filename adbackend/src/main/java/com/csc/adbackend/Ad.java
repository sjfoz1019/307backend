package com.csc.adbackend;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Objects;

public class Ad {
    private Integer id;
    private String mainText;
    private String subText;
    private String imagePath;
    private String url;
    private Campaign campaign;

    /* CONSTRUCTORS */

    public Ad(Integer id, String mainText, String subText, String imagePath, String url, Campaign campaign) {
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

    public Integer getID() {
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

    /* METHODS */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Ad))
            return false;
        Ad ad = (Ad) o;
        return Objects.equals(this.id, ad.id) && Objects.equals(this.mainText, ad.mainText)
                && Objects.equals(this.subText, ad.subText) && Objects.equals(this.imagePath, ad.imagePath) && Objects.equals(this.url, ad.url);
    }

    public String jsonify() {
        Gson g = new Gson();
        return g.toJson(this);
    }

    public static Ad fromJSON(String json) throws JsonSyntaxException {
        Gson g = new Gson();
        Ad ad = g.fromJson(json, Ad.class);
        return ad;
    }
}
