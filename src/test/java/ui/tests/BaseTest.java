package ui.tests;

import com.slack.web.ui.utils.StringFunctions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    Logger logger = Logger.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeClass
    public void setUp(ITestContext context) throws MalformedURLException {

        String browser = System.getProperty("browser","chrome");
        String hub_host = System.getProperty("hub_host");
        if(StringFunctions.isNullOrEmptyOrBlank(System.getProperty("slack_url")) ||
                StringFunctions.isNullOrEmptyOrBlank(System.getProperty("email")) ||
                StringFunctions.isNullOrEmptyOrBlank(System.getProperty("passwd")) ||
                StringFunctions.isNullOrEmptyOrBlank(System.getProperty("suiteXmlFile")) ||
                StringFunctions.isNullOrEmptyOrBlank(System.getProperty("channel"))){
            throw new RuntimeException("Fields: slack_url,email,passwd,suiteXmlFile,channel cannot be null or empty or blank");
        }
        if(browser !=null && browser.equalsIgnoreCase("firefox") && hub_host == null){
            WebDriverManager.firefoxdriver().setup();
            this.driver = new FirefoxDriver();
            logger.info("Local firefox initiated");
        }else if(browser !=null && browser.equalsIgnoreCase("chrome") && hub_host == null){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.setExperimentalOption("prefs",prefs);
            options.setExperimentalOption("excludeSwitches",new String[]{"enable-automation"});
            this.driver = new ChromeDriver(options);
            logger.info("Local chrome initiated");
        }else if(browser !=null && hub_host != null){
            MutableCapabilities dc;
            if(browser.equalsIgnoreCase("firefox")){
                dc = new FirefoxOptions();
            }else{
                dc = new ChromeOptions();
            }
            String completeUrl = "http://" + hub_host + ":4444/wd/hub";
            dc.setCapability("name", context.getCurrentXmlTest().getName());
            this.driver = new RemoteWebDriver(new URL(completeUrl), dc);
            logger.info("Remote browser initiated");
        }
        //Initialise web element timeout and launch URL
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        this.driver.manage().window().maximize();
        this.driver.get(System.getProperty("slack_url"));
    }

    @AfterClass
    public void tearDown(){
        this.driver.quit();
    }
}
