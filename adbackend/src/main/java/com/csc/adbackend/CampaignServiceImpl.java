package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    CampaignRepo campaignRepo;
    Integer nextCmpId;
    Integer nextAdId;



    public CampaignServiceImpl(CampaignRepo campaignRepo) {
        this.campaignRepo = campaignRepo;
        this.nextCmpId = 0;
        this.nextAdId = 0;
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        Iterable<Campaign> iterable = campaignRepo.findAll();
        List<Campaign> camps = new ArrayList<>();
        iterable.forEach(camps :: add);
        return camps;
    }

    @Override
    public Campaign getCampaign(Integer campId) {
        Optional<Campaign> camp = campaignRepo.findById(campId);
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
    public Integer addAd(Integer campId, Ad ad) {
        ad.setID(nextAdId);
        Optional<Campaign> camp = campaignRepo.findById(campId);
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
    public List<Ad> getCampaignAds(Integer campId) {
        Optional<Campaign> camp = campaignRepo.findById(campId);

        if (camp.isPresent()) {
            return camp.get().listOfAds();
        } else {
            return null;
        }
    }

    @Override
    public Ad getRandomAd(Integer campId) {
        Random random = new Random();
        Campaign temp;

        if(campId != null) {
            Optional<Campaign> camp = campaignRepo.findById(campId);
            if (camp.isPresent()) {
                temp = camp.get();
            } else {
                return null;
            }
        } else {
            List<Campaign> camps = getAllCampaigns();
            temp = camps.get(random.nextInt(camps.size()));
        }
        List<Ad> ads = temp.listOfAds(); 
        return ads.get(random.nextInt(ads.size()));
    }
    
    @Override
    public void updateCampaign(Integer campId, Campaign campaign) {
        //campaign.setID(campId);
        Optional<Campaign> camp = campaignRepo.findById(campId);
        if (camp.isPresent()) {
            campaign.setID(campId);
            campaignRepo.save(campaign);
        }
    }

    @Override
    public void deleteCampaign(Integer campId) {
        campaignRepo.deleteById(campId);
    }

    @Override
    public ResponseEntity<String> deleteAd(Integer campId, Integer adId) {
        ResponseEntity<String> responseEntity;

        Optional<Campaign> camp = campaignRepo.findById(campId);
        if (camp.isPresent()) {
            try {
                camp.get().mapOfAds().remove(adId);
                campaignRepo.save(camp.get());
                responseEntity = ResponseEntity.ok().build();

            } catch (IllegalArgumentException e) {
                responseEntity = ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"notFound\", \"details\":[]}");
            }
        } else {
            responseEntity = ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\":\"notFound\", \"details\":[]}");
        }

        return responseEntity;
    }


}
