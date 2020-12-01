package com.csc.adbackend;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Random;

@Entity
public class Ad {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String mainText;
    private String subText;
    private String url;
    private String imagePath;


    /* CONSTRUCTORS */

    public Ad(Integer id, String mainText, String subText, String imagePath, String url) {
        this.id = id;
        this.mainText = mainText;
        this.subText = subText;
        this.imagePath = imagePath;
        this.url = url;
    }

    public Ad() {
        this.id = -1;
        this.mainText = "";
        this.subText = "";
        this.imagePath = "";
        this.url = "";
    }

    /* GETTER & SETTERS */

    public Integer getID() {
        return this.id;
    }

    public void setID() {
        Random rand = new Random();
        this.id = rand.nextInt(10000);
    }

    public void setID(Integer id) {
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
