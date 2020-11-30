package com.csc.adbackend;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class CampaignServiceImpl implements CampaignService {
    HashMap<Integer, Campaign> campaigns;
    Integer nextCmpId;
    Integer nextAdId;


    public CampaignServiceImpl() {
        campaigns = new HashMap<>();
        nextCmpId = 0;
        nextAdId = 0;
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return new ArrayList<>(campaigns.values());
    }

    @Override
    public Campaign getCampaign(Integer cmpId) {
        return campaigns.get(cmpId);
    }

    @Override
    public Integer addCampaign(Campaign campaign) {
        campaign.setID(nextCmpId);
        campaign.setAds();
        campaigns.put(nextCmpId,campaign);
        nextCmpId++;
        return nextCmpId-1;
    }

    @Override
    public Integer addAd(Integer cmpId, Ad ad) {
        ad.setID(nextAdId);
        campaigns.get(cmpId).addAd(ad);
        nextAdId++;
        return nextAdId-1;
    }

    @Override
    public void deleteAll() {
        campaigns.clear();
    }

    @Override
    public List<Ad> getCampaignAds(Integer cmpId) {
        return new ArrayList<>(campaigns.get(cmpId).getAds().values());
    }

    @Override
    public Ad getRandomAd() {
        Random random = new Random();
        List<Campaign> camps = new ArrayList<>(campaigns.values());
        Campaign temp = camps.get(random.nextInt(camps.size()));
        List<Ad> ads = new ArrayList<>(temp.getAds().values());
        return ads.get(random.nextInt(ads.size()));
    }



}
