package cheaper.shop.scraper;

import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import java.util.Optional;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface SeleniumScraper {
    Shop getShopName();

    WebElement useSearchBar(RemoteWebDriver webDriver);

    Optional<Product> findProduct(String searchRequest,
                                  WebElement searchBar,
                                  RemoteWebDriver webDriver);
}
