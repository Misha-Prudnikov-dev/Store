package by.htp.store.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.htp.store.bean.Category;
import by.htp.store.bean.Manufacturer;
import by.htp.store.bean.Product;
import by.htp.store.bean.ProductImage;
import by.htp.store.bean.ProductSize;
import by.htp.store.bean.Review;
import by.htp.store.bean.Subcategory;
import by.htp.store.dao.DAOException;
import by.htp.store.dao.ProductDAO;
import by.htp.store.dao.connection.ConnectionPool;
import by.htp.store.dao.connection.ConnectionPoolRuntimeException;
import by.htp.store.dao.util.Stemmer;

public class ProductDAOImpl implements ProductDAO {

	private static final String SQL_TABLE_CATEGORY_ID = "id_categories";
	private static final String SQL_TABLE_CATEGORY_TITLE = "title_categories";

	private static final String SQL_TABLE_SUBCATEGORY_ID = "id_subcategories";
	private static final String SQL_TABLE_SUBCATEGORY_TITLE = "title_subcategories";

	private static final String SQL_TABLE_COLUMN_USER_ID = "id_users";
	private static final String SQL_TABLE_COLUMN_NAME = "name_users";

	private static final String SQL_TABLE_PRODUCT_TITLE = "title_products";
	private static final String SQL_TABLE_PRODUCT_ID = "id_products";
	private static final String SQL_TABLE_PRODUCT_DESCRIPTION = "description_products";
	private static final String SQL_TABLE_PRODUCT_COLOR = "color_products";
	private static final String SQL_TABLE_PRODUCT_YEAR = "year_products";
	private static final String SQL_TABLE_PRODUCT_PRICE_UNIT = "price_products";
	private static final String SQL_TABLE_PRODUCT_QUANTITY = "quantity_products";
	private static final String SQL_TABLE_PRODUCT_DATE_ADD = "date_add_products";
	private static final String SQL_TABLE_PRODUCT_RATING = "RATING";
	private static final String SQL_TABLE_IMAGE_PRODUCT = "image_products";

	private static final String SQL_TABLE_REVIEW_TEXT = "text_reviews";
	private static final String SQL_TABLE_REVIEW_TYPE = "type_reviews";
	private static final String SQL_TABLE_REVIEW_ADD_DATE = "date_reviews";
	private static final String SQL_TABLE_REVIEW_RATING = "rating_reviews";

	private static final String SQL_TABLE_MANUFACTURER_COUNTRY = "country_manufacturer";
	private static final String SQL_TABLE_MANUFACTURER_TITLE = "title_manufacturer";
	private static final String SQL_TABLE_MANUFACTURER_ID = "id_manufacturer";
	private static final String SQL_TABLE_MANUFACTURER_DESCRIPTION = "description_manufacturer";

	private static final String SQL_TABLE_PRODUCT_SIZE_ID = "id_product_sizes";
	private static final String SQL_TABLE_PRODUCT_SIZE_SIZE = "size_product_sizes";
	private static final String SQL_TABLE_PRODUCT_SUZE_QUANTITY = "quantity_product_sizes";

	private static final String SQL_TABLE_PRODUCT_IMAGE_ID = "id_product_image";
	private static final String SQL_TABLE_PRODUCT_IMAGE = "image_product_image";
	private static final String SQL_TABLE_PRODUCTS_IMAGE = "image_products";

	private static final String SQL_SELECT_CATEGORY = "SELECT * FROM categories";

	private static final String SQL_SELECT_SUBCATEGORY = "SELECT * FROM subcategories where categories_id_categories=?";

	private static final String SQL_SELECT_GROUP_PRODUCT_BY_MANUFACTURER = "SELECT * ,"
			+ "(select IFNULL(AVG(rating_reviews),0) from shopdb.reviews  where reviews.products_id_products=shopdb.products.id_products) AS RATING  "
			+ "FROM products,manufacturer "
			+ "WHERE shopdb.manufacturer.id_manufacturer= ? AND shopdb.manufacturer.id_manufacturer=shopdb.products.manufacturer_id_manufacturer ";

