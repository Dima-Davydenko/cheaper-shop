package cheaper.shop.scraper;

import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

@Component
public class AtbSeleniumScraperImpl implements SeleniumScraper {
    private static final int HRN_TEXT_LENGTH = 4;
    private static final int WAIT_TIME_MS = 1000;
    private static final String PRICE_INFO = "multi-snippet";
    private static final String PRODUCT_INFO = "multi-content";
    private static final String PRODUCT_NAME = "span";
    private static final String PRODUCT_PRICE = "multi-price";
    private static final String SEARCH_BAR = "multi-input";
    private static final String SEARCH_PAGE_URL = "https://atbmarket.com/#/search/";
    private static final String SOLD_OUT = "Немає в наявності";
    private final Shop shopName = Shop.АТБ;

    @Override
    public Shop getShopName() {
        return shopName;
    }

    @Override
    public WebElement useSearchBar(RemoteWebDriver webDriver) {
        webDriver.get(SEARCH_PAGE_URL);
        return webDriver.findElement(By.className(SEARCH_BAR));
    }

    @Override
    public Optional<Product> findProduct(String searchRequest,
                                         WebElement searchBar,
                                         RemoteWebDriver webDriver) {
        searchBar.sendKeys(searchRequest);
        waitTime(WAIT_TIME_MS, webDriver);
        List<WebElement> webElements = webDriver.findElements(By.className(PRODUCT_INFO));
        if (webElements.size() == 0 || webElements.get(0)
                .findElement(By.className(PRICE_INFO))
                .getText()
                .contains(SOLD_OUT)) {
            searchBar.clear();
            return Optional.empty();
        }
        searchBar.clear();
        return Optional.of(parseProduct(webElements.get(0)));
    }

    private Product parseProduct(WebElement webElement) {
        Product product = new Product();
        String priceHolder = webElement.findElement(By.className(PRICE_INFO)).getText();
        String productName = webElement.findElement(By.tagName(PRODUCT_NAME)).getText();
        String currentPrice = webElement.findElement(By.className(PRODUCT_PRICE)).getText();
        BigDecimal productPrice = BigDecimal.valueOf(Double.parseDouble(currentPrice
                .substring(0, currentPrice.length() - HRN_TEXT_LENGTH)));
        product.setName(productName);
        if (!currentPrice.equals(priceHolder)) {
            String oldPrice = priceHolder
                    .substring(0, priceHolder.length() - currentPrice.length());
            product.setPrice(productPrice)
                    .setOldPrice(BigDecimal.valueOf(Double.parseDouble(oldPrice)));
        } else {
            product.setPrice(productPrice);
        }
        return product;
    }

    private void waitTime(int timeInMs, RemoteWebDriver webDriver) {
        synchronized (webDriver) {
            try {
                webDriver.wait(timeInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
