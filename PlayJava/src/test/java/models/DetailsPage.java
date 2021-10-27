// models/SearchPage.java
package models;

import com.microsoft.playwright.Page;

public class DetailsPage {
    private final Page page;

    public DetailsPage(Page page) {
        this.page = page;
    }

    /**
     * Navigates to given airbbnb+URL
     * URL - string
     */
    public void navigate(String URL) {
        page.navigate("https://www.airbnb.com"+URL);
    }

    /**
     * @return - returns the Amenities of the selected offer
     */
    public String offerDetails (){
        return page.textContent("._1byskwn");

    }


}





