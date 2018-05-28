package de.hska.vislab.dbm.repositories;

import de.hska.vislab.model.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

  List<Product> findByNameOrCategoryOrDetailsRegexAndPriceBetween(String name, String category, String details, double price_min, double price_max);

  List<Product> findByPriceGreaterThanEqualAndPriceBetween(double price_min, double price_max);

  List<Product> findByCategory(String categoryId);

  Product findByName(String name);
}
