package cheaper.shop.service;

import cheaper.shop.dao.CartDao;
import cheaper.shop.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private static final int MIN_ALLOWABLE_VALUE = 0;
    private static final int STANDARD_VALUE = 2;
    @Autowired
    private CartDao cartDao;

    @Override
    public void putProductsToCart(List<Product> products) {
        cartDao.putProductsToCart(products);
    }

    @Override
    public List<Product> getProducts() {
        return cartDao.getProducts();
    }

    @Override
    public List<Product> findCheapestOffer() {
        return cartDao.findCheapestOffer();
    }

    @Override
    public void clear() {
        cartDao.clear();
    }

    @Override
    public void roundPrice(int digitsAfterComa) {
        if (digitsAfterComa < MIN_ALLOWABLE_VALUE || digitsAfterComa > STANDARD_VALUE) {
            return;
        }
        cartDao.roundPrice(digitsAfterComa);
    }
}