	private static final String SQL_SELECT_MANUFACTURER = "SELECT * FROM manufacturer WHERE id_manufacturer= ? ";

	private static final String SQL_SELECT_PRODUCT_INFO = "SELECT *,"
			+ "(select IFNULL(AVG(rating_reviews),0) from shopdb.reviews  where reviews.products_id_products=shopdb.products.id_products) AS RATING  "
			+ " FROM shopdb.products "
			+ "JOIN manufacturer ON shopdb.manufacturer.id_manufacturer=shopdb.products.manufacturer_id_manufacturer "
			+ "JOIN shopdb.subcategories ON shopdb.products.subcategories_id_subcategories=shopdb.subcategories.id_subcategories "
			+ "WHERE   products.id_products= ? ";

	private static final String SQL_SELECT_GROUP_PRODUCT = "SELECT  * , "
			+ "(select IFNULL(AVG(rating_reviews),0) from shopdb.reviews  where reviews.products_id_products=shopdb.products.id_products) AS RATING  "
			+ "FROM shopdb.products " + "WHERE shopdb.products.subcategories_id_subcategories =? ";

	private static final String SQL_SELECT_REVIEWS = "SELECT * FROM shopdb.reviews,shopdb.users "
			+ "WHERE shopdb.reviews.products_id_products=? AND shopdb.users.id_users=shopdb.reviews.users_id_users AND shopdb.reviews.status_reviews='ACTIVE' ORDER BY shopdb.reviews.date_reviews DESC";

	private static final String SQL_INSERT_ADD_REVIEW = "INSERT INTO reviews(type_reviews,text_reviews,rating_reviews,date_reviews,users_id_users,products_id_products,status_reviews) VALUES(?,?,?,?,?,?,?)";

	private static final String SQL_SELECT_IMAGES = "SELECT * FROM shopdb.product_image"
			+ " WHERE shopdb.product_image.products_id_products= ?";

	private static final String INSERT_PRODUCT_ID_USER_ID = "INSERT INTO favorites(users_id_users,products_id_products) VALUE(?,?)";

	private static final String SQL_SELECT_FAVORITES_USER_ID = "SELECT * , "
			+ "(select IFNULL(AVG(rating_reviews),0) from shopdb.reviews  where reviews.products_id_products=shopdb.products.id_products) AS RATING  "
			+ "FROM shopdb.favorites JOIN shopdb.products "
			+ "ON shopdb.favorites.products_id_products=shopdb.products.id_products " + "WHERE  users_id_users = ? ";

	private static final String DELETE_PRODUCT_IN_FAVORITES = "DELETE  FROM favorites WHERE products_id_products = ? AND users_id_users = ?";

	private static final String SQL_SELECT_FAVORITES = "SELECT * FROM favorites WHERE users_id_users= ? AND products_id_products = ? ";

	private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE shopdb.product_sizes "
			+ "SET shopdb.product_sizes.quantity_product_sizes = quantity_product_sizes - ? "
			+ "WHERE shopdb.product_sizes.products_id_products=? AND shopdb.product_sizes.size_product_sizes =? ";

	private static final String SQL_TABLE_SUBCATEGORY_IMAGE = "image_subcategories";

	private static final String SQL_DELETE_REVIEW_PRODUCT = "UPDATE shopdb.reviews SET shopdb.reviews.status_reviews = ? "
			+ "WHERE shopdb.reviews.users_id_users = ? AND shopdb.reviews.products_id_products = ? ";

	private static final String SQL_SELECT_PRODUCT_SIZE = "SELECT * FROM shopdb.product_sizes WHERE products_id_products=? ";

	private static final String SQL_SELECT_PRODUCT_SIZE_QUANTITY = "SELECT quantity_product_sizes FROM product_sizes "
			+ "WHERE products_id_products= ?  AND size_product_sizes=? ";

