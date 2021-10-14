package by.htp.store.dao;

import java.util.Date;
import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Delivery;
import by.htp.store.bean.Order;
import by.htp.store.bean.OrderDetail;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.dao.DAOException;

public interface OrderDAO {

	int createOrder(int userId) throws DAOException;

	void addProductToOrder(int orderId, int productId, int quantityProduct,String productSize) throws DAOException;

	List<OrderDetail> getProductInOrder(int orderId) throws DAOException;

	int getOrderId(int userId) throws DAOException;

	void removeProductFromOrder(int productId,String productSize, int orderId) throws DAOException;

	int addAddressOrder(Address address, int userId) throws DAOException;

	void addPaymentOrder(String typePayment, String statusPayment, int orderId) throws DAOException;

	int addDeliveryOrder(Delivery delivery, int addressId) throws DAOException;

	void checkout(int orderId, int deliveryId, Date dateCreateOrder, String statusOrder) throws DAOException;

	void checkout(int orderId, int deliveryId, java.sql.Date dateCreateOrder, String statusOrder) throws DAOException;

	List<Order> getOrderHistory(int userId) throws DAOException;

	Order getOrderInfo(int orderId, int userId) throws DAOException;

}
