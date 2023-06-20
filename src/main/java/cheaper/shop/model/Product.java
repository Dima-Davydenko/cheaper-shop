package cheaper.shop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private BigDecimal oldPrice;
    private String article;
    private String name;
    private String nameInDb;
    private String shop;
    private String volume;

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getArticle() {
        return article;
    }

    public Product setArticle(String article) {
        this.article = article;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public Product setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public Product setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameInDb() {
        return nameInDb;
    }

    public Product setNameInDb(String nameInDb) {
        this.nameInDb = nameInDb;
        return this;
    }

    public String getShop() {
        return shop;
    }

    public Product setShop(String shop) {
        this.shop = shop;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public Product setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return getId() == product.getId()
                && Objects.equals(getPrice(), product.getPrice())
                && Objects.equals(getOldPrice(), product.getOldPrice())
                && Objects.equals(getArticle(), product.getArticle())
                && Objects.equals(getName(), product.getName())
                && Objects.equals(getNameInDb(), product.getNameInDb())
                && Objects.equals(getShop(), product.getShop())
                && Objects.equals(getVolume(), product.getVolume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getPrice(),
                getOldPrice(),
                getArticle(),
                getName(),
                getNameInDb(),
                getShop(),
                getVolume());
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", nameInDb='" + nameInDb + '\''
                + ", volume='" + volume + '\''
                + ", price=" + price
                + ", oldPrice=" + oldPrice
                + ", article='" + article + '\''
                + ", shop='" + shop + '\''
                + '}';
    }
}
