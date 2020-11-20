package com.csc.adbackend;

import java.util.Date;
import java.util.List;

public class Campaign {
    int id;
    String name;
    Boolean active;
    Date created;
    List<Ad> ads;

    /* CONSTRUCTORS */

    public Campaign() {
        this.id = -1;
        this.active = false;
        this.created = new Date(); //now
        this.name = "";
        this.ads = null;
    }

    /* GETTERS & SETTERS */

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ad> getAds() {
        return this.ads;
    }

    /* METHODS */

    public void addAd(Ad ad) {
        this.ads.add(ad);
    }
}
