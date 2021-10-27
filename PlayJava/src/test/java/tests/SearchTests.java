package tests;

import com.microsoft.playwright.*;
import models.SearchPage;
import models.ResultsPage;
import models.DetailsPage;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTests{
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldPerformSearch() {
        Page page = browser.newPage();
        SearchPage searchPage = new SearchPage(page);
        ResultsPage resultsPage = new ResultsPage(page);
        //Open www.airbnb.com.
        searchPage.navigate();
        //Select Rome, Italy as a location.
        searchPage.search("Rome, Italy");
        // Pick a Check-In date one week after the current date.
        // Pick a Check-Out date one week after the Check-In date.
        searchPage.selectDates(8,15);
        //Select the number of guests as 2 adults and 1 child.
        searchPage.selectGuests(2,1,0);
        //Search for properties.
        searchPage.search();

        //Verify that the applied filters are correct - location
        String content = resultsPage.filterText();
        assertThat(content, containsString("Rome"));
        //Verify that the applied filters are correct - guests
        String guests = resultsPage.filterGuests();
        assertThat(guests, containsString("3"));
    }


    @Test
    void shouldCheckTheBox() {
        Page page = browser.newPage();
        SearchPage searchPage = new SearchPage(page);
        ResultsPage resultsPage = new ResultsPage(page);
        DetailsPage detailsPage = new DetailsPage(page);
        //Open www.airbnb.com.
        searchPage.navigate();
        //Select Rome, Italy as a location.
        searchPage.search("Rome, Italy");
        // Pick a Check-In date one week after the current date.
        // Pick a Check-Out date one week after the Check-In date.
        searchPage.selectDates(8,15);
        //Select the number of guests as 2 adults and 1 child.
        searchPage.selectGuests(2,1,0);
        //Search for properties.
        searchPage.search();
        //Click More filters
        //Select the number of bedrooms as 5.
        //Select Pool from the Facilities section.
        //Click Show
        resultsPage.selectExtraFilters(5);
        //Verify that the properties displayed on the first page have at least the number of selected bedrooms
        String rooms = resultsPage.checkForFilter();
        assertThat(rooms, containsString("5"));//beedrooms

        //Open the details of the first property.
        String firstLink = resultsPage.getFirstLink();
        detailsPage.navigate(firstLink);

        // Check that in the ‘Pool’ option is displayed in the ‘Amenities’ popup under the ‘Facilities’
        //category.
        String details = detailsPage.offerDetails();
        assertThat(details, containsString("Pool"));


    }

    @Test
    void shouldShowMap() {
        Page page = browser.newPage();
        SearchPage searchPage = new SearchPage(page);
        ResultsPage resultsPage = new ResultsPage(page);
        //Open www.airbnb.com.
        searchPage.navigate();
        //Select Rome, Italy as a location.
        searchPage.search("Rome, Italy");
        // Pick a Check-In date one week after the current date.
        // Pick a Check-Out date one week after the Check-In date.
        searchPage.selectDates(8,15);
        //Select the number of guests as 2 adults and 1 child.
        searchPage.selectGuests(2,1,0);
        //Search for properties.
        searchPage.search();
        //Hover over the first property in the results list.
        resultsPage.hoverFirstLink();
        //Check that the property is displayed on the map and the color of the pin changes (upon hover).
        Boolean coloredPin = resultsPage.coloredMapEntryPresent();
        assertEquals(coloredPin,true);
        //After identifying the property on the map, click it.
        resultsPage.selectHoverItemMap();
        //Verify that the details shown in the map popup are the same as the ones shown in the search
        //results.
        String maptext = resultsPage.mapOfferText();
        String resulttext = resultsPage.firstResultText();
        assertThat(maptext, containsString(resulttext));

    }
}