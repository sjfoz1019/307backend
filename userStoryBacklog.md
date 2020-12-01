### Frontend
- As a user I want to view a basic front page containing different campaigns.
     - Campaigns should be arranged in rows
     - Name, start date and end date should be displayed
     
- As a developer, I want to add a new campaign to the table so that I can store ad campaigns on the server for later use.
     - Make an "Add campaign" button and input fields for making a new campaign.
     - When submitting a new campaign, make a POST request to /campaigns with the required fields.

- As a developer, I want to send a json string of a newly added campaign object to the proper endpoint (see spec).
     - 2 ways:
          1. Write a campaign class, make a campaign object with fields that are entered, and then call built in method that 
          converts to JSON
          2. Write a method that takes in fields entered and structures it as a JSON string representation of object
     - Newly assigned ID should be return and stored

- As a user, I want to be able to select any campaign I've added and be able to see the individual ads within that campaign
     - Fragment of selected campaign should pop up, taking up the whole screen
     - Different ads within campaign should be displayed in rows
     - Display main text, sub text
     
- As a developer, I want to send a json string of a newly added ad object to the proper endpoint.
     - Newly assigned ID should be returned and stored.
     
- As a user, I want to delete all campaigns.
     - All campaigns should be removed plus their associated ads

- As a developer, I want to send a DELETE request to the backend to delete all Campaigns in the database

- As a user, I want to delete all ads in a specific Campaign
                    
### Backend
- As a developer, I want a database that can store all added Campaigns.
     - First, try to fix JPA repository because it would be easiest
          - https://spring.io/guides/tutorials/rest/
          - https://spring.io/guides/gs/accessing-data-jpa/
     - If that doesn't work then we can do a MySQL database
     - Once this is done, REST mapping methods can be adjusted.
     - Database must be able to store Campaigns using a key

- As an a developer I want to add a campaign to the database following a POST call
     - Requested body will be a Campaign object
     - ID, startDate, endDate must be assigned before storing the campaign to the database
     - The ID is returned to the frontend

- As an developer I want to add an ad to a specific campaign following a POST call
     - Requested body is an Ad object and parameter is a Campaign ID
     - ID must be assigned
     - The ID is returned to the frontend
     
- As a developer I want to delete all the Campaigns in the database folloing a DELETE call
     - All ads associated with each campaign are deleted
     - Left with an empty database
     
- As a dveloper I want to delete all the Ads for a given Campaign following a DELETE call
     - Left with an empty HashMap for ads field in that Campaign.

- As a developer, I want to use Postman to test all REST mappings. Tests will: 
     - Tests should be automated
     - Check spec to see what values should be returned
          - POST calls should return an ID
          - Random GET call should return an Ad object
