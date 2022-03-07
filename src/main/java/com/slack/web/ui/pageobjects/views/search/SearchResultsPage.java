package com.slack.web.ui.pageobjects.views.search;

import com.slack.web.ui.enums.SearchKey;
import com.slack.web.ui.pageobjects.views.AbstractUserContentsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage extends AbstractUserContentsPage {

    private final Logger logger = Logger.getLogger(SearchResultsPage.class);

    //Load page along with other page components
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(d->searchResultsTitle.isDisplayed());
    }

    @FindBy(xpath = "//span[@class='c-truncate c-truncate--break_words']")
    private WebElement searchResultsTitle;


    /*********** Actions ************/
    /**
     * Observation: The message in the search results doesn't popup on the first try but comes in the next retry attempts
     * after some significant delay. This code will handle the retry and find on the page. Makes a maximum of 3 retry attempts
     */

    public boolean isSavedMessageDisplayed(String message){
        boolean found = false;
        int counter = 1;
        do{
            if(counter > 1){
                logger.info("Reattempting search");
                performSavedItemSearch();
            }
            found = isMessagePresent(message);
            if(found)
                break;
            else
                counter++;
        }while(counter <=3);
        return found;
    }

    private void performSavedItemSearch(){
        getSearchBar().enterSearchKey(SearchKey.HAS_STAR);
    }

    private boolean isMessagePresent(String message){
        try{
            new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class)
                    .until(driver->driver.findElement(By.xpath(
                            String.format("//div[@class='p-rich_text_section' and contains(text(),'%s')]"
                                    , message))).isDisplayed());
            logger.info("Message found in search results");
            return true;
        }catch(Exception e){
            logger.error(String.format("Exception while searching: %s",message),e);
            return false;
        }
    }
}
