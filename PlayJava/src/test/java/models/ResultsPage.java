// models/SearchPage.java
package models;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import javax.lang.model.element.Element;
import java.util.List;

public class ResultsPage {
    private final Page page;

    public ResultsPage(Page page) {
        this.page = page;
    }

    /**
     * @return the text of Location part of the filter
     */
    public String filterText() {
        return page.textContent("div._1w4b6lfu > div > button:nth-child(2)");

    }

    /**
     * @return the text of Guests part of the filter
     */
    public String filterGuests() {
        return page.textContent("div._1w4b6lfu > div > button:nth-child(6)");

    }

    /**
     * Selects the number of bedrooms in extra filters
     * rooms - must be positive integer
     */
    public void selectBedRooms(Integer rooms) {
        for (int i = 0; i < rooms; i++) {
            page.click("#filterItem-rooms_and_beds-stepper-min_bedrooms-0 > button:nth-child(3)");
        }
    }

    /**
     * Sets the extra filter
     * beds - must be positive integer
     * pool
     * TO DO - make more dynamic for each filter section
     */
    public void selectExtraFilters(Integer beds) {
        page.click("div._3lctk2 > div > div._7yfwnp");
        selectBedRooms(5);
        page.click("#filterItem-facilities-checkbox-amenities-7");
        page.click("div > footer > a");
    }

    /**
     * @return the filters that are set
     */
    public String checkForFilter() {
            return page.textContent("._12oal24");
    }

    /**
     * @return the link of the first search result
     */
    public String getFirstLink() {
        return page.getAttribute(" div._1mx6kqf > div > span > div > a","href");
    }

    /**
     * Hover over first search result
     */
    public void hoverFirstLink() {
        page.hover(" div._1mx6kqf > div > span > div > a");
    }

    /**
     * @return - is there an entry in the map with a different color
     */
    public boolean coloredMapEntryPresent() {
        page.waitForSelector("[style *= 'background-color: rgb(34, 34, 34)']");
        return page.isVisible("[style *= 'background-color: rgb(34, 34, 34)']");
    }

    /**
     * Waits and then clicks on the map
     * First search result is clicked
     */
    public void selectHoverItemMap() {
        page.waitForSelector("[style *= 'background-color: rgb(34, 34, 34)']");
        page.click("[style *= 'background-color: rgb(34, 34, 34)']");
    }

    /**
     * @return - returns the text of the Selected Map entry
     */
    public String mapOfferText (){
        return page.textContent("._9m9ayv");

    }

    /**
     * @return - returns the text of the first test result
     */
    public String firstResultText (){
        return page.textContent("._1whrsux9");

    }
}





