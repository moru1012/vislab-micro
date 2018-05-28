package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vislab.model.Product;

import java.util.List;

public interface ProductManager {

	List<Product> getProducts();

	Product getProductById(String id);

	Product getProductByName(String name);

	String addProduct(String name, double price, String categoryId, String details);

	List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);

	void deleteProductsByCategoryId(String categoryId);
	
    boolean deleteProductById(String id);
}
