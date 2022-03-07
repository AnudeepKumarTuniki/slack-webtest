package com.slack.web.ui.pageobjects.views.login;

import com.slack.web.ui.pageobjects.views.AbstractComponent;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends AbstractComponent {

    private final Logger logger = Logger.getLogger(LoginPage.class);
    private LoginComp loginComponent;

    @Override
    protected void waitUntilLoaded() {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(d->slackTitle.isDisplayed());
    }

    @FindBy(xpath = "//img[@title='Slack']")
    private WebElement slackTitle;

    //Load page along with other page components
    public LoginPage(final WebDriver driver){
        super(driver);
        this.loginComponent = new LoginComp(driver);
    }

    public void launchWebUrl(String url){
        driver.get(url);
    }
    public LoginComp getLoginComponent(){
        return loginComponent;
    }

}
