package cheaper.shop.dao;

import cheaper.shop.exception.DataProcessingException;
import cheaper.shop.model.Product;
import cheaper.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CartDaoImpl implements CartDao {
    @Override
    public List<Product> getProducts() {
        String query = "SELECT * FROM cart ORDER BY id, price;";
        List<Product> productsInCart = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productsInCart.add(parseProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get products from carts.", e);
        }
        return productsInCart;
    }

    @Override
    public void putProductsToCart(List<Product> products) {
        String query = "INSERT "
                + "INTO cart (id, product, volume, price, old_price, article, shop) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            for (Product product : products) {
                statement.setLong(1, product.getId());
                statement.setString(2, product.getNameInDb());
                statement.setString(3, product.getVolume());
                statement.setBigDecimal(4, product.getPrice());
                statement.setBigDecimal(5, product.getOldPrice());
                statement.setString(6, product.getArticle());
                statement.setString(7, product.getShop());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't insert product to cart "
                    + products + ". ", e);
        }
    }

    @Override
    public List<Product> findCheapestOffer() {
        String query = "SELECT * FROM (SELECT * FROM cart) sr1 "
                + "WHERE price = (SELECT MIN(sr2.price) "
                + "FROM (SELECT * FROM cart) sr2 "
                + "WHERE sr1.id = sr2.id) "
                + "ORDER BY shop";
        List<Product> productsInAllCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productsInAllCarts.add(parseProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get products from carts.", e);
        }
        return productsInAllCarts;
    }

    @Override
    public void roundPrice(int digitsAfterComa) {
        String query = "ALTER TABLE `cheaper_shop`.`cart` "
                + "CHANGE COLUMN `price` `price` DECIMAL(10,?) NULL DEFAULT NULL, "
                + "CHANGE COLUMN `old_price` `old_price` DECIMAL(10,?) NULL DEFAULT NULL;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, digitsAfterComa);
            statement.setInt(2, digitsAfterComa);
            statement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create table 'cart'.", e);
        }
    }

    @Override
    public void clear() {
        String query = "TRUNCATE TABLE cart;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't clear cart", e);
        }
    }

    private Product parseProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getObject("id", Long.class));
        product.setNameInDb(resultSet.getString("product"));
        product.setVolume(resultSet.getString("volume"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setOldPrice(resultSet.getBigDecimal("old_price"));
        product.setArticle(resultSet.getString("article"));
        product.setShop(resultSet.getString("shop"));
        return product;
    }
}
