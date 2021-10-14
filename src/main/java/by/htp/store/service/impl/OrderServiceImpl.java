package by.htp.store.service.impl;

import java.util.Date;
import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Delivery;
import by.htp.store.bean.Order;
import by.htp.store.bean.OrderDetail;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.dao.DAOException;
import by.htp.store.dao.DAOFactory;
import by.htp.store.dao.OrderDAO;
import by.htp.store.service.OrderService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.validation.Validator;

public class OrderServiceImpl implements OrderService {

	@Override
	public int createOrder(int userId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		int orderId = 0;

		try {

			orderId = orderDAO.createOrder(userId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orderId;

	}

	@Override
	public void addProductToOrder(int orderId, int productId, int quantityProduct,String productSize) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		try {

			orderDAO.addProductToOrder(orderId, productId, quantityProduct,productSize);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<OrderDetail> getProductInOrder(int orderId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		List<OrderDetail> groupOrderDetail = null;

		try {

			groupOrderDetail = orderDAO.getProductInOrder(orderId);

			return groupOrderDetail;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void removeProductFromOrder(int productId,String productSize, int orderId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		try {
			orderDAO.removeProductFromOrder(productId,productSize, orderId);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}

	}

	@Override
	public int getOrderId(int userId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		int orderId = 0;

		try {
			orderId = orderDAO.getOrderId(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		return orderId;

	}

	@Override
	public int addAddressOrder(Address address, int userId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		int addressId = 0;
		
		if(Validator.addressValid(address)) {
			
			return addressId;
		}

		try {

			addressId = orderDAO.addAddressOrder(address, userId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return addressId;
	}

	@Override
	public void addPaymentOrder(String typePayment, String statusPayment, int orderId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		try {
			orderDAO.addPaymentOrder(typePayment, statusPayment, orderId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int addDeliveryOrder(Delivery delivery, int addressId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		int deliveryId = 0;

		try {

			deliveryId = orderDAO.addDeliveryOrder(delivery, addressId);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		return deliveryId;

	}

	@Override
	public void checkout(int orderId, int deliveryId, Date dateCreateOrder, String statusOrder)
			throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		try {

			orderDAO.checkout(orderId, deliveryId, dateCreateOrder, statusOrder);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> getOrderHistory(int userId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		List<Order> groupOrderHistory = null;

		try {
			groupOrderHistory = orderDAO.getOrderHistory(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return groupOrderHistory;
	}

	@Override
	public Order getOrderInfo(int orderId, int userId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		OrderDAO orderDAO = daoFactory.getOrderDAO();

		Order order = null;

		try {

			order = orderDAO.getOrderInfo(orderId, userId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return order;
	}

}
