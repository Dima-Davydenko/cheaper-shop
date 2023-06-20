package cheaper.shop.service;

import cheaper.shop.model.Product;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = "prototype")
public class ShoppingListCsvImpl implements ShoppingListService {
    private final File file;

    public ShoppingListCsvImpl(File file) {
        this.file = file;
    }

    @Override
    public void form(List<Product> products) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            for (Product product : products) {
                bufferedWriter.write(product.getNameInDb());
                bufferedWriter.write(",");
                bufferedWriter.write(String.valueOf(product.getPrice()));
                bufferedWriter.write(",");
                if (product.getOldPrice() == null) {
                    bufferedWriter.write("");
                } else {
                    bufferedWriter.write(String.valueOf(product.getOldPrice()));
                }
                bufferedWriter.write(",");
                bufferedWriter.write(product.getShop());
                bufferedWriter.write(System.lineSeparator());
            }
            bufferedWriter.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file.", e);
        }
    }
}
