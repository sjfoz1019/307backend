package com.csc.adbackend;

import javax.persistence.*;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public Campaign(String name, long start, long end) {
        this.startDate = new Date(start*1000);
        this.endDate = new Date(end*1000);
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

    public long getStartDate() {
        return this.startDate.getTime()/1000;
    }

    public void setStartDate(long start) {
        this.startDate = new Date(start*1000);
    }

    public long getEndDate() {
        return this.endDate.getTime()/1000;
    }

    public void setEndDate(long end) {
        this.endDate = new Date(end*1000);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAdIDs() {
        return new ArrayList<>(this.ads.keySet());
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

    public List<Ad> listOfAds() { 
        return new ArrayList<>(this.ads.values()); 
    }

    public Map<Integer, Ad> mapOfAds() {
        return ads;
    }

    public String jsonify() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
