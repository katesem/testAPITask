# 1. "Search Breweries" method

---
**Test Scenario: Missing Query Parameter**

- Send GET request to https://api.openbrewerydb.org/v1/breweries/search or https://api.openbrewerydb.org/v1/breweries/autocomplete without any query parameters <br><br>
**Expected Result:** response with error message or error status code 

---
**Test Scenario: Invalid Query Value**

- Send GET request to https://api.openbrewerydb.org/v1/breweries/search or https://api.openbrewerydb.org/v1/breweries/autocomplete 
with invalid query parameter value that does not match any of the fields values in the JSON objects. <br>
For example add "query=&&&&&&&&&&&&&&&&&&&&&&&&" as a query parameter. <br> <br>
  **Expected Result:** response with empty result(empty JSON array). Status code is 200.
---

<i>Since some fields in JSON objects can have null type values:</i> <br><br>

**Test Scenario: Search by Null Value**

Verify that by entering a query parameter with a null value:
- Send GET request to https://api.openbrewerydb.org/v1/breweries/search or https://api.openbrewerydb.org/v1/breweries/autocomplete with query parameter: "query=null". <br><br>
  **Expected Result:** response returns an Array of JSON objects with fields that contain null values. Status code is 200. <br>
*This case covered in checkSearchResults() method
---

**Test Scenario: Search with an Unknown Value**
- Send GET request to https://api.openbrewerydb.org/v1/breweries/search or https://api.openbrewerydb.org/v1/breweries/autocomplete 
with query parameter other than "query" <br>
For example: add **"name=dog"** as a query parameter<br><br>
**Expected Result:** response with empty result(empty JSON array). Status code is 200.
---

**Test Scenario: Incorrect Endpoint Format**
 - Send GET request to an invalid endpoint. For example: https://api.openbrewerydb.org/v1/search <br><br>
**Expected Result:** response contains an error message. Status code is 404.
---

**Test Scenario: Multiple Parameters(one invalid)** 
- Send GET request to https://api.openbrewerydb.org/v1/breweries/search  with valid query parameter value  and invalid value for per_page  parameter. <br>
Example: "query=dog,per_page=$$$$$$$$$" <br><br>
  **Expected Result:** response contains error message
