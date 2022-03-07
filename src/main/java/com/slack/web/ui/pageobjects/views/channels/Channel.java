package com.slack.web.ui.pageobjects.views.channels;

import com.slack.web.ui.pageobjects.views.AbstractComponent;
import com.slack.web.ui.pageobjects.views.common.MessageActionsWidget;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Channel extends AbstractComponent {

    private final Logger logger = Logger.getLogger(Channel.class);

    public Channel(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOf(sendMessageTextBox));
    }

    @FindBy(xpath = "//div[contains(@class,'ql-editor')]")
    private WebElement sendMessageTextBox;

    @FindBy(xpath = "//button[@data-qa='texty_send_button']")
    private WebElement sendMessageButton;

    /********* Actions ***********/
    public boolean isExpectedChannelLoaded(String channelName) {
        return driver
                .findElement(By.xpath(
                        String.format("//span[contains(@class,'p-view_header__channel_title') and text()='%s']"
                                , channelName))).isDisplayed();
    }

    public void enterMessageAndSend(String message) {
        sendMessageTextBox.sendKeys(message);
        new WebDriverWait(driver,Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(sendMessageButton));
        sendMessageButton.click();
    }

    public boolean isMessageSentToChannel(String message){
        try{
            //Verify the the message appears on the channel
            new WebDriverWait(driver,Duration.ofSeconds(20)).until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(
                            String.format("//div[@class='p-rich_text_section' and contains(text(),'%s')]"
                                    ,message))));
            return true;
        }catch (Exception e){
            logger.error("Exception occured while trying to check if message is sent to the channel",e);
            return false;
        }
    }

    public MessageActionsWidget clickMessageToPerformActions(String message){
        driver.findElement(By.xpath(String.format("//div[@class='p-rich_text_section' and contains(text(),'%s')]"
                ,message))).click();
        return new MessageActionsWidget(driver);
    }

    //Returns the label attached to the message e.g: 'Added to your saved items' when saved
    public void verifyMessageLabel(String message,String expectedLabel){
        try{
           String actualLabel = driver.findElement(
                                By.xpath(String.format("//div[@class='p-rich_text_section' and contains(text(),'%s')]/ancestor::div[@class='c-message_kit__gutter']/preceding-sibling::div"
                                                        ,message))).getText();
           assert actualLabel.equals(expectedLabel);
        }catch(Exception e){
            logger.error("Exception occured while trying to find saved message on saveditems page",e);
            throw new RuntimeException("Exception occurred while tracing the message label",e);
        }
    }
}
