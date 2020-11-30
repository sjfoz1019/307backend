package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import java.util.Random;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
public class RestApi {

//    @GetMapping("/hello")
//    public String helloWorld(){
//        return "Hello World!";
//    }

    @Autowired
    private CampaignService campaignService;

    @PostMapping(path = "/campaigns")
    public Integer addCampaign(@RequestBody Campaign campaign) {
        return campaignService.addCampaign(campaign);
    }

    @PostMapping(path = "/campaigns/{campId}")
    public Integer addAd(@RequestBody Ad ad, @PathVariable Integer cmpId) {
        return campaignService.addAd(cmpId, ad);
    }

    @GetMapping(path = "/campaigns/random")
    public Ad getRandAd() {
        return campaignService.getRandomAd();
    }

    @DeleteMapping(path = "/campaigns")
    public void deleteAll() {
        campaignService.deleteAll();
    }

    @GetMapping(path = "/campaigns")
    public List<Campaign> getCampaignList() {
        return campaignService.getAllCampaigns();
    }
}
