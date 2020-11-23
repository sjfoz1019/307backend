package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepo campaignRepo;

    public List<Campaign> makeList() {

        var it = campaignRepo.findAll();

        var users = new ArrayList<Campaign>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Long count() {

        return campaignRepo.count();
    }

    public void deleteById(Integer Id) {
        campaignRepo.deleteById(Id);
    }

    public Integer addCamp(Campaign campaign) {
        campaignRepo.save(campaign);
        return campaign.getID();
    }

    public Integer addAd(Ad ad, Integer cmpId) {
        Campaign campaign = campaignRepo.findById(cmpId).get();
        campaign.addAd(ad);
        return ad.getID();
    }

    public Ad getRand() {
        Random rand = new Random();
        List camps = makeList();
        int randIndex = rand.nextInt(camps.size());
        Campaign temp = (Campaign) camps.get(randIndex);
        randIndex = rand.nextInt(temp.getAds().size());
        return (Ad)temp.getAds().values().toArray()[randIndex];
    }

    public void deleteAllCamp() {
        campaignRepo.deleteAll();
    }
}
