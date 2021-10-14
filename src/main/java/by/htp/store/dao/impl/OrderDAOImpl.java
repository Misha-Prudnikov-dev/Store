package by.htp.store.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Delivery;
import by.htp.store.bean.Order;
import by.htp.store.bean.OrderDetail;
import by.htp.store.bean.Payment;
import by.htp.store.bean.Product;
import by.htp.store.bean.ProductImage;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.dao.DAOException;
import by.htp.store.dao.OrderDAO;
import by.htp.store.dao.connection.ConnectionPool;
import by.htp.store.dao.connection.ConnectionPoolRuntimeException;

public class OrderDAOImpl implements OrderDAO {

	private static final String SQL_TABLE_ORDER_ID = "id_orders";
	private static final String SQL_TABLE_ORDER_DATE = "date_orders";
	private static final String SQL_TABLE_ORDER_STATUS = "status_order";

	private static final String STATUS_ORDER_CART = "CART";
	private static final String STATUS_ORDER_ORDER = "ORDER";
	private static final String STATUS_ORDER_BOUGHT = "BOUGHT";

	private static final String SQL_TABLE_ORDER_DETAIL_ID = "id_order_details";
	private static final String SQL_TABLE_ORDER_DETAIL_PRODUCT_QUANTITY = "quantity_order_details";
	private static final String SQL_TABLE_ORDER_DETAIL_PRODUCT_SIZE = "product_size_order_details";

	private static final String SQL_TABLE_PRODUCT_TITLE = "title_products";
	private static final String SQL_TABLE_PRODUCT_ID = "id_products";
	private static final String SQL_TABLE_PRODUCT_DESCRIPTION = "description_products";
	private static final String SQL_TABLE_PRODUCT_COLOR = "color_products";
	private static final String SQL_TABLE_PRODUCT_YEAR = "year_products";
	private static final String SQL_TABLE_PRODUCT_PRICE_UNIT = "price_products";
	private static final String SQL_TABLE_PRODUCT_QUANTITY = "quantity_products";
	private static final String SQL_TABLE_PRODUCT_IMAGE = "image_products";

	private static final String SQL_TABLE_DELIVERY_ID = "id_delivery";
	private static final String SQL_TABLE_DELIVERY_DATE = "date_delivery";
	private static final String SQL_TABLE_DELIVERY_STATUS = "status_delivery";
	private static final String SQL_TABLE_DELIVERY_TYPE = "type_delivery";

	private static final String SQL_TABLE_PAYMENT_TYPE = "type_payment";
	private static final String SQL_TABLE_PAYMENT_STATUS = "status_payment";
	private static final String SQL_TABLE_PAYMENT_DATE = "date_payment";
	private static final String SQL_TABLE_PAYMENT_ID = "id_payment";

	private static final String SQL_TABLE_ADDRESS_ID = "id_addresses";
	private static final String SQL_TABLE_ADDRESS_COUNTRY = "country_addresses";
	private static final String SQL_TABLE_ADDRESS_CITY = "city_addresses";
	private static final String SQL_TABLE_ADDRESS_STREET = "street_addresses";
	private static final String SQL_TABLE_ADDRESS_NUMBER_HOUSE = "numberHouse_addresses";

	private static final String SELECT_ORDER_DETAILS_PRODUCT = "SELECT * FROM shopdb.order_details JOIN shopdb.products "
			+ "ON shopdb.order_details.products_id_products=shopdb.products.id_products  "
			+ "WHERE shopdb.order_details.order_id_order = ? ";

	private static final String SELECT_PRODUCT_IN_ORDER = " SELECT * FROM shopdb.orders WHERE shopdb.orders.users_id_users = ? AND shopdb.orders.status_order=?";

	private static final String DELETE_PRODUCT_ID_IN_ORDER_DETAIL = "DELETE  FROM order_details WHERE products_id_products = ? "
			+ "AND order_id_order = ? AND product_size_order_details=? ";

	private static final String INSERT_PRODUCT_ID_AND_QUANTITY_PRODUCT_AND_ORDER_ID = "INSERT INTO "
			+ "order_details(products_id_products,order_id_order,quantity_order_details,product_size_order_details) VALUES(?,?,?,?) ";

