package com.slack.web.ui.pageobjects.views.login;

import com.slack.web.ui.pageobjects.views.AbstractComponent;
import com.slack.web.ui.pageobjects.views.channels.ChannelViewPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginComp extends AbstractComponent {

    private final Logger logger = Logger.getLogger(LoginComp.class);
    public LoginComp(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@data-qa='login_email']")
    private WebElement emailText;

    @FindBy(xpath= "//input[@data-qa='login_password']")
    private WebElement passWordText;

    @FindBy(xpath = "//button[@data-qa='signin_button']")
    private WebElement signInButton;

    @FindBy(linkText = "use Slack in your browser")
    private WebElement useSlackInBrowserLink;

    @Override
    protected void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(signInButton));
    }

    /************ Actions *************/

    public void enterEmailText(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(email== null | !matcher.matches()){
            throw new IllegalArgumentException("Email address is not valid");
        }
        emailText.sendKeys(email);
    }

    public void enterPassword(String passWord){
        if(passWord == null){
            throw new IllegalArgumentException("Password cannot be null");
        }
        passWordText.sendKeys(passWord);
    }

    public ChannelViewPage clickSingInButton(){
        signInButton.click();
            new WebDriverWait(driver,Duration.ofSeconds(100))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class)
                    .until(driver -> useSlackInBrowserLink.isDisplayed());
            useSlackInBrowserLink.click();

        return new ChannelViewPage(driver);
    }
}
