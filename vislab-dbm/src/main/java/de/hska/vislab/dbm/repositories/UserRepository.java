package de.hska.vislab.dbm.repositories;

import de.hska.vislab.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findByUsernameAndPassword(String username, String password);
}
