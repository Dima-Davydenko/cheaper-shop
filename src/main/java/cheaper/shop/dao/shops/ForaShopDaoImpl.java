package cheaper.shop.dao.shops;

import cheaper.shop.exception.DataProcessingException;
import cheaper.shop.model.Product;
import cheaper.shop.model.Shop;
import cheaper.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ForaShopDaoImpl implements ShopDao {
    private final Shop shopName = Shop.Фора;

    @Override
    public Shop getShopName() {
        return shopName;
    }

    @Override
    public List<Product> get(String searchRequest) {
        String query = "SELECT id, product, volume, article_fora "
                + "FROM products "
                + "WHERE product LIKE '" + searchRequest + "'";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("article_fora") == null) {
                    continue;
                }
                Product product = new Product();
                product.setId(resultSet.getObject("id", Long.class));
                product.setNameInDb(resultSet.getString("product"));
                product.setVolume(resultSet.getString("volume"));
                product.setArticle(resultSet.getString("article_fora"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get product by request "
                    + searchRequest, e);
        }
        return products;
    }
}
