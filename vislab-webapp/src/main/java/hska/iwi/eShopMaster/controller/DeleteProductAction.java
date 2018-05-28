package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;

import java.util.Map;

public class DeleteProductAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3666796923937616729L;

    private String id;

    @Override
    public String execute() {

        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getType().equals("admin"))) {

            ProductManager productManager = new ProductManagerImpl();
            boolean b = productManager.deleteProductById(id);
            if (b) {
                res = "success";
            }
        }

        return res;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
