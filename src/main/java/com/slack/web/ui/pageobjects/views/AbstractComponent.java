package com.slack.web.ui.pageobjects.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractComponent {

    protected abstract void waitUntilLoaded();
    protected final WebDriver driver;

    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
        this.waitUntilLoaded();
    }

}
