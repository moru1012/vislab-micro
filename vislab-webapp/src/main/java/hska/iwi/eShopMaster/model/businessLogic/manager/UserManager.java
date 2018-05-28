package hska.iwi.eShopMaster.model.businessLogic.manager;


import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;


public interface UserManager {
    
    void registerUser(String username, String name, String lastName, String password, Role role);
    
    User getUserByUsername(String username);
    
    boolean deleteUserById(String id);
    
    Role getRoleByLevel(int level);
    
    boolean doesUserAlreadyExist(String username);
}
