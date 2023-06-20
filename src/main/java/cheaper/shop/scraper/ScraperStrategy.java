package cheaper.shop.scraper;

import cheaper.shop.model.Shop;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScraperStrategy {
    @Autowired
    private List<SeleniumScraper> scrapers;

    public SeleniumScraper getScraper(Shop shop) {
        return scrapers.stream()
                .filter(s -> s.getShopName().equals(shop))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
