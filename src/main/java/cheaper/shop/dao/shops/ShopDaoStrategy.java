package cheaper.shop.dao.shops;

import cheaper.shop.model.Shop;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopDaoStrategy {
    @Autowired
    private List<ShopDao> dao;

    public ShopDao getDao(Shop shop) {
        return dao.stream()
                .filter(d -> d.getShopName().equals(shop))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
