package com.slack.web.ui.pageobjects.views;

import com.slack.web.ui.pageobjects.views.common.LeftMenuItems;
import com.slack.web.ui.pageobjects.views.common.SearchBar;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class AbstractUserContentsPage extends AbstractComponent {

    private LeftMenuItems leftMenuItems;
    private SearchBar searchBar;

    public AbstractUserContentsPage(WebDriver driver) {
        super(driver);
        this.leftMenuItems = new LeftMenuItems(driver);
        this.searchBar = new SearchBar(driver);
    }

    public SearchBar getSearchBar(){
        return searchBar;
    }

    public LeftMenuItems getLeftMenuItems(){
        return leftMenuItems;
    }
}
