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
---


It would also be good to check, but since I don't have any requirements for this I consider it redundant for this task:
- to check if the returned values in the json object meet the requirements, for example: that the phone number has a specific length and consists of only digits

---



# 2. "List Breweries" method

**Scope of Automation:** Since these are API tests, it will be appropriate to cover all or most of them with autotests:
this will be especially effective for large amounts of data and repetitive scenarios.


**Test scenarios:**
Future automation tests will be based on test scenarios:

- positive scenarios: providing the valid data sets as a query param value
- negative scenarios:  providing invalid or improper data sets as a query param value


**What should be checked in test scenarios:**

- return of the correct status codes in case of valid query param value input
- return of the error message or corresponding status codes (400, 404 etc) in case of invalid query param value input/without any query param 
- return of the correct HTTP headers (Content-Type: application/json etc.)
- return of the valid response payload (that all required fields in JSON object are returned; the value of the fields are correct where it is possible and mentioned in documentation (for example for by_type parameter) etc.)


**Test design techniques:**
- Error guessing: since the requirements are not very detailed, I can use my own experience and intuition to anticipate possible errors or problems that may occur
- Combinatorial Testing: it can be achieved by using combinations of different query parameters in one request
- Boundary values analysis: (Example: per_page parameter: had max value = 200, so I can test it with 1, 200 (positive) and  0, 201 (negative))
- Equivalence partitioning: (Example: per_page parameter had max value = 200, so I can test it with 1, 200 (positive) and  0, 201 (negative) and 100 (number in range))


### Automation approach details:

1. Building test automation framework: using **Java** programming language
2. Test creation and execution: **TestNG** to create and execute tests, using the @Test annotation to mark tests.
3. **Data-driven approach:** Since we have many possible sets of query parameters and their values, I think it would be reasonable to use a data-driven approach. 
This can be implemented with **TestNG @DataProvider** - it will allow to run test method multiple times with different sets of data.
4. Performing and validating requests: **Rest-Assured** can be used for sending requests, checking response status codes
5. Logging: it will help identify errors and should be applied for all tests. For this purpose **Log4j** can be used
6. Reporting: To create detailed reports and easy to easy-to-analyze testing results **Allure reports** can be used
7. Dependencies management and automated project build : For this purpose **Maven** can be used
8. CI/CD: set up the job that will run the whole test suite in **Gitlab CI/CD or Jenkins** 
9. (Optional): Parallel tests run: to increase the time of test execution - TestNG  "parallel" attribute (set in testng.xml) 
or "threadPoolSize" attribute (set inside the @Test annotation) but given the small number of tests it is optional.


### Estimated time for completing this task: 
Writing all automation tests: up to 20 hours <br>
Preparation of the test scenarios + project setup + CI/CD setup: up to 8-10 hours <br>

Project setup +  test scenarios + all automation tests creation based on the + CI/CD setup: 25-30 hours
