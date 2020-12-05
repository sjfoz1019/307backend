package com.csc.adbackend;

import java.lang.Integer;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
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
     *
     * @param campaign JSON object with required fields:
     *     name - name of campaign
     *     startDate - date for the campaign take effect
     *     endDate - date for the campaign to end
     * @return url of created campaign in Location header
     */
    @PostMapping(path = "/campaigns")
    public ResponseEntity<String> addCampaign(@RequestBody Campaign campaign) {
        Error err = new Error();
        if (campaign.getName() == null || campaign.getName().equals("")) {
            err.setError("missingField");
            err.addDetail("name");
        }
        try {
            campaign.getStartDate();
        } catch (NullPointerException e) {
            err.setError("missingField");
            err.addDetail("startDate");
        }
        try {
            campaign.getEndDate();
        } catch (NullPointerException e) {
            err.setError("missingField");
            err.addDetail("endDate");
        }

        if (!err.noError()) {
            try {
                return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(err.jsonify());
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(500).build();
            }
        }

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
    public ResponseEntity<String> getCampInfo(@PathVariable Integer campID) {
        Campaign camp = campaignService.getCampaign(campID);
        if (camp != null) {
            try {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(camp.jsonify());
              
            } catch (Exception e) {
                return ResponseEntity.status(500).build();
            }
        }
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\":\"notFound\", \"details\":[]}");
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
    public ResponseEntity<String> updateCampInfo(@RequestBody Campaign campaign, @PathVariable Integer campID) {
        campaignService.updateCampaign(campID, campaign);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location",
                "/campaigns/" + campID);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("");
    }

    /**
     * DELETE
     * 
     * Deletes the specified campaign. All ads associated with the campaign are also deleted.
     */
    @DeleteMapping(path = "/campaigns/{campID}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Integer campID) {
        ResponseEntity<String> responseEntity;

        try {
            campaignService.deleteCampaign(campID);
            responseEntity = ResponseEntity.ok().build();

        } catch (EmptyResultDataAccessException e) {
            responseEntity = ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\":\"notFound\", \"details\":[]}");
        }

        return responseEntity;
    }

    /* /CAMPAIGNS/{CAMPID}/ADS */

    /**
     * GET
     * 
     * @return a list of all ads associated with campaign of specified campID.
     * Data returned for each ad is equivalent to GET /campaigns/{campID}/ads/{adID}
     */
    @GetMapping(path = "/campaigns/{campID}/ads")
    public ResponseEntity<String> getCampAds(@PathVariable Integer campID) {
        List<Ad> ads = campaignService.getCampaignAds(campID);

        if (ads == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"notFound\", \"details\":[]}");
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ObjectMapper().writeValueAsString(ads));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).build();
        }
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
        Error err = new Error();
        if (ad.getMainText() == null || ad.getMainText().equals("")) {
            err.setError("missingField");
            err.addDetail("mainText");
        }
        if (ad.getSubText() == null || ad.getSubText().equals("")) {
            err.setError("missingField");
            err.addDetail("subText");
        }
        if (ad.getURL() == null || ad.getURL().equals("")) {
            err.setError("missingField");
            err.addDetail("url");
        }
        if (ad.getImagePath() == null || ad.getImagePath().equals("")) {
            err.setError("missingField");
            err.addDetail("imagePath");
        }

        if (!err.noError()) {
            try {
                return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(err.jsonify());
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(500).build();
            }
        }
        
        Integer adID = campaignService.addAd(campID, ad);

        if (adID == -1) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"notFound\", \"details\":[]}");
        }

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
    public ResponseEntity<String> getAdInfo(@PathVariable Integer campID,  @PathVariable Integer adID) {
        Campaign camp = campaignService.getCampaign(campID);
        
        if (camp == null) {
            return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\":\"notFound\", \"details\":[]}");
        }

        Ad ad = camp.mapOfAds().get(adID);

        if (ad == null) {
            return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\":\"notFound\", \"details\":[]}");
           
        } else {
            try {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ad.jsonify());
            } catch (Exception e) {
                return ResponseEntity.status(500).build();
            }
        }
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
    public ResponseEntity<String> deleteAd(@PathVariable Integer campID,  @PathVariable Integer adID) {
        return campaignService.deleteAd(campID, adID);   
    }

    /* /RANDOM */

    /**
     * GET
     *
     * @return JSON representation of random ad from any campaign.
     */
    @GetMapping(path = "/random")
    public ResponseEntity<String> getRandAd() {
        HttpStatus status;
        String adJson = null;
        Ad ad = campaignService.getRandomAd(null);

        if (ad != null) {
            status = HttpStatus.OK;
            try {
                adJson = ad.jsonify();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return  new ResponseEntity<String>(adJson, status);
    }

    /* /RANDOM/{CAMPID} */

    /**
     * GET
     *
     * @return JSON representation of random ad from campaign with specified campID.
     */
    @GetMapping(path = "/random/{campID}")
    public ResponseEntity<String> getRandAdInCampaign(@PathVariable Integer campID) {
        Ad ad = campaignService.getRandomAd(campID);
        HttpStatus status;
        String adJson = null;

        if (ad != null) {
            status = HttpStatus.OK;
            try {
                adJson = ad.jsonify();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return  new ResponseEntity<String>(adJson, status);
    }

    /* /DB */

    /**
     * DELETE
     *
     * Resets the database to have zero ads and zero campaigns.
     */
    @DeleteMapping(path = "/db")
    public ResponseEntity<String> deleteAll() {
        try {
            campaignService.deleteAll();
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
