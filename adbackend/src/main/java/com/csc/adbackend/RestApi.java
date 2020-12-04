package com.csc.adbackend;

import java.lang.Integer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApi {

    @Autowired
    private CampaignService campaignService;
  
    /* /CAMPAIGNS */
  
    /**
     * GET
     *
     * @return a list of all campaigns.
     * Data returned for each campaign is equivalent to GET /campaigns/{campID}
     */
    @GetMapping(path = "/campaigns")
    public List<Campaign> getCampaignList() {
        return campaignService.getAllCampaigns();
    }

    /**
     * POST
     *
     * Creates a new campaign.
    
     * @param campaign JSON object with required fields:
     *     name - name of campaign
     *     startDate - date for the campaign take effect
     *     endDate - date for the campaign to end
     * @return url of created campaign in Location header
     */
    @PostMapping(path = "/campaigns")
    public ResponseEntity<String> addCampaign(@RequestBody Campaign campaign) {
        Integer campID = campaignService.addCampaign(campaign);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", 
            "/campaigns/" + campID);

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .body("");
    }

    /* /CAMPAIGNS/{CAMPID} */

    /**
     * GET
     *
     * @return JSON representation of campaign with the specified campID
     * Returned fields:
     *     id
     *     name
     *     startDate
     *     endDate
     *     adIDs - list of ad ids associated with the campaign
     */
    @GetMapping(path = "/campaigns/{campID}")
    public Campaign getCampInfo(@PathVariable Integer campID) {
        Campaign camp = campaignService.getCampaign(campID);
        if (camp != null) {
            return camp;
        }
        return null;
    }

    /**
     * PUT
     *
     * Updates specified campaign with fields received in request body.
     * @param campID JSON object with required fields:
     *     name - name of campaign
     *     startDate - date for the campaign take effect
     *     endDate - date for the campaign to end
     * @return url of created campaign in Location header
     */
    @PutMapping(path = "/campaigns/{campID}")
    public void updateCampInfo(@PathVariable Integer campID) {
        // TODO
    }

    /**
     * DELETE
     * 
     * Deletes the specified campaign. All ads associated with the campaign are also deleted.
     */
    @DeleteMapping(path = "/campaigns/{campID}")
    public void deleteCampaign(@PathVariable Integer campID) {
        // TODO
    }

    /* /CAMPAIGNS/{CAMPID}/ADS */

    /**
     * GET
     * 
     * @return a list of all ads associated with campaign of specified campID.
     * Data returned for each ad is equivalent to GET /campaigns/{campID}/ads/{adID}
     */
    @GetMapping(path = "/campaigns/{campID}/ads")
    public List<Ad> getCampAds(@PathVariable Integer campID) {
        // TODO
        // return campaignService.getCampaignAds(campID);
        return null;
    }

    /**
     * POST
     *
     * Creates a new ad under the given campaign.
     * @param ad JSON object with required fields:
     *     mainText - Title or main text of the ad
     *     subText - Secondary text of the ad
     *     url - where the ad takes the user when clicked
     *     imagePath - a path to the image to be displayed with the ad
     * @return url of created ad in Location header
     */
    @PostMapping(path = "/campaigns/{campID}/ads")
    public ResponseEntity<String> addAd(@RequestBody Ad ad, @PathVariable Integer campID) {
        // Integer adID = campaignService.addAd(campID, ad);
        Integer adID = -1;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", 
            "/campaigns/" + campID + "/ads/" + adID);

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .body("");
    }

    /* /CAMPAIGNS/{CAMPID}/ADS/{ADID} */

    /**
     * GET
     *
     * Returns the ad with the specified adID under campaign campID.
     * @return JSON representation of ad with fields:
     *     id
     *     mainText
     *     subText
     *     url
     *     imagePath
     */
    @GetMapping(path = "/campaigns/{campID}/ads/{adID}")
    public Ad getAdInfo(@PathVariable Integer campID,  @PathVariable Integer adID) {
        return null;
        //TODO
    }

    /**
     * PUT
     *
     * Updates ad under the given campaign.
     * @param ad JSON object with required fields:
     *     mainText - Title or main text of the ad
     *     subText - Secondary text of the ad
     *     url - where the ad takes the user when clicked
     *     imagePath - a path to the image to be displayed with the ad
     * @return url of updated ad in Location header
     */
    @PutMapping(path = "/campaigns/{campID}/ads/{adID}")
    public void updateAdInfo(@RequestBody Ad ad, @PathVariable Integer campID,  @PathVariable Integer adID) {
        // TODO
    }

    /**
     * DELETE
     *
     * Deletes the specified ad.
     */
    @DeleteMapping(path = "/campaigns/{campID}/ads/{adID}")
    public void deleteAd(@PathVariable Integer campID,  @PathVariable Integer adID) {
        // TODO
    }

    /* /RANDOM */

    /**
     * GET
     *
     * @return JSON representation of random ad from any campaign.
     */
    @GetMapping(path = "/random")
    public Ad getRandAd() {
        return campaignService.getRandomAd();
    }

    /* /RANDOM/{CAMPID} */

    /**
     * GET
     *
     * @return JSON representation of random ad from campaign with specified campID.
     */
    @GetMapping(path = "/random/{campID}")
    public Ad getRandAdInCampaign(@PathVariable Integer campID) {
        // TODO
        return null;
    }

    /* /DB */

    /**
     * DELETE
     *
     * Resets the database to have zero ads and zero campaigns.
     */
    @DeleteMapping(path = "/db")
    public void deleteAll() {
        campaignService.deleteAll();
    }
}