	private static final String INSERT_USER_ID_AND_STATUS = "INSERT INTO orders(users_id_users,status_order) VALUES(?,?)";

	private static final String SELECT_PRODUCT = "SELECT * FROM order_details WHERE order_details.products_id_products=? AND order_details.order_id_order=? "
			+ "AND order_details.product_size_order_details=? ";

	private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE order_details SET quantity_order_details= ? "
			+ "WHERE products_id_products=? AND order_id_order=? AND product_size_order_details=? ";

	private static final String INSERT_USER_ADDRESS = "INSERT INTO addresses"
			+ "(users_id_users,country_addresses,city_addresses,street_addresses,numberHouse_addresses) VALUES(?,?,?,?,?)";

	private static final String INSERT_PAYMENT_ORDER = "INSERT INTO  payment(order_id_order,type_payment,status_payment) VALUES(?,?,?)";

	private static final String INSERT_DELIVERY_ORDER = "INSERT INTO  delivery"
			+ "(date_delivery,type_delivery,addresses_id_addresses,status_delivery_id_status_delivery) VALUES(?,?,?,?)";

	private static final String INSERT_STATUS_DELIVERY = "INSERT INTO status_delivery(status_delivery)VALUES(?)";

	private static final String UPDATE_ORDER = "UPDATE shopdb.orders SET shopdb.orders.status_order = ?, "
			+ "shopdb.orders.date_orders=?,shopdb.orders.delivery_id_delivery=? "
			+ "WHERE shopdb.orders.id_orders = ? ";

	private static final String SELECT_ORDER_HISTORY = "SELECT * FROM  shopdb.orders JOIN shopdb.delivery "
			+ "ON shopdb.orders.delivery_id_delivery=shopdb.delivery.id_delivery "
			+ "JOIN shopdb.status_delivery ON shopdb.delivery.status_delivery_id_status_delivery=shopdb.status_delivery.id_status_delivery "
			+ "WHERE shopdb.orders.users_id_users = ? AND shopdb.orders.status_order != 'CART' ORDER BY shopdb.orders.id_orders DESC";

	private static final String SELECT_ORDER_INFO = "SELECT * FROM shopdb.orders JOIN shopdb.order_details "
			+ "ON shopdb.orders.id_orders=shopdb.order_details.order_id_order JOIN shopdb.products "
			+ "ON shopdb.order_details.products_id_products=shopdb.products.id_products "
			+ "JOIN shopdb.payment ON shopdb.orders.id_orders=shopdb.payment.order_id_order "
			+ "JOIN shopdb.delivery ON shopdb.orders.delivery_id_delivery=shopdb.delivery.id_delivery "
			+ "JOIN shopdb.status_delivery ON shopdb.delivery.status_delivery_id_status_delivery=shopdb.status_delivery.id_status_delivery "
			+ " JOIN shopdb.addresses ON shopdb.delivery.addresses_id_addresses=shopdb.addresses.id_addresses"
			+ " WHERE orders.id_orders= ? AND orders.users_id_users = ? ";

	@Override
	public int createOrder(int userId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		int orderId = 0;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(INSERT_USER_ID_AND_STATUS, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, userId);
			ps.setString(2, STATUS_ORDER_CART);

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			rs.next();

			orderId = rs.getInt(1);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return orderId;
	}

	@Override
	public void addProductToOrder(int orderId, int productId, int quantityProduct,String productSize) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			int result = checkProductInOrder(productId, orderId,productSize);

			if (result != 0) {

				ps = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY);

				ps.setInt(1, result + quantityProduct);
				ps.setInt(2, productId);
				ps.setInt(3, orderId);
				ps.setString(4, productSize);

