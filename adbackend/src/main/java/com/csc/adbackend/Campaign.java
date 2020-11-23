package com.csc.adbackend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@Entity
class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Boolean active;
    private Date created;
    private Map<Integer,Ad> ads;

    /* CONSTRUCTORS */
    protected Campaign() {}

    public Campaign(String name, Date date) {
        this.active = true;
        this.created = date; //now
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

    public Map<Integer,Ad> getAds() {
        return this.ads;
    }

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
