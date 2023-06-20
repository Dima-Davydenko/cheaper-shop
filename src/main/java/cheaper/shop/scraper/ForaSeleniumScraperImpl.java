package cheaper.shop.scraper;

import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

@Component
public class ForaSeleniumScraperImpl implements SeleniumScraper {
    private static final BigDecimal COINS_COEFF = BigDecimal.valueOf(0.01);
    private static final int HRN_TEXT_LENGTH = 4;
    private static final int WAIT_TIME_MS = 4000;
    private static final String AD_CLOSE_ICON = "modal-closeIcon";
    private static final String SHOP_PAGE_URL = "https://shop.fora.ua";
    private static final String OLD_PRICE_HRN = "old-integer";
    private static final String PRICE_COINS = "current-fraction";
    private static final String PRICE_HRN = "current-integer";
    private static final String PRODUCT_DETAILS = "product-list-item__body";
    private static final String PRODUCT_TITLE = "product-title";
    private static final String PRODUCT_WEIGHT = "product-weight";
    private static final String SEARCH_BAR = "search-input";
    private static final String SOLD_OUT = "add-to-comment-wrapper";
    private final Shop shopName = Shop.Фора;

    @Override
    public Shop getShopName() {
        return shopName;
    }

    @Override
    public WebElement useSearchBar(RemoteWebDriver webDriver) {
        webDriver.get(SHOP_PAGE_URL);
        checkForAd(webDriver);
        waitTime(webDriver);
        return webDriver.findElement(By.className(SEARCH_BAR));
    }

    @Override
    public Optional<Product> findProduct(String searchRequest,
                                         WebElement searchBar,
                                         RemoteWebDriver webDriver) {
        searchBar.sendKeys(searchRequest);
        searchBar.sendKeys(Keys.ENTER);
        waitTime(webDriver);
        List<WebElement> webElements = webDriver.findElements(By.className(PRODUCT_DETAILS));
        if (webElements.size() == 0
                || webElements.get(0).findElements(By.className(SOLD_OUT)).size() > 0) {
            searchBar.sendKeys(Keys.LEFT_CONTROL + "a");
            searchBar.sendKeys(Keys.DELETE);
            return Optional.empty();
        }
        searchBar.sendKeys(Keys.LEFT_CONTROL + "a");
        searchBar.sendKeys(Keys.DELETE);
        return Optional.of(parseProduct(webElements.get(0)));
    }

    private Product parseProduct(WebElement webElement) {
        Product product = new Product();
        String productName = webElement.findElement(By.className(PRODUCT_TITLE)).getText();
        String productWeight = webElement.findElement(By.className(PRODUCT_WEIGHT)).getText();
        BigDecimal currentPriceHrn = BigDecimal.valueOf(Double
                .parseDouble(webElement.findElement(By.className(PRICE_HRN)).getText()));
        String coinsValue = webElement.findElement(By.className(PRICE_COINS)).getText();
        BigDecimal currentPriceCoins = BigDecimal.valueOf(Double.parseDouble(coinsValue
                .substring(0, coinsValue.length() - HRN_TEXT_LENGTH)));
        BigDecimal currentTotalPrice = currentPriceHrn.add(currentPriceCoins.multiply(COINS_COEFF));
        if (webElement.findElements(By.className(OLD_PRICE_HRN)).size() > 0) {
            String oldPriceValue = webElement.findElement(By.className(OLD_PRICE_HRN))
                    .getText().replace(",",".");
            product.setOldPrice(BigDecimal.valueOf(Double.parseDouble(oldPriceValue
                    .substring(0, oldPriceValue.length() - HRN_TEXT_LENGTH))));
        }
        product.setName(productName + " " + productWeight)
                .setPrice(currentTotalPrice);
        return product;
    }

    private void waitTime(RemoteWebDriver webDriver) {
        synchronized (webDriver) {
            try {
                webDriver.wait(WAIT_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkForAd(RemoteWebDriver webDriver) {
        if (webDriver.findElements(By.className(AD_CLOSE_ICON)).size() > 0) {
            webDriver.findElement(By.className(AD_CLOSE_ICON)).click();
        }
    }
}
