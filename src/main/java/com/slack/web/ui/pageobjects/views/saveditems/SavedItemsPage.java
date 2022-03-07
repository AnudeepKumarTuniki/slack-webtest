package com.slack.web.ui.pageobjects.views.saveditems;

import com.slack.web.ui.pageobjects.views.AbstractUserContentsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class SavedItemsPage extends AbstractUserContentsPage {

    Logger logger = Logger.getLogger(SavedItemsPage.class);

    @Override
    protected void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(d->savedItemsTitle.isDisplayed());
    }

    public SavedItemsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Saved items']")
    private WebElement savedItemsTitle;

    /************** ACTIONS ****************/
    public boolean isMessageSaved(String message){
        try{
            new WebDriverWait(driver,Duration.ofSeconds(20))
                    .until(driver->driver.findElement(
                            By.xpath(String.format("//div[@class='p-rich_text_section' and contains(text(),'%s')]"
                                    ,message))).isDisplayed());
            logger.info("Message displayed on the Saved items page");
            return true;
        }catch (Exception e){
            logger.error(String.format("Exception while searching %s on Saved items page",message),e);
            return false;
        }
    }
}
