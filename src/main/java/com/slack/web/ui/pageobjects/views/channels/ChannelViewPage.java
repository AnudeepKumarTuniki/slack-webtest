package com.slack.web.ui.pageobjects.views.channels;

import com.slack.web.ui.pageobjects.views.AbstractUserContentsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class ChannelViewPage extends AbstractUserContentsPage {

    private final Logger logger = Logger.getLogger(ChannelViewPage.class);
    private Channel channelComponent;

    @Override
    protected void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(d->channelTitle.isDisplayed());
    }

    @FindBy(xpath = "//span[@class='p-view_header__channel_title p-view_header__truncated_text']")
    private WebElement channelTitle;

    //Load page along with other page components
    public ChannelViewPage(WebDriver driver) {
        super(driver);
        channelComponent = new Channel(driver);
    }

    public Channel getChannelComponent(){
        return channelComponent;
    }

}
