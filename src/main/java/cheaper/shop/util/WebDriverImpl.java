package cheaper.shop.util;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

@Component
public class WebDriverImpl implements WebDriver {
    private static final String PATH_TO_WEBDRIVER = "ENTER YOUR PATH";

    public WebDriverImpl() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_WEBDRIVER);
    }

    @Override
    public RemoteWebDriver getWebDriver() {
        return new ChromeDriver(setOptions());
    }

    private ChromeOptions setOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        //chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("start-maximized");
        return chromeOptions;
    }
}