				ps.executeUpdate();

			} else {

				ps = connection.prepareStatement(INSERT_PRODUCT_ID_AND_QUANTITY_PRODUCT_AND_ORDER_ID);

				ps.setInt(1, productId);
				ps.setInt(2, orderId);
				ps.setInt(3, quantityProduct);
				ps.setString(4, productSize);


				ps.executeUpdate();
			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

	}

	public int checkProductInOrder(int productId, int orderId,String productSize) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_PRODUCT);

			ps.setInt(1, productId);
			ps.setInt(2, orderId);
			ps.setString(3, productSize);
			rs = ps.executeQuery();

			if (rs.next()) {

				return rs.getInt(SQL_TABLE_ORDER_DETAIL_PRODUCT_QUANTITY);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return 0;
	}

	@Override
	public List<OrderDetail> getProductInOrder(int orderId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		List<OrderDetail> groupOrderDetail = new ArrayList<OrderDetail>();

		try {

			OrderDetail orderDetail = null;
			Product product = null;
			ProductImage productImage = null;

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_ORDER_DETAILS_PRODUCT);

			ps.setInt(1, orderId);
			rs = ps.executeQuery();

			if (rs == null) {

				return null;
			}

			while (rs.next()) {

				orderDetail = new OrderDetail();
				product = new Product();
				productImage = new ProductImage();

				orderDetail.setId(rs.getInt(SQL_TABLE_ORDER_DETAIL_ID));
				orderDetail.setQuantity(rs.getInt(SQL_TABLE_ORDER_DETAIL_PRODUCT_QUANTITY));
				orderDetail.setProductSize(rs.getString(SQL_TABLE_ORDER_DETAIL_PRODUCT_SIZE));

				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setColor(rs.getString(SQL_TABLE_PRODUCT_COLOR));				
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));

				productImage.setImage(rs.getString(SQL_TABLE_PRODUCT_IMAGE));

				product.setProductImage(productImage);
				orderDetail.setProduct(product);

				groupOrderDetail.add(orderDetail);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupOrderDetail;
	}

	@Override
	public int getOrderId(int userId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		int orderId = 0;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_PRODUCT_IN_ORDER);
			ps.setInt(1, userId);
			ps.setString(2, STATUS_ORDER_CART);

			rs = ps.executeQuery();

			if (rs == null) {
				return 0;
			}

			while (rs.next()) {
				orderId = rs.getInt(SQL_TABLE_ORDER_ID);
			}

			return orderId;

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
	}

	@Override
	public void removeProductFromOrder(int productId,String productSize, int orderId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(DELETE_PRODUCT_ID_IN_ORDER_DETAIL);

			ps.setInt(1, productId);
			ps.setInt(2, orderId);
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
	public int addAddressOrder(Address address, int userId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		int addressId = 0;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(INSERT_USER_ADDRESS, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, userId);
			ps.setString(2, address.getCountry());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getStreet());
			ps.setString(5, address.getNumberHouse());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			rs.next();

			addressId = rs.getInt(1);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return addressId;

	}

	@Override
	public void addPaymentOrder(String typePayment, String statusPayment, int orderId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(INSERT_PAYMENT_ORDER);

			ps.setInt(1, orderId);
			ps.setString(2, typePayment);
			ps.setString(3, statusPayment);

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

	}

	@SuppressWarnings("resource")
	@Override
	public int addDeliveryOrder(Delivery delivery, int addressId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(INSERT_STATUS_DELIVERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, delivery.getStatusDelivery());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			rs.next();

			int statusDeliveryId = rs.getInt(1);
			ps.close();

			ps = connection.prepareStatement(INSERT_DELIVERY_ORDER, Statement.RETURN_GENERATED_KEYS);

			ps.setDate(1, new java.sql.Date(delivery.getDateDelivery().getTime()));
			ps.setString(2, delivery.getTypeDelivery());
			ps.setInt(3, addressId);
			ps.setInt(4, statusDeliveryId);

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			rs.next();

			int deliveryId = rs.getInt(1);

			return deliveryId;

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
	}

	@Override
	public void checkout(int orderId, int deliveryId, Date dateCreateOrder, String statusOrder) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(UPDATE_ORDER);

			ps.setString(1, statusOrder);
			ps.setDate(2, new java.sql.Date(dateCreateOrder.getTime()));
			ps.setInt(3, deliveryId);
			ps.setInt(4, orderId);

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
	public void checkout(int orderId, int deliveryId, java.util.Date dateCreateOrder, String statusOrder)
			throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(UPDATE_ORDER);

			ps.setString(1, statusOrder);
			ps.setDate(2, new java.sql.Date(dateCreateOrder.getTime()));
			ps.setInt(3, deliveryId);
			ps.setInt(4, orderId);

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
	public List<Order> getOrderHistory(int userId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ConnectionPool connectionPool = null;

		List<Order> groupOrderHistory = new ArrayList<>();
		Order order = null;
		Delivery delivery = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_ORDER_HISTORY);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				order = new Order();
				delivery = new Delivery();

				order.setId(rs.getInt(SQL_TABLE_ORDER_ID));
				order.setDateOrder(rs.getDate(SQL_TABLE_ORDER_DATE));
				order.setStatus(rs.getString(SQL_TABLE_ORDER_STATUS));

				delivery.setId(rs.getInt(SQL_TABLE_DELIVERY_ID));
				delivery.setDateDelivery(rs.getDate(SQL_TABLE_DELIVERY_DATE));

				order.setDelivery(delivery);
				groupOrderHistory.add(order);

			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return groupOrderHistory;
	}

	@Override
	public Order getOrderInfo(int orderId, int userId) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		Order order = new Order();
		Payment payment = new Payment();
		Delivery delivery = new Delivery();
		Address address = new Address();

		OrderDetail orderDetail = null;
		List<OrderDetail> groupOrderDetail = new ArrayList<>();

		Product product = null;
		ProductImage productImage = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_ORDER_INFO);
			ps.setInt(1, orderId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				orderDetail = new OrderDetail();
				product = new Product();
				productImage = new ProductImage();

				order.setId(rs.getInt(SQL_TABLE_ORDER_ID));
				order.setStatus(rs.getString(SQL_TABLE_ORDER_STATUS));
				order.setDateOrder(rs.getDate(SQL_TABLE_ORDER_DATE));

				delivery.setId(rs.getInt(SQL_TABLE_DELIVERY_ID));
				delivery.setDateDelivery(rs.getDate(SQL_TABLE_DELIVERY_DATE));
				delivery.setStatusDelivery(rs.getString(SQL_TABLE_DELIVERY_STATUS));
				delivery.setTypeDelivery(rs.getString(SQL_TABLE_DELIVERY_TYPE));

				payment.setId(rs.getInt(SQL_TABLE_PAYMENT_ID));
				payment.setDatePayment(rs.getDate(SQL_TABLE_PAYMENT_DATE));
				payment.setStatusPayment(rs.getString(SQL_TABLE_PAYMENT_STATUS));
				payment.setTypePayment(rs.getString(SQL_TABLE_PAYMENT_TYPE));

				address.setId(rs.getInt(SQL_TABLE_ADDRESS_ID));
				address.setCountry(rs.getString(SQL_TABLE_ADDRESS_COUNTRY));
				address.setCity(rs.getString(SQL_TABLE_ADDRESS_CITY));
				address.setStreet(rs.getString(SQL_TABLE_ADDRESS_STREET));
				address.setNumberHouse(rs.getString(SQL_TABLE_ADDRESS_NUMBER_HOUSE));

				orderDetail.setId(rs.getInt(SQL_TABLE_ORDER_DETAIL_ID));
				orderDetail.setQuantity(rs.getInt(SQL_TABLE_ORDER_DETAIL_PRODUCT_QUANTITY));
				orderDetail.setProductSize(rs.getString(SQL_TABLE_ORDER_DETAIL_PRODUCT_SIZE));

				product.setId(rs.getInt(SQL_TABLE_PRODUCT_ID));
				product.setDescription(rs.getString(SQL_TABLE_PRODUCT_DESCRIPTION));
				product.setColor(rs.getString(SQL_TABLE_PRODUCT_COLOR));
				product.setTitle(rs.getString(SQL_TABLE_PRODUCT_TITLE));
				product.setPrice(rs.getDouble(SQL_TABLE_PRODUCT_PRICE_UNIT));
				product.setQuantity(rs.getInt(SQL_TABLE_PRODUCT_QUANTITY));
				product.setYear(rs.getInt(SQL_TABLE_PRODUCT_YEAR));

				productImage.setImage(rs.getString(SQL_TABLE_PRODUCT_IMAGE));
				product.setProductImage(productImage);

				orderDetail.setProduct(product);
				groupOrderDetail.add(orderDetail);

			}

			order.setDelivery(delivery);
			order.setPayment(payment);
			order.setOrderDetails(groupOrderDetail);
			order.setAddress(address);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return order;
	}

}
