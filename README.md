# Specification
Modeled after the [CHSRest Specification](http://ec2-34-221-236-150.us-west-2.compute.amazonaws.com//WebDev/Modules/02HTTPandREST/2CHSREST/CHSREST.html)

## Overview
The Java Ad Management Service (JAMS) provides an interface for marketing managers to create, deploy, and track ad campaigns across their site. 

## General Points
The following points apply across the document:
1. All resource URLs are presumed to be prefixed by a root URL specifying the hostname, e.g., http://examplesite.com
2. All resources accept and provide only JSON body content
3. Absent documentation to the contrary, all DELETE calls, POST, and PUT calls with a 400 HTTP response return as their body content, a list of JSON objects describing all errors that occured. Each error object within that list is of form {tag: {errorTag}, params: {params}} where errorTag is a string tag identifying the error, and params is an array of additional values needed to fill in details about the error, or is null if no values are needed. E.g. {tag: "missingField", params: ["lastName"]} And, per REST standards, all successful (200 code) DELETE actions return empty body.
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
10. **TODO: How are we storing dates?**
11. Non JSON parseable bodies result in 500 error.

## Error Codes
- *missingField* Field missing from request. Params[0] gives field name. Missing field errors may result in further errors being ignored and not reported until all required fields are present.
- *badValue* A field has a value that violates the spec in some way not covered by more specific errors. Params[0] gives the field's name, e.g. "mainText"
- *notFound* Entity not present in DB -- for cases where an Ad or Campaign is not there.

## Resources for Campaign and Ad Management

### /capmpaigns
#### GET
    Returns a list of all campaigns. Data returned for each campaign is equivalent to GET /campaigns/{id}
#### POST
    Creates a new campaign. Required fields:
    - *name* name of campaign
    - *active* true if campaign should be active, false if not
    - *id* and *dateCreated* will be automatically assigned in the backend

### /campaigns/{id}
#### GET
    Returns the campaign with the specified id. Returned fields:
    - *id*
    - *name*
    - *active*
    - *dateCreated*
    - ~~*ads* List of ads associated with this campaign~~
#### PUT
    Updates campaign with fields received in request body. Required fields same as POST /campaigns.
#### DELETE
    Deletes the campaign with the specified id. 
    *TODO: What happens to the ads?*

### TODO: I need a better understanding of how campaigns and ads relate. Are ads always tied to a campaign? In that case, it makes sense to always access them through the campaign. Can one ad be associated with multiple campaigns? This scenario seems likely, but upon more thought seems difficult to implement. What follows is my best guess for now...
### /campaigns/{id}/ads
#### GET
    Returns a list of all ads associated with the specified campaign. Data returned for each ad is equivalent to GET /campaigns/{c_id}/ads/{a_id}
#### POST
    Creates a new ad under the given campaign. Required fields:
    - *mainText*
    - *subText*
    - *imagePath*
    - *url*
    - *id* and *campaign* will be automatically assigned in the backend (TODO: is *campaign* really necessary in this setup?)

### /campaigns/{c_id}/ads/{a_id}
#### GET
    Returns the ad with the specified a_id under campaign c_id. Returned fields:
    - *id*
    - *mainText*
    - *subText*
    - *imagePath*
    - *url*
    - *campaign*
#### PUT
    Updates ad with fields received in request body. Requited fields same as POST /campaign/{id}/ads
#### DELETE
    Deletes the specified ad

## Resources for Analysis and Statistics
**Not Implemented**
