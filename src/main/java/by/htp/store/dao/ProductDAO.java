package by.htp.store.dao;

import java.util.List;

import by.htp.store.bean.Category;
import by.htp.store.bean.Criteria;
import by.htp.store.bean.Manufacturer;
import by.htp.store.bean.Product;
import by.htp.store.bean.ProductImage;
import by.htp.store.bean.ProductSize;
import by.htp.store.bean.Review;
import by.htp.store.bean.Subcategory;
import by.htp.store.dao.DAOException;

public interface ProductDAO {

	List<Category> getAllCategory()throws DAOException;
	
	List<Subcategory> getSubcategoryByIdCategory(int idCategory)throws DAOException;
	
	List<Product> getGroupProductByIdSubcategory(int idSubcategory)throws DAOException;
	
	Product getProductById(int idProduct)throws DAOException;
	
	boolean checkProductFavorites(int userId,int productId)throws DAOException;
	
	List<Review> getReviewByIdProduct(int idProduct)throws DAOException;
	
	List<ProductImage> getGroupProductImageByIdProduct(int idProduct)throws DAOException;
	
	List<ProductSize> getGroupProductSize(int idProduct)throws DAOException;
	
	void addReviewProduct(Review review,int userId,int productId,String statusReview)throws DAOException;
	
	boolean addProductFavorites(int userId,int productId)throws DAOException;
	
	void deleteProductFavorites(int userId,int productId)throws DAOException;
	
	List<Product> getProductFavorites(int userId)throws DAOException;
	
	List<Product> getProductByManufacturerId(int manufacturerId)throws DAOException;
	
	Manufacturer getManufacturerById(int manufacturerId)throws DAOException;
	
	int checkProductQuantiyInStock(int productId,String productSize)throws DAOException;
	
	void updateProductQuantity(int productId,int quantityProduct,String productSize)throws DAOException;
	
	void deleteReviewProduct(int userId,int productId,String statusReview)throws DAOException;
	
	List<Product> searchProduct(String searchLine)throws DAOException;
	
	List<Product> recommendBuyWithProduct()throws DAOException;
}
