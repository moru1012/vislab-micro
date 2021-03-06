package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vislab.model.Category;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;

import java.util.List;
import java.util.Map;

public class AddProductAction extends ActionSupport {

    private static final long serialVersionUID = 39979991339088L;

    private String name = null;
    private String price = null;
    private String categoryId = "";
    private String details = null;
    private List<Category> categories;

    @Override
    public String execute() {
        String result = "input";
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getType().equals("admin"))) {

            ProductManager productManager = new ProductManagerImpl(user.getUsername(), user.getPassword());
            String productId = productManager.addProduct(name, Double.parseDouble(price), categoryId,
                    details);

            if (productId != null) {
                result = "success";
            }
        }

        return result;
    }

    @Override
    public void validate() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");
        CategoryManager categoryManager = new CategoryManagerImpl(user.getUsername(), user.getPassword());
        this.setCategories(categoryManager.getCategories());
        // Validate name:

        if (getName() == null || getName().length() == 0) {
            addActionError(getText("error.product.name.required"));
        }

        // Validate price:

        if (String.valueOf(getPrice()).length() > 0) {
            if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
                    || Double.parseDouble(getPrice()) < 0.0) {
                addActionError(getText("error.product.price.regex"));
            }
        } else {
            addActionError(getText("error.product.price.required"));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
