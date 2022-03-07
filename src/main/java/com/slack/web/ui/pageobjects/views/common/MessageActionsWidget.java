package com.slack.web.ui.pageobjects.views.common;

import com.slack.web.ui.pageobjects.views.AbstractComponent;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;


public class MessageActionsWidget extends AbstractComponent {

    private final Logger logger = Logger.getLogger(MessageActionsWidget.class);
    public MessageActionsWidget(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@data-qa='save_message']")
    private WebElement saveMessageIcon;

    @Override
    protected void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d->saveMessageIcon.isDisplayed());
    }

    /*********** Actions *************/
    public void clickSaveMessageButton(){
        saveMessageIcon.click();
    }
}