	private static final String SQL_SEARCH_PRODUCT = "SELECT *,"
			+ "(select IFNULL(AVG(rating_reviews),0) from shopdb.reviews  where reviews.products_id_products=shopdb.products.id_products) AS RATING  "
			+ " FROM shopdb.products "
			+ "WHERE (MATCH(products.title_products,products.color_products) AGAINST ((+?) IN BOOLEAN MODE)) ";
 
	private static final String SQL_SELECT_RECOMMEND = null;

	@Override
	public List<Category> getAllCategory() throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		List<Category> groupCategory = null;
		Category category = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_CATEGORY);
			rs = ps.executeQuery();

			groupCategory = new ArrayList<>();

			while (rs.next()) {

				category = new Category();
				category.setId(rs.getInt(SQL_TABLE_CATEGORY_ID));
				category.setTitle(rs.getString(SQL_TABLE_CATEGORY_TITLE));

				groupCategory.add(category);
			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupCategory;
	}

	@Override
	public List<Subcategory> getSubcategoryByIdCategory(int idCategory) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		List<Subcategory> groupSubcategory = null;
		Subcategory subcategory = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_SUBCATEGORY);
			ps.setInt(1, idCategory);
			rs = ps.executeQuery();

			groupSubcategory = new ArrayList<>();

			while (rs.next()) {

				subcategory = new Subcategory();
				subcategory.setId(rs.getInt(SQL_TABLE_SUBCATEGORY_ID));
				subcategory.setTitle(rs.getString(SQL_TABLE_SUBCATEGORY_TITLE));
				subcategory.setImage(rs.getString(SQL_TABLE_SUBCATEGORY_IMAGE));

				groupSubcategory.add(subcategory);
			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupSubcategory;
	}

	@Override
	public List<Product> getGroupProductByIdSubcategory(int idSubcategory) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Product product = null;
		ProductImage productImage = null;
		List<Product> groupProduct = new ArrayList<>();

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_GROUP_PRODUCT);

			ps.setInt(1, idSubcategory);
			rs = ps.executeQuery();

			while (rs.next()) {

				product = new Product();
				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setDateAddProduct(rs.getDate(SQL_TABLE_PRODUCT_DATE_ADD));

				product.setRating(rs.getDouble(SQL_TABLE_PRODUCT_RATING));

				productImage = new ProductImage();
				productImage.setImage(rs.getString(SQL_TABLE_IMAGE_PRODUCT));

				product.setProductImage(productImage);

				groupProduct.add(product);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupProduct;
	}

	@Override
	public Product getProductById(int idProduct) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Subcategory subcategory = null;
		Product product = null;
		Manufacturer manufacturer = null;
		ProductImage productImage = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_PRODUCT_INFO);

			ps.setInt(1, idProduct);

			rs = ps.executeQuery();

			subcategory = new Subcategory();
			product = new Product();
			manufacturer = new Manufacturer();
			productImage = new ProductImage();

			while (rs.next()) {

				subcategory.setId(rs.getInt(SQL_TABLE_SUBCATEGORY_ID));
				subcategory.setTitle(rs.getString(SQL_TABLE_SUBCATEGORY_TITLE));

				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setColor(rs.getString(SQL_TABLE_PRODUCT_COLOR));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setDateAddProduct(rs.getDate(SQL_TABLE_PRODUCT_DATE_ADD));
				product.setRating(rs.getDouble(SQL_TABLE_PRODUCT_RATING));

				manufacturer.setId(rs.getInt(SQL_TABLE_MANUFACTURER_ID));
				manufacturer.setTitle(rs.getString(SQL_TABLE_MANUFACTURER_TITLE));
				manufacturer.setDescription(rs.getString(SQL_TABLE_MANUFACTURER_DESCRIPTION));
				manufacturer.setCountry(rs.getString(SQL_TABLE_MANUFACTURER_COUNTRY));

				productImage.setImage(rs.getString(SQL_TABLE_PRODUCTS_IMAGE));

				product.setProductImage(productImage);

				product.setManufacturer(manufacturer);
				product.setSubcategory(subcategory);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return product;
	}

	@Override
	public List<ProductImage> getGroupProductImageByIdProduct(int idProduct) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		ProductImage productImage = null;
		List<ProductImage> groupProductImage = new ArrayList<>();

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_IMAGES);
			ps.setInt(1, idProduct);
			rs = ps.executeQuery();

			while (rs.next()) {

				productImage = new ProductImage();
				productImage.setId(rs.getInt(SQL_TABLE_PRODUCT_IMAGE_ID));
				productImage.setImage(rs.getString(SQL_TABLE_PRODUCT_IMAGE));
				groupProductImage.add(productImage);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupProductImage;
	}

	@Override
	public List<ProductSize> getGroupProductSize(int idProduct) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		ProductSize productSize = null;
		List<ProductSize> groupProductSize = new ArrayList<ProductSize>();

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_PRODUCT_SIZE);
			ps.setInt(1, idProduct);

			rs = ps.executeQuery();

			while (rs.next()) {

				productSize = new ProductSize();
				productSize.setId(rs.getInt(SQL_TABLE_PRODUCT_SIZE_ID));
				productSize.setSize(rs.getString(SQL_TABLE_PRODUCT_SIZE_SIZE));
				productSize.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_SUZE_QUANTITY));

				groupProductSize.add(productSize);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupProductSize;
	}

	@Override
	public List<Review> getReviewByIdProduct(int idProduct) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Review review = null;
		List<Review> groupReview = new ArrayList<>();

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_REVIEWS);
			ps.setInt(1, idProduct);
			rs = ps.executeQuery();

			while (rs.next()) {

				review = new Review();
				review.setUserId(rs.getInt(SQL_TABLE_COLUMN_USER_ID));
				review.setUserName(rs.getString(SQL_TABLE_COLUMN_NAME));
				review.setText(rs.getString(SQL_TABLE_REVIEW_TEXT));
				review.setType(rs.getString(SQL_TABLE_REVIEW_TYPE));
				review.setRating(rs.getByte(SQL_TABLE_REVIEW_RATING));
				review.setDateAddReview(rs.getDate(SQL_TABLE_REVIEW_ADD_DATE));

				groupReview.add(review);
			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupReview;
	}

	@Override
	public void addReviewProduct(Review review, int userId, int productId, String statusReview) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool сonnectionPool = null;

		try {

			сonnectionPool = ConnectionPool.getInstance();
			connection = сonnectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_INSERT_ADD_REVIEW);

			ps.setString(1, review.getType());
			ps.setString(2, review.getText());
			ps.setByte(3, review.getRating());
			ps.setDate(4, new java.sql.Date(review.getDateAddReview().getTime()));
			ps.setInt(5, userId);
			ps.setInt(6, productId);
			ps.setString(7, statusReview);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			сonnectionPool.closeConnection(connection, ps, rs);
		}

	}

	@Override
	public boolean addProductFavorites(int userId, int productId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			if (checkProductFavorites(userId, productId)) {

				return true;
			}

			ps = connection.prepareStatement(INSERT_PRODUCT_ID_USER_ID);

			ps.setInt(1, userId);
			ps.setInt(2, productId);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return false;
	}

	@Override
	public boolean checkProductFavorites(int userId, int productId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_FAVORITES);

			ps.setInt(1, userId);
			ps.setInt(2, productId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return false;
	}

	@Override
	public void deleteProductFavorites(int userId, int productId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(DELETE_PRODUCT_IN_FAVORITES);

			ps.setInt(1, productId);
			ps.setInt(2, userId);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
	}

	@Override
	public List<Product> getProductFavorites(int userId) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Product product = null;
		List<Product> groupProductFavorites = new ArrayList<Product>();

		try {

			ProductImage productImage = null;

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_FAVORITES_USER_ID);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				product = new Product();
				productImage = new ProductImage();
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setRating(rs.getDouble(SQL_TABLE_PRODUCT_RATING));

				productImage.setImage(rs.getString(SQL_TABLE_IMAGE_PRODUCT));
				product.setProductImage(productImage);

				groupProductFavorites.add(product);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupProductFavorites;
	}

	@Override
	public List<Product> getProductByManufacturerId(int manufacturerId) throws DAOException {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Product product = null;
		List<Product> groupProductManufacturer = new ArrayList<Product>();

		try {

			ProductImage productImage = null;

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_GROUP_PRODUCT_BY_MANUFACTURER);

			ps.setInt(1, manufacturerId);

			rs = ps.executeQuery();

			while (rs.next()) {

				product = new Product();
				productImage = new ProductImage();

				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setRating(rs.getDouble(SQL_TABLE_PRODUCT_RATING));

				productImage.setImage(rs.getString(SQL_TABLE_IMAGE_PRODUCT));
				product.setProductImage(productImage);

				groupProductManufacturer.add(product);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupProductManufacturer;
	}

	@Override
	public Manufacturer getManufacturerById(int manufacturerId) throws DAOException {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		Manufacturer manufacturer = new Manufacturer();

		try {

			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(SQL_SELECT_MANUFACTURER);

			ps.setInt(1, manufacturerId);

			rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}

			manufacturer.setId(rs.getInt(SQL_TABLE_MANUFACTURER_ID));
			manufacturer.setTitle(rs.getString(SQL_TABLE_MANUFACTURER_TITLE));
			manufacturer.setDescription(rs.getString(SQL_TABLE_MANUFACTURER_DESCRIPTION));
			manufacturer.setCountry(rs.getString(SQL_TABLE_MANUFACTURER_COUNTRY));

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {

			connectionPool.closeConnection(con, ps, rs);
		}

		return manufacturer;
	}

	@Override
	public int checkProductQuantiyInStock(int productId, String productSize) throws DAOException {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(SQL_SELECT_PRODUCT_SIZE_QUANTITY);

			ps.setInt(1, productId);
			ps.setString(2, productSize);

			rs = ps.executeQuery();

			rs.next();

			return rs.getInt(SQL_TABLE_PRODUCT_SUZE_QUANTITY);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {

			connectionPool.closeConnection(con, ps, rs);
		}

	}

	@Override
	public void updateProductQuantity(int productId, int quantityProduct, String productSize) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY);

			ps.setInt(1, quantityProduct);
			ps.setInt(2, productId);
			ps.setString(3, productSize);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

	}

	@Override
	public void deleteReviewProduct(int userId, int productId, String statusReview) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_DELETE_REVIEW_PRODUCT);

			ps.setString(1, statusReview);
			ps.setInt(2, userId);
			ps.setInt(3, productId);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

	}
	
	@Override
	public List<Product> searchProduct(String searchLine) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		Product product = null;
		ProductImage productImage = null;
		List<Product> groupProduct = new ArrayList<Product>();


		Stemmer stemmer = new Stemmer();
		searchLine=stemmer.prepareLine(searchLine);

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SEARCH_PRODUCT);

			ps.setString(1, "'+" + searchLine + "*'");

			rs = ps.executeQuery();

			while (rs.next()) {


				product = new Product();
				productImage = new ProductImage();
				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setDateAddProduct(rs.getDate(SQL_TABLE_PRODUCT_DATE_ADD));

				product.setRating(rs.getDouble(SQL_TABLE_PRODUCT_RATING));

				productImage.setImage(rs.getString(SQL_TABLE_IMAGE_PRODUCT));

				product.setProductImage(productImage);
				groupProduct.add(product);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return groupProduct;
	}

	@Override
	public List<Product> recommendBuyWithProduct() throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_RECOMMEND);
		} catch (SQLException e) {
			throw new DAOException();
		}

		return null;
	}

}
