package by.htp.store.service;

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
import by.htp.store.service.ServiceException;

public interface ProductService {

	List<Category> getAllCategory()throws ServiceException;
	
	List<Subcategory> getSubcategoryByIdCategory(int idCategory)throws ServiceException;
	
	List<Product> getGroupProductByIdSubcategory(int idSubcategory)throws ServiceException;
	
	Product getProductById(int idProduct)throws ServiceException;
		
	List<ProductImage> getGroupProductImageByIdProduct(int idProduct)throws ServiceException;
	
	List<ProductSize> getGroupProductSize(int idProduct)throws ServiceException;
	
	List<Review> getReviewByIdProduct(int idProduct)throws ServiceException;

	boolean addReviewProduct(Review review,int userId,int productId,String statusReview)throws ServiceException;
	
	boolean addProductFavorites(int userId,int productId)throws ServiceException;
	
	boolean checkProductFavorites(int userId,int productId)throws ServiceException;
	
	void deleteProductFavorites(int userId,int productId)throws ServiceException;
	
	List<Product> getProductFavorites(int userId)throws ServiceException;
	
	List<Product> getProductByManufacturerId(int manufacturerId)throws ServiceException;

	Manufacturer getManufacturerById(int manufacturerId)throws ServiceException;
	
	int checkProductQuantiyInStock(int productId,String productSize)throws ServiceException;
	
	void updateProductQuantity(int productId, int quantityProduct,String productSize) throws ServiceException ;
	
	void deleteReviewProduct(int userId,int productId,String statusReview)throws ServiceException;

	List<Product> searchProduct(String searchLine)throws ServiceException;

}
