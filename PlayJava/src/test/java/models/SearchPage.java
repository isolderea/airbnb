// models/SearchPage.java
package models;

import com.microsoft.playwright.Page;

public class SearchPage {
    private final Page page;

    public SearchPage(Page page) {
        this.page = page;
    }

    /**
     * Navigates to given URL
     * URL - string
     */
    public void navigate() {
        page.navigate("https://www.airbnb.com/");
    }

    /**
     * Searches for location
     * text - string to search for
     */
    public void search(String text) {
        page.click("[data-testid=\"structured-search-input-field-query\"]");
        page.fill("[data-testid=\"structured-search-input-field-query\"]", text);
    }

    /**
     * Sets checkin and checkout dates
     * checkin - set the number of days from today
     * checkout - set the number of days from today
     */
    public void selectDates(Integer checkin, Integer checkout) {
        page.click("._1akb2mdw[data-testid *=\"structured-search-input-field-split-dates-0\"]");
        page.click(":nth-match(._12fun97,"+checkin+" )");
        page.click(":nth-match(._12fun97,"+checkout+" )");

    }

    /**
     * Clicks the Adult button
     * adults - set the number adults
     */
    public void selectAdults(Integer adults) {
        for (int i = 0; i < adults; i++) {
            page.click("[data-testid=\"stepper-adults-increase-button\"]");
        }
    }

    /**
     * Clicks the Children button
     * kids - set the number adults
     */
    public void selectKids(Integer kids){
        for (int i = 0; i < kids; i++) {
           page.click("[data-testid=\"stepper-children-increase-button\"]");
        }
    }

    /**
     * Sets the number of guests
     * adults - set the number adults
     * kids - set the number of kids
     * infants - set the number of infants
     */
    public void selectGuests (Integer adults, Integer kids, Integer infants){
           page.click("._1yulsurh [data-testid *=\"structured-search-input-field-guests-button\"]");
           selectAdults(adults);
           selectKids(kids);
    }

    /**
     * Clicks the Search button
     */
    public void search (){
        page.click("div[role=\"tabpanel\"] button:has-text(\"Suche\")");

    }
}

