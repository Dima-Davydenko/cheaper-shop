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
public class NovusSeleniumScraperImpl implements SeleniumScraper {
    private static final int NUMBER_OF_PRODUCTS = 100;
    private static final int WAIT_TIME_MS = 1500;
    private static final String SEARCH_PAGE_URL = "https://novus.online/search?text=";
    private static final String OLD_PRICE = "base-card__price-old";
    private static final String PRODUCT_ARTICLE = "product-page__copy-value";
    private static final String PRODUCT_DETAILS = "catalog-products__item";
    private static final String PRODUCT_PAGE_ATTRIBUTE = "href";
    private static final String PRODUCT_PRICE = "base-card__price-current";
    private static final String PRODUCT_TITLE = "base-card__label";
    private static final String PRODUCTS_QUANTITY = "badge__value";
    private static final String SOLD_OUT = "ProductTile__unavailable";
    private final Shop shopName = Shop.NOVUS;

    @Override
    public Shop getShopName() {
        return shopName;
    }

    @Override
    public WebElement useSearchBar(RemoteWebDriver webDriver) {
        webDriver.get(SEARCH_PAGE_URL);
        waitTime(webDriver);
        List<WebElement> elements = webDriver
                .findElements(By.className("base-autocomplete__input"));
        WebElement searchBar = null;
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                searchBar = element;
            }
        }
        return searchBar;
    }

    @Override
    public Optional<Product> findProduct(String searchRequest,
                                         WebElement searchBar,
                                         RemoteWebDriver webDriver) {
        searchBar.sendKeys(searchRequest);
        waitTime(webDriver);
        searchBar.sendKeys(Keys.ENTER);
        waitTime(webDriver);
        List<WebElement> webElements = webDriver.findElements(By.className(PRODUCT_DETAILS));
        if (webElements.size() == 0
                || webElements.get(0).findElements(By.className(SOLD_OUT)).size() > 0) {
            return Optional.empty();
        }
        if (webElements.size() > 1 && Integer.parseInt(webDriver
                .findElement(By.className(PRODUCTS_QUANTITY))
                .getText()) < NUMBER_OF_PRODUCTS) {
            return findRequestedProduct(webElements, searchRequest, webDriver);
        }
        return Optional.of(parseProduct(webElements.get(0)));
    }

    private Product parseProduct(WebElement webElement) {
        Product product = new Product();
        String productName = webElement.findElement(By.className(PRODUCT_TITLE)).getText();
        double currentPrice = Double
                .parseDouble(webElement.findElement(By.className(PRODUCT_PRICE)).getText());
        if (webElement.findElements(By.className(OLD_PRICE)).size() > 0) {
            BigDecimal oldPrice = BigDecimal.valueOf(Double
                    .parseDouble(webElement.findElement(By.className(OLD_PRICE)).getText()));
            product.setOldPrice(oldPrice);
        }
        product.setName(productName)
                .setPrice(BigDecimal.valueOf(currentPrice));
        return product;
    }

    private Optional<Product> findRequestedProduct(List<WebElement> webElements,
                                                   String searchRequest,
                                                   RemoteWebDriver webDriver) {
        for (WebElement webElement : webElements) {
            String productPage = webElement.getAttribute(PRODUCT_PAGE_ATTRIBUTE);
            webDriver.navigate().to(productPage);
            String productArticle = webDriver.findElement(By.className(PRODUCT_ARTICLE)).getText();
            if (searchRequest.equals(productArticle)) {
                webDriver.navigate().back();
                return Optional.of(parseProduct(webElement));
            }
            webDriver.navigate().back();
        }
        return Optional.empty();
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
}
