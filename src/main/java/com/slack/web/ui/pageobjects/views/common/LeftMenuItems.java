package com.slack.web.ui.pageobjects.views.common;

import com.slack.web.ui.pageobjects.views.AbstractComponent;
import com.slack.web.ui.pageobjects.views.channels.ChannelViewPage;
import com.slack.web.ui.pageobjects.views.saveditems.SavedItemsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LeftMenuItems extends AbstractComponent {

    private final Logger logger = Logger.getLogger(LeftMenuItems.class);
    public LeftMenuItems(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='p-channel_sidebar__name' and text()='Saved items']")
    private WebElement savedItems;

    @Override
    protected void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(savedItems));
    }

    /******* Action ********/
    public SavedItemsPage clickSavedItems(){
        savedItems.click();
        return new SavedItemsPage(driver);
    }

    public ChannelViewPage clickChannel(String channelName){
        try{
            driver.findElement(
                    By.xpath(
                            String.format("//span[@class='p-channel_sidebar__name' and text()='%s']"
                                    ,channelName))).click();
        }catch(Exception e){
            logger.error(String.format("Exception while attempting to open the channel %s",channelName),e);
            throw new RuntimeException("Exception occurred while attempting to click channel",e);
        }
        return new ChannelViewPage(driver);
    }
}
