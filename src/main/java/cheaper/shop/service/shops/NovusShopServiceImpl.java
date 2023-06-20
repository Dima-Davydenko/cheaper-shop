package cheaper.shop.service.shops;

import cheaper.shop.dao.shops.ShopDaoStrategy;
import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import cheaper.shop.scraper.ScraperStrategy;
import cheaper.shop.scraper.SeleniumScraper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NovusShopServiceImpl implements ShopService {
    private static final Shop SHOP = Shop.NOVUS;
    @Autowired
    private ScraperStrategy scraperStrategy;
    @Autowired
    private ShopDaoStrategy shopDaoStrategy;

    @Override
    public Shop getShopName() {
        return SHOP;
    }

    @Override
    public WebElement searchTab(RemoteWebDriver webDriver) {
        return getScraper().useSearchBar(webDriver);
    }

    @Override
    public List<Product> findProductsViaDb(String searchRequest,
                                           WebElement searchBar,
                                           RemoteWebDriver webDriver) {
        List<Product> products = new ArrayList<>();
        List<Product> productsFromDb = shopDaoStrategy.getDao(SHOP).get(searchRequest);
        for (Product productFromDb : productsFromDb) {
            Optional<Product> productFromWeb = getScraper()
                    .findProduct(productFromDb.getArticle(), searchBar, webDriver);
            productFromWeb.ifPresent(pfw -> products.add(productFromDb
                    .setPrice(pfw.getPrice())
                    .setOldPrice(pfw.getOldPrice())
                    .setName(pfw.getName())
                    .setShop(SHOP.toString())));
        }
        return products;
    }

    private SeleniumScraper getScraper() {
        return scraperStrategy.getScraper(SHOP);
    }
}
