package de.hska.vislab.dbm.repositories;

import de.hska.vislab.model.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

  List<Product> findByNameAndPriceGreaterThanEqualAndPriceIsLessThanEqual(String name, double price_min, double price_max);

  List<Product> findByNameAndPriceGreaterThanEqual(String name, double price_min);

  List<Product> findByNameAndPriceIsLessThanEqual(String name, double price_max);

  List<Product> findByPriceGreaterThanEqualAndPriceIsLessThanEqual(double price_min, double price_max);

  List<Product> findByName(String name);

  List<Product> findByPriceGreaterThanEqual(double price_min);

  List<Product> findByPriceIsLessThanEqual(double price_max);
}
