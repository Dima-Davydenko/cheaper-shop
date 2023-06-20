package cheaper.shop.service.shops;

import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface ShopService {
    Shop getShopName();

    WebElement searchTab(RemoteWebDriver webDriver);

    List<Product> findProductsViaDb(String searchRequest,
                                    WebElement searchTab,
                                    RemoteWebDriver webDriver);
}
