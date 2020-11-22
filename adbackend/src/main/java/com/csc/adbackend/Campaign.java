package com.csc.adbackend;

import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

class Campaign {
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

    public String jsonify() {
        Gson g = new Gson();
        return g.toJson(this);
    }

    /* TODO: Determine if this is needed. You shouldn't really need to reconstruct campaigns from JSON. You should only create them once, then lookup in backend... */
    public static Campaign fromJSON(String json) throws JsonSyntaxException {
        Gson g = new Gson();
        Campaign campaign = g.fromJson(json, Campaign.class);
        return campaign;
    }
}
