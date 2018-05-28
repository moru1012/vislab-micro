package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;

import java.util.Map;

public class LoginAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = -983183915002226000L;
    private String username = null;
    private String password = null;
    private String firstName;
    private String lastName;
    private String role;


    @Override
    public String execute() {

        // Return string:
        String result = "input";

        UserManager myCManager = new UserManagerImpl();

        // Get user from DB:
        User user = myCManager.getUserByUsername(getUsername());

        // Does user exist?
        if (user != null) {
            // Is the password correct?
            if (user.getPassword().equals(getPassword())) {
                // Get session to save user role and login:
                Map<String, Object> session = ActionContext.getContext().getSession();

                // Save user object in session:
                session.put("webshop_user", user);
                session.put("message", "");
                firstName = user.getFirstName();
                lastName = user.getLastName();
                role = user.getRole().getType();
                result = "success";
            } else {
                addActionError(getText("error.password.wrong"));
            }
        } else {
            addActionError(getText("error.username.wrong"));
        }

        return result;
    }

    @Override
    public void validate() {
        if (getUsername().length() == 0) {
            addActionError(getText("error.username.required"));
        }
        if (getPassword().length() == 0) {
            addActionError(getText("error.password.required"));
        }
    }

    public String getUsername() {
        return (this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return (this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
