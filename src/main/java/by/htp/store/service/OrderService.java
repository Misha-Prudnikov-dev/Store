package by.htp.store.service;

import java.util.Date;
import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Delivery;
import by.htp.store.bean.Order;
import by.htp.store.bean.OrderDetail;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.service.ServiceException;

public interface OrderService {

	int createOrder(int userId) throws ServiceException;

	void addProductToOrder(int orderId, int productId, int quantityProduct,String productSize) throws ServiceException;

	List<OrderDetail> getProductInOrder(int orderId) throws ServiceException;

	int getOrderId(int userId) throws ServiceException;

	void removeProductFromOrder(int productId,String productSize, int orderId) throws ServiceException;

	int addAddressOrder(Address address, int userId) throws ServiceException;

	void checkout(int orderId, int deliveryId, Date dateCreateOrder, String statusOrder) throws ServiceException;

	void addPaymentOrder(String typePayment, String statusPayment, int orderId) throws ServiceException;

	int addDeliveryOrder(Delivery delivery, int addressId) throws ServiceException;

	List<Order> getOrderHistory(int userId) throws ServiceException;

	Order getOrderInfo(int orderId, int userId) throws ServiceException;

}
