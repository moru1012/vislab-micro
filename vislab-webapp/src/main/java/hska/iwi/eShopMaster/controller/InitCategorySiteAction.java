package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vislab.model.Category;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;

import java.util.List;
import java.util.Map;

public class InitCategorySiteAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = -1108136421569378914L;

    private String pageToGoTo;
    private User user;

    private List<Category> categories;

    @Override
    public String execute() {

        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        user = (User) session.get("webshop_user");
        if (user != null && user.isAdmin()) {
            CategoryManager categoryManager = new CategoryManagerImpl(user.getUsername(), user.getPassword());
            this.setCategories(categoryManager.getCategories());

            if (pageToGoTo != null) {
                if (pageToGoTo.equals("c")) {
                    res = "successC";
                } else if (pageToGoTo.equals("p")) {
                    res = "successP";
                }
            }
        }

        return res;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPageToGoTo() {
        return pageToGoTo;
    }

    public void setPageToGoTo(String pageToGoTo) {
        this.pageToGoTo = pageToGoTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
