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

    @PostMapping(path = "/campaigns")
    public Campaign addCampaign(@RequestBody Campaign campaign) {
        campaign.setData();
        return campaign;
    }

    @PostMapping(path = "/campaigns/{campId}")
    public Ad addAd(@RequestBody Ad ad, @PathVariable Integer cmpId) {
        ad.setID();
        return ad;
    }

    @GetMapping(path = "/campaigns/random")
    public Ad getRandAd() {
        return new Ad();
    }

    @DeleteMapping(path = "/campaigns")
    public void deleteAll() {
    }
}
