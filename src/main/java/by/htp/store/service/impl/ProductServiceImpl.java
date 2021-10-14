package by.htp.store.service.impl;

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
import by.htp.store.dao.DAOFactory;
import by.htp.store.dao.ProductDAO;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.validation.Validator;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Category> getAllCategory() throws ServiceException {

		List<Category> groupCategory;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {
			groupCategory = productDAO.getAllCategory();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return groupCategory;
	}

	@Override
	public List<Subcategory> getSubcategoryByIdCategory(int idCategory) throws ServiceException {

		List<Subcategory> groupSubcategory;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {
			groupSubcategory = productDAO.getSubcategoryByIdCategory(idCategory);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}

		return groupSubcategory;
	}

	@Override
	public List<Product> getGroupProductByIdSubcategory(int idSubcategory) throws ServiceException {

		List<Product> groupProduct = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			groupProduct = productDAO.getGroupProductByIdSubcategory(idSubcategory);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}

		return groupProduct;
	}

	@Override
	public Product getProductById(int idProduct) throws ServiceException {

		Product product = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			product = productDAO.getProductById(idProduct);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}

		return product;
	}

	@Override
	public List<ProductImage> getGroupProductImageByIdProduct(int idProduct) throws ServiceException {

		List<ProductImage> groupImage = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			groupImage = productDAO.getGroupProductImageByIdProduct(idProduct);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}
		return groupImage;
	}

	@Override
	public List<ProductSize> getGroupProductSize(int idProduct) throws ServiceException {

		List<ProductSize> groupProductSize = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {
			groupProductSize = productDAO.getGroupProductSize(idProduct);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return groupProductSize;
	}

	@Override
	public List<Review> getReviewByIdProduct(int idProduct) throws ServiceException {

		List<Review> groupReview = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			groupReview = productDAO.getReviewByIdProduct(idProduct);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}
		return groupReview;
	}

	@Override
	public boolean addReviewProduct(Review review, int userId, int productId, String statusReview)
			throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		if (Validator.reviewProductValid(review)) {
			return false;
		}

		try {
			productDAO.addReviewProduct(review, userId, productId, statusReview);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		return true;
	}

	@Override
	public boolean addProductFavorites(int userId, int productId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		boolean result = false;

		try {
			result = productDAO.addProductFavorites(userId, productId);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		return result;

	}

	@Override
	public boolean checkProductFavorites(int userId, int productId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		boolean result = false;

		try {
			result = productDAO.checkProductFavorites(userId, productId);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		return result;
	}

	@Override
	public void deleteProductFavorites(int userId, int productId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {
			productDAO.deleteProductFavorites(userId, productId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Product> getProductFavorites(int userId) throws ServiceException {

		List<Product> getGroupProductFavorites;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			getGroupProductFavorites = productDAO.getProductFavorites(userId);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		return getGroupProductFavorites;
	}

	@Override
	public List<Product> getProductByManufacturerId(int manufacturerId) throws ServiceException {

		List<Product> groupProductManufacturer = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			groupProductManufacturer = productDAO.getProductByManufacturerId(manufacturerId);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}

		return groupProductManufacturer;

	}

	@Override
	public Manufacturer getManufacturerById(int manufacturerId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		Manufacturer manufacturer = null;

		try {

			manufacturer = productDAO.getManufacturerById(manufacturerId);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}

		return manufacturer;
	}

	@Override
	public int checkProductQuantiyInStock(int productId, String productSize) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		int quantityProduct = 0;

		try {

			quantityProduct = productDAO.checkProductQuantiyInStock(productId, productSize);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		return quantityProduct;
	}

	@Override
	public void updateProductQuantity(int productId, int quantityProduct, String productSize) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {
			productDAO.updateProductQuantity(productId, quantityProduct, productSize);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}
	}

	@Override
	public void deleteReviewProduct(int userId, int productId, String statusReview) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		try {

			productDAO.deleteReviewProduct(userId, productId, statusReview);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}

	}

	@Override
	public List<Product> searchProduct(String searchLine) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		ProductDAO productDAO = daoFactory.getProductDAO();

		List<Product> groupProduct;

		try {

			groupProduct = productDAO.searchProduct(searchLine);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return groupProduct;
	}

}
