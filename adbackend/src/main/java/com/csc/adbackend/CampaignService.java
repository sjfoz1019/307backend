package com.csc.adbackend;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CampaignService {
    public List<Campaign> getAllCampaigns();
    public Campaign getCampaign(Integer campId);
    public Integer addCampaign(Campaign campaign);
    public Integer addAd(Integer campId, Ad ad);
    public void deleteCampaign(Integer ID);
    public void deleteAll();
    public List<Ad> getCampaignAds(Integer cmpId);
    public Ad getRandomAd(Integer campId);
    public boolean updateCampaign(Integer campId, Campaign campaign);
    public ResponseEntity<String> updateAd(Integer campId, Integer adId, Ad ad);
    public ResponseEntity<String> deleteAd(Integer campId, Integer adId);
}
