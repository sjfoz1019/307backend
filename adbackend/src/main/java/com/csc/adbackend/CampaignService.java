package com.csc.adbackend;

import java.util.List;

public interface CampaignService {
    public List<Campaign> getAllCampaigns();
    public Campaign getCampaign(Integer cmpId);
    public Integer addCampaign(Campaign campaign);
    public Integer addAd(Integer cmpId, Ad ad);
    public void deleteCampaign(Integer ID);
    public void deleteAll();
    public List<Ad> getCampaignAds(Integer cmpId);
    public Ad getRandomAd();
    public void updateCampaign(Integer cpmId, Campaign campaign);
}
