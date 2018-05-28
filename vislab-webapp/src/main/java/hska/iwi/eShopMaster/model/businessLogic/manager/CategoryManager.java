package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vislab.model.Category;

import java.util.List;

public interface CategoryManager {

	List<Category> getCategories();
	
	Category getCategory(String id);
	
	Category getCategoryByName(String name);
	
	void addCategory(String name);
	
	void delCategory(Category cat);
	
	void delCategoryById(String id);
}
