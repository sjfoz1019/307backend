package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    CampaignRepo campaignRepo;

    Integer nextCmpId;
    Integer nextAdId;


    public CampaignServiceImpl(CampaignRepo campaignRepo) {
        this.campaignRepo = campaignRepo;
        nextCmpId = 0;
        nextAdId = 0;
    }

    public void deleteCampaign(Integer ID) throws IllegalArgumentException{
        campaignRepo.deleteById(ID);
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
        Optional<Campaign> camp = campaignRepo.findById(cmpId);
        if (camp.isPresent()) {
            return camp.get();
        }
        return null;
    }

    @Override
    public Integer addCampaign(Campaign campaign) {
        campaign.setID(nextCmpId);
        campaignRepo.save(campaign);
        nextCmpId++;
        return nextCmpId-1;
    }

    @Override
    public Integer addAd(Integer cmpId, Ad ad) {
        ad.setID(nextAdId);
        Optional<Campaign> camp = campaignRepo.findById(cmpId);
        if (camp.isPresent()) {
            camp.get().addAd(ad);
            campaignRepo.save(camp.get());
            nextAdId++;
            return nextAdId-1;
        } else {
            return -1;
        }
    }

    @Override
    public void deleteAll() {
        nextAdId = 0;
        nextCmpId = 0;
        campaignRepo.deleteAll();
        
    }

    @Override
    public List<Ad> getCampaignAds(Integer cmpId) {
        Optional<Campaign> camp = campaignRepo.findById(cmpId);
        if (camp.isPresent()) {
            return camp.get().listOfAds();
        } else {
            return null;
        }
    }

    @Override
    public Ad getRandomAd() {
        Random random = new Random();
        List<Campaign> camps = getAllCampaigns();
        Campaign temp = camps.get(random.nextInt(camps.size()));
        List<Ad> ads = temp.listOfAds();
        return ads.get(random.nextInt(ads.size()));
    }
    
    @Override
    public void updateCampaign(Integer campId, Campaign campaign) {
        //campaign.setID(campId);
        campaignRepo.save(campaign);
    }

}
