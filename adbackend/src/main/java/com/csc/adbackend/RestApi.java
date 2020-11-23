package com.csc.adbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Random;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
public class RestApi {

    @Autowired
    private CampaignService campaignService;
//    private final CampaignRepo repo;

//    RestApi(CampaignRepo repo) {
//        this.repo = repo;
//    }

//    @GetMapping("/hello")
//    public String helloWorld(){
//        return "Hello World!";
//    }

    @PostMapping(path = "/campaigns")
    public long addCampaign(@RequestBody Campaign campaign) {
//        repo.save(campaign);
        campaignService.addCamp(campaign);
        return campaign.getID();
    }

    @PostMapping(path = "/campaigns/{campId}")
    public long addAd(@RequestBody Ad ad, @PathVariable Integer cmpId) {
//        repo.getOne(cmpId).addAd(ad);
        campaignService.addAd(ad, cmpId);
        return ad.getID();
    }

    @GetMapping(path = "/campaigns/random")
    public Ad getRandAd() {
//        Random rand = new Random();
//        List camps = repo.findAll();
//        int randIndex = rand.nextInt(camps.size());
//        Campaign temp = (Campaign) camps.get(randIndex);
//        randIndex = rand.nextInt(temp.getAds().size());
//        return (Ad)temp.getAds().values().toArray()[randIndex];
        return campaignService.getRand();
    }

    @DeleteMapping(path = "/campaigns")
    public void deleteAll() {
//        repo.deleteAll();
        campaignService.deleteAllCamp();
    }
}
