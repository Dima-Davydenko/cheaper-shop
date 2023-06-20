package cheaper.shop.dao.shops;

import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import java.util.List;

public interface ShopDao {
    Shop getShopName();

    List<Product> get(String searchRequest);
}
