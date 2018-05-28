package de.hska.vislab.dbm.repositories;

import de.hska.vislab.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CategoryRepository extends MongoRepository<Category, String> {

  boolean existsByName(String name);

  Category findByName(String name);

  List<Category> findByNameRegex(String name);
}
