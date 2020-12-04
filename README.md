# Specification
Modeled after the [CHSRest Specification](http://ec2-34-221-236-150.us-west-2.compute.amazonaws.com//WebDev/Modules/02HTTPandREST/2CHSREST/CHSREST.html)

## Overview
The Java Ad Management Service (JAMS) provides an interface for marketing managers to create, deploy, and track ad campaigns across their site. 

## General Points
The following points apply across the document:
1. All resource URLs are presumed to be prefixed by a root URL specifying the hostname, e.g., http://examplesite.com
2. All resources accept and provide only JSON body content
3. Absent documentation to the contrary, all DELETE calls, POST, and PUT calls with a 400 HTTP response return as their body content, a list of JSON objects describing all errors that occured. Each error object within that list is of form {error: "error", details: [details]} where *error* is a string tag identifying the error, and *details* is an array of additional values needed to fill in details about the error, or is null if no values are needed. E.g. {error: "missingField", detatils: ["mainText"]} And, per REST standards, all successful (200 code) DELETE actions return empty body.
4. Resource documentation lists possible errors only when the error is not obvious from this General Points section. Relevant errors may appear in any order in the body. Missing field errors are checked first, and no further errors are reported if missing fields are found.
5. All resource-creating POST calls return the newly created resource as a URL path via the Location response header, not in the response body.
6. Calls to a resource return one of the following, and the earliest that is applicable:    
    a. **500 Internal Server Error** for any server error    
    b. **404 Not Found** for URLs not defined in this spec    
    c. **400 Bad Request** and a list of error strings    
    d. **200 OK** and the specified information in the body    
7. Fields of JSON content for POST and PUT calls are assumed to be strings, booleans, ints, or doubles without further documentation where obvious by their name or intent. In nonobvious cases, the docs give the type explicitly.
8. All fields listed for a POST are required by default unless the description says otherwise. Required fields may not be passed as null or "". Doing so has the same outcome as if the field were entirely missing.
9. Excess length of string fields in POST/PUT calls results in 400 error with badValue tag.
10. All dates are sent as epoch seconds.
11. Non JSON parseable bodies will be handled automatically by Spring and result in 400 error with Spring's default error message.
12. ID assignment will be handled by the backend.

## Error Codes
- *missingField* Field missing from request. Details[0] gives field name. Missing field errors may result in further errors being ignored and not reported until all required fields are present.
- *badValue* A field has a value that violates the spec in some way not covered by more specific errors. Details[0] gives the field's name, e.g. "mainText"
- *notFound* Entity not present in DB -- for cases where an Ad or Campaign is not there.

## Resources for Campaign and Ad Management

### /campaigns
#### GET
Returns a list of all campaigns. Data returned for each campaign is equivalent to **GET /campaigns/{campID}**
#### POST
Creates a new campaign. Required fields:
- *name* - name of campaign
- *startDate* - date for the campaign take effect
- *endDate* - date for the campaign to end

### /campaigns/{campID}
#### GET
Returns the campaign with the specified campID. Returned fields:
- *id*
- *name*
- *startDate*
- *endDate*
- *adIDs* - list of ad ids associated with the campaign
#### PUT
Updates specified campaign with fields received in request body. Requires all fields listed in **POST /campaigns.**
#### DELETE
Deletes the specified campaign. All ads associated with the campaign are also deleted. 

### /campaigns/{campID}/ads
#### GET
Returns a list of all ads associated with campaign of specified campID. Data returned for each ad is equivalent to **GET /campaigns/{campID}/ads/{adID}**
#### POST
Creates a new ad under the given campaign. Required fields:
- *mainText* - Title or main text of the ad
- *subText* - Secondary text of the ad
- *url* - where the ad takes the user when clicked
- *imagePath* - a path to the image to be displayed with the ad

### /campaigns/{campID}/ads/{adID}
#### GET
Returns the ad with the specified adID under campaign campID. Returned fields:
- *id*
- *mainText*
- *subText*
- *url*
- *imagePath*
#### PUT
Updates ad with fields received in request body. Requires all fields listed in **POST /campaign/{campID}/ads**
#### DELETE
Deletes the specified ad.

### /random
#### GET
Returns a random ad from any campaign.
### /random/{campID}
#### GET
Returns a random ad from campaign with campID.

## Resources for Testing and Development
### /db
#### DELETE
Resets the database to have zero ads and zero campaigns. Resets ID assignment: All new ads and campaigns will have autoincremented IDs starting at 0.
