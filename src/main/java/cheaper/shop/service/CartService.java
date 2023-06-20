package cheaper.shop.service;

import cheaper.shop.model.Product;
import java.util.List;

public interface CartService {
    void putProductsToCart(List<Product> products);

    List<Product> getProducts();

    List<Product> findCheapestOffer();

    void clear();

    void roundPrice(int digitsAfterComa);
}
