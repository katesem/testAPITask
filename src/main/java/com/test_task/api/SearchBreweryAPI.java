package com.test_task.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class SearchBreweryAPI {
    private static final Logger logger = Logger.getLogger(SearchBreweryAPI.class.getName());

    private static final String SEARCH_URL = "https://api.openbrewerydb.org/v1/breweries/search";
    private static final String AUTOCOMPLETE_URL = "https://api.openbrewerydb.org/v1/breweries/autocomplete";
    private static final String QUERY = "query";
    private static final String PER_PAGE = "per_page";

    public static void sendGetSearchRequestWithQueryParameter(String queryParamValue) {
        Response response = sendGetRequest(SEARCH_URL, QUERY, queryParamValue);
        response.then().assertThat().statusCode(200);
        checkSearchResults(response, queryParamValue);
    }


    public static void sendGetAutocompleteRequestWithQueryParameter(String queryParamValue) {
        Response response = sendGetRequest(AUTOCOMPLETE_URL, QUERY, queryParamValue);
        response.then().assertThat().statusCode(200);
        assertThat(response.jsonPath().getList("").size(), lessThanOrEqualTo(15));
    }


    public static void sendGetRequestWithMultipleQueryParameters(String queryParamValue, String perPageParamValue) {
        Response response = RestAssured.given()
                .queryParam(QUERY, queryParamValue)
                .queryParam(PER_PAGE, perPageParamValue)
                .get(SEARCH_URL);

        logger.info(String.format("Sent GET request to %s with query parameters:  %s=%s;  %s=%s", SEARCH_URL, QUERY, queryParamValue, PER_PAGE, perPageParamValue));
        response.then().assertThat().statusCode(200);
        assertThat(String.valueOf(response.jsonPath().getList("").size()), equalTo(perPageParamValue));
    }


    public static void sendGetRequestToInvalidEndpoint(String queryParamValue) {
        String invalidEndpoint = "https://api.openbrewerydb.org/v1/brew/search";
        Response response = sendGetRequest(invalidEndpoint, QUERY, queryParamValue);
        response.then().assertThat().statusCode(404);
        assertThat(response.getBody().asString(), emptyString());
    }


    //Created to avoid code repetition (for sending GET requests with one query parameter)
    private static Response sendGetRequest(String path, String param, String paramValue) {
        Response response = RestAssured.given()
                .queryParam(param, paramValue)
                .get(path);

        logger.info(String.format("Sent GET request to %s with query parameter: %s=%s", path, param, paramValue));
        return response;
    }


    //This method сhecks whether the entered string appears in the values of all fields of the Map object.
    private static void checkSearchResults(Response response, String queryString) {

        List<Map<String, Object>> breweries = response.jsonPath().getList("");
        for (Map<String, Object> brewery : breweries) {

            boolean found = false;
            for (Map.Entry<String, Object> entry : brewery.entrySet()) {
                Object value = entry.getValue();
                if (value == null && "null".equalsIgnoreCase(queryString)) { //special and rare case: if we will decide to find all null values in json
                    found = true;
                } else if (value instanceof String && ((String) value).toLowerCase().contains(queryString.toLowerCase())) {
                    found = true;
                }
            }
      /*      if (found) {  This strings commented because it were used for local debug
                System.out.println("Query search string  '" + queryString + "' found in the following object:");
                System.out.println("Object: " + brewery);
            }
            else{
                System.out.println("Query search string  '" + queryString + "' not found in the following object:");
                System.out.println("Object: " + brewery);
            }*/

            assert found : "Query search string  '" + queryString + "' was not found in all objects";
        }
    }
}