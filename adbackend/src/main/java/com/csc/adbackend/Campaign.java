package com.csc.adbackend;

import javax.persistence.*;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@Entity
class Campaign {

    @Id
    private Integer id;
    private String name;
    private Date startDate;
    private Date endDate;

    @ElementCollection
    private Map<Integer,Ad> ads;

    /* CONSTRUCTORS */
    protected Campaign() {}

    public Campaign(String name, Date start, Date end) {
        this.startDate = start;
        this.endDate = end;
        this.name = name;
        this.ads = new HashMap<>();
    }

    /* GETTERS & SETTERS */

    public Integer getID() {
        return this.id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer,Ad> getAds() { return this.ads; }

    public void setAds() {
        this.ads = new HashMap<>();
    }

//    public void setData() {
//        Random rand = new Random();
//        this.id = rand.nextInt(10000);
//        this.active = true;
//        this.ads = new HashMap<>();
//    }

    /* METHODS */

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Campaign))
            return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(this.id, campaign.id) && Objects.equals(this.name, campaign.name);
    }

    public void addAd(Ad ad) {
        this.ads.put(ad.getID(), ad);
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
