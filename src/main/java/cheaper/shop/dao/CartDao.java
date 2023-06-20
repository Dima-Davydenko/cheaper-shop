package cheaper.shop.dao;

import cheaper.shop.model.Product;
import java.util.List;

public interface CartDao {
    List<Product> getProducts();

    List<Product> findCheapestOffer();

    void putProductsToCart(List<Product> products);

    void roundPrice(int digitsAfterComa);

    void clear();
}
