package de.hska.vislab.dbm.repositories;

import de.hska.vislab.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {}
