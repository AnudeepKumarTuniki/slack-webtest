package com.slack.web.ui.pageobjects.views.common;

import com.google.common.util.concurrent.Uninterruptibles;
import com.slack.web.ui.pageobjects.views.AbstractComponent;
import com.slack.web.ui.pageobjects.views.search.SearchResultsPage;
import com.slack.web.ui.enums.SearchKey;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SearchBar extends AbstractComponent {

    private final Logger logger = Logger.getLogger(SearchBar.class);
    public SearchBar(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@data-qa='top_nav_search']")
    private WebElement searchBox;

    @FindBy(xpath = "//div[@data-qa='focusable_search_input']/div")
    private WebElement inputSearchText;

    @Override
    protected void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(ExpectedConditions.visibilityOf(searchBox));
    }

    /*********** Actions ***************/
    private void clickSearchBox(){
        searchBox.click();
    }

    public SearchResultsPage enterSearchKey(SearchKey key){
        clickSearchBox();
        inputSearchText.clear();
        Uninterruptibles.sleepUninterruptibly(30, TimeUnit.MILLISECONDS);
        inputSearchText.sendKeys(key.getKey());
        Uninterruptibles.sleepUninterruptibly(30, TimeUnit.MILLISECONDS);
        inputSearchText.sendKeys(Keys.ENTER);
        logger.info(String.format("Enter key- %s in the search box",key.getKey()));
        return new SearchResultsPage(driver);
    }
}
