import org.testng.annotations.Test;

import static com.test_task.api.SearchBreweryAPI.*;

public class SearchBreweryTest {
    private static final String QUERY_VALUE = "dog";
    private static final String PER_PAGE_VALUE = "5";


    @Test(description = "Scenario: Search with valid query param")
    public void searchWithValidQueryParamTest() {
        //Verify that request with "query=dog" returns an array of Json objects and each object had search string
        sendGetSearchRequestWithQueryParameter(QUERY_VALUE);
    }

    @Test(description = "Scenario: Autocomplete Search with valid query paramater")
    //Verify that request with "query=dog" returns an array of JSON objects: each of the had only 2 fields: id and name
    // and size of the result json object not more than 15 as it was mentioned in documentation notes
    public void autocompleteSearchWithValidQueryParamTest() {
        sendGetAutocompleteRequestWithQueryParameter(QUERY_VALUE);
    }


    @Test(description = "Scenario: Search with  valid multiple query params")
    //Verify that request with "query=dog&" returns response without errors and size of the JSON objects array corresponds
    // to the value of per_page parameter
    public void searchWithValidMultipleQueryParamTest() {
        sendGetRequestWithMultipleQueryParameters(QUERY_VALUE, PER_PAGE_VALUE);
    }

    @Test(description = "Scenario: Request to Invalid Endpoint")
    public void requestToInvalidEndpoint() {
        //Verify that request to invalid endpoint returns 404 status code and empty body
        sendGetRequestToInvalidEndpoint(QUERY_VALUE);
    }
}