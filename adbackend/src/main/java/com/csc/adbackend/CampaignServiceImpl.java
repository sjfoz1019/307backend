package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    CampaignRepo campaignRepo;

//    HashMap<Integer, Campaign> campaigns;
    Integer nextCmpId;
    Integer nextAdId;


    public CampaignServiceImpl(CampaignRepo campaignRepo) {
        this.campaignRepo = campaignRepo;
//        campaigns = new HashMap<>();
        nextCmpId = 0;
        nextAdId = 0;
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        Iterable<Campaign> iterable = campaignRepo.findAll();
        List<Campaign> camps = new ArrayList<>();
        iterable.forEach(camps :: add);
        return camps;
    }

    @Override
    public Campaign getCampaign(Integer cmpId) {
        return campaignRepo.findById((long)cmpId);
    }

    @Override
    public Integer addCampaign(Campaign campaign) {
        campaign.setID(nextCmpId);
        campaign.setAds();
        campaignRepo.save(campaign);
        nextCmpId++;
        return nextCmpId-1;
    }

    @Override
    public Integer addAd(Integer cmpId, Ad ad) {
        ad.setID(nextAdId);
        campaignRepo.findById((long)cmpId).addAd(ad);
        nextAdId++;
        return nextAdId-1;
    }

    @Override
    public void deleteAll() {
        nextAdId = 0;
        nextCmpId = 0;
        campaignRepo.deleteAll();
    }

    @Override
    public List<Ad> getCampaignAds(Integer cmpId) {
        return new ArrayList<>(campaignRepo.findById((long)cmpId).getAds().values());
    }

    @Override
    public Ad getRandomAd() {
        Random random = new Random();
        List<Campaign> camps = getAllCampaigns();
        Campaign temp = camps.get(random.nextInt(camps.size()));
        List<Ad> ads = new ArrayList<>(temp.getAds().values());
        return ads.get(random.nextInt(ads.size()));
    }

}
