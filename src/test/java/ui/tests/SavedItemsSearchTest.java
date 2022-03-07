package ui.tests;

import com.slack.web.ui.enums.SearchKey;
import com.slack.web.ui.listeners.TestListener;
import com.slack.web.ui.pageobjects.views.channels.ChannelViewPage;
import com.slack.web.ui.pageobjects.views.common.MessageActionsWidget;
import com.slack.web.ui.pageobjects.views.login.LoginPage;
import com.slack.web.ui.pageobjects.views.saveditems.SavedItemsPage;
import com.slack.web.ui.pageobjects.views.search.SearchResultsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Listeners(TestListener.class)
public class SavedItemsSearchTest extends BaseTest{


    private Logger logger = Logger.getLogger(SavedItemsSearchTest.class);
    /**
     * TestCase: Adds a message to saved items and verifies its display in the search with key "has:star"
     *
     * Steps:
     * Launch Slack Web URL
     * Login with a valid user and password
     * Launch the desired channel from menu items on the left
     * Send a message to channel and verify it is displayed
     * Add the message sent to saved items
     * Click on 'Saved Items' from menu items on the left
     * Verify if the message is present and displayed in the saved items
     * Enter 'has:star' in the search button on the top of the page
     * Verify that the message is captured in the search results
     * if it doesn't display retry 'has:star' and fail if it still doesn't appear on the search results page.
     */
    @Test
    public void searchSavedItems(){

        //Login
        LoginPage loginpage = new LoginPage(this.driver);
        loginpage.getLoginComponent().enterEmailText(System.getProperty("email"));
        loginpage.getLoginComponent().enterPassword(System.getProperty("passwd"));
        ChannelViewPage homePage = loginpage.getLoginComponent().clickSingInButton();

        //Launch users channel
        String channel = System.getProperty("channel");
        ChannelViewPage userChannelPage = homePage.getLeftMenuItems().clickChannel(channel);
        assertTrue(userChannelPage.getChannelComponent().isExpectedChannelLoaded(channel));

        //Generate a message and send
        String message = String.format("Test automation message %s",RandomStringUtils.randomAlphanumeric(5));
        userChannelPage.getChannelComponent().enterMessageAndSend(message);
        assertTrue(userChannelPage.getChannelComponent().isMessageSentToChannel(message));

        //Save message and verify in saved items
        MessageActionsWidget actionsWidget = userChannelPage.getChannelComponent().clickMessageToPerformActions(message);
        actionsWidget.clickSaveMessageButton();
        userChannelPage.getChannelComponent().verifyMessageLabel(message,"Added to your saved items");
        SavedItemsPage savedItemsPage = userChannelPage.getLeftMenuItems().clickSavedItems();
        assertTrue(savedItemsPage.isMessageSaved(message));

        //Search message in search results page with key
        SearchResultsPage searchResultsPage = savedItemsPage.getSearchBar().enterSearchKey(SearchKey.HAS_STAR);
        assertTrue(searchResultsPage.isSavedMessageDisplayed(message));
    }

}