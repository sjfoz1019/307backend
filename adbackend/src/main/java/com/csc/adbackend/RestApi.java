package com.csc.adbackend;

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
    public String listCampaigns() {
        // TODO
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
    public void addCampaign(@RequestBody Campaign campaign) {
        return campaignService.addCampaign(campaign);
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
    public String getCampInfo(@PathVariable Integer campId) {
        // TODO
    }

    /**
     * PUT
     * 
     * Updates specified campaign with fields received in request body.
     * @param campaign JSON object with required fields:
     *     name - name of campaign
     *     startDate - date for the campaign take effect
     *     endDate - date for the campaign to end
     * @return url of created campaign in Location header
     */
    @PutMapping(path = "/campaigns/{campID}")
    public void updateCampInfo(@PathVariable Integer campId) {
        // TODO
    }

    /**
     * DELETE
     * 
     * Deletes the specified campaign. All ads associated with the campaign are also deleted.
     */
    @DeleteMapping(path = "/campaigns/{campID}")
    public void deleteCampaign(@PathVariable Integer campId) {
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
    public String getCampAds(@PathVariable Integer campId) {
        // TODO
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
    @PostMapping(path = "/campaigns/{campId}/ads")
    public void addAd(@RequestBody Ad ad, @PathVariable Integer campId) {
        return campaignService.addAd(campId, ad);
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
    public String getAdInfo(@PathVariable Integer campId,  @PathVariable Integer adID) {
        // TODO
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
    public void updateAdInfo(@RequestBody Ad ad, @PathVariable Integer campId,  @PathVariable Integer adID) {
        // TODO
    }

    /**
     * DELETE
     * 
     * Deletes the specified ad.
     */
    @DeleteMapping(path = "/campaigns/{campID}/ads/{adID}")
    public void deleteAd(@PathVariable Integer campId,  @PathVariable Integer adID) {
        // TODO
    }

    /* /RANDOM */

    /**
     * GET
     * 
     * @return JSON representation of random ad from any campaign.
     */
    @GetMapping(path = "/random")
    public String getRandAd() {
        return campaignService.getRandomAd();
    }

    /* /RANDOM/{CAMPID} */

    /**
     * GET
     * 
     * @return JSON representation of random ad from campaign with specified campID.
     */
    @GetMapping(path = "/random/{campID}")
    public String getRandAdInCampaign(@PathVariable Integer campId) {
        // TODO
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
