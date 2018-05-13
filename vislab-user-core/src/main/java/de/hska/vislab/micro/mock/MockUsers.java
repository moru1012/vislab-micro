package de.hska.vislab.micro.mock;

import de.hska.vislab.dbm.MongoConfigurationManager;
import de.hska.vislab.dbm.repositories.RoleRepository;
import de.hska.vislab.dbm.repositories.UserRepository;
import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock users
 *
 * @author Ruwen Moos
 */
public class MockUsers {

  public static List<User> users = new ArrayList<>();

  public static void mock() {
    UserRepository userRepository =
        MongoConfigurationManager.getInstance().getBean(UserRepository.class);
    List<User> all = userRepository.findAll();
    if (all.isEmpty()) {
      RoleRepository roleRepository =
          MongoConfigurationManager.getInstance().getBean(RoleRepository.class);
      Role adminRole = new Role("admin", 0);
      roleRepository.save(adminRole);
      Role userRole = new Role("user", 1);
      roleRepository.save(userRole);

      User admin = new User("admin", "admin", "admin", "admin", adminRole);
      userRepository.save(admin);

      User user = new User("user", "Max", "Mustermann", "password", userRole);
      userRepository.save(user);
    }
  }
}
