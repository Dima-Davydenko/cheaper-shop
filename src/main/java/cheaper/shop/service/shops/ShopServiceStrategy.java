package cheaper.shop.service.shops;

import cheaper.shop.model.Shop;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceStrategy {
    @Autowired
    private List<ShopService> shopServices;

    public ShopService getShopService(Shop shop) {
        return shopServices.stream()
                .filter(s -> s.getShopName().equals(shop))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
