package cheaper.shop;

import cheaper.shop.config.AppConfig;
import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import cheaper.shop.service.CartService;
import cheaper.shop.service.ShoppingListService;
import cheaper.shop.service.shops.ShopService;
import cheaper.shop.service.shops.ShopServiceStrategy;
import cheaper.shop.util.RequestConvector;
import cheaper.shop.util.WebDriver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    //where your shopping list should be saved
    private static final String PATH = "C:\\Users\\acer\\Desktop\\";
    private static final String FILE_NAME = "cheapestOffer.csv";

    public static void main(String[] args) {
        List<String> productsToBuy = List.of(
                "enter your shopping List",
                "each product separately"
        );

        RequestConvector requestConvector = new RequestConvector();
        List<String> preparedRequests = productsToBuy.stream()
                .map(requestConvector::prepareRequest).toList();

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CartService cartService = context.getBean(CartService.class);
        cartService.roundPrice(-1);
        cartService.clear();

        WebDriver webDriver = context.getBean(WebDriver.class);
        RemoteWebDriver webDriverAtb = webDriver.getWebDriver();
        RemoteWebDriver webDriverFora = webDriver.getWebDriver();
        RemoteWebDriver webDriverNovus = webDriver.getWebDriver();

        ShopServiceStrategy shopServiceStrategy = context.getBean(ShopServiceStrategy.class);
        ShopService atb = shopServiceStrategy.getShopService(Shop.АТБ);
        ShopService fora = shopServiceStrategy.getShopService(Shop.Фора);
        ShopService novus = shopServiceStrategy.getShopService(Shop.NOVUS);

        WebElement searchTabAtb = atb.searchTab(webDriverAtb);
        WebElement searchTabFora = fora.searchTab(webDriverFora);
        WebElement searchTabNovus = novus.searchTab(webDriverNovus);

        List<Product> products = new ArrayList<>();
        for (String request : preparedRequests) {
            products.addAll(atb.findProductsViaDb(request, searchTabAtb, webDriverAtb));
            products.addAll(fora.findProductsViaDb(request, searchTabFora, webDriverFora));
            products.addAll(novus.findProductsViaDb(request, searchTabNovus, webDriverNovus));
        }
        cartService.putProductsToCart(products);

        List<Product> cheapestOffers = cartService.findCheapestOffer();
        List<Product> allOffers = cartService.getProducts();

        File file = new File(PATH + FILE_NAME);
        ShoppingListService shoppingList =
                context.getBean(ShoppingListService.class, file);

        shoppingList.form(cheapestOffers);
        shoppingList.form(allOffers);

        webDriverAtb.quit();
        webDriverFora.quit();
        webDriverNovus.quit();
    }
}
