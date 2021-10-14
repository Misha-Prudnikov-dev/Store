package by.htp.store.dao;

import by.htp.store.dao.impl.OrderDAOImpl;
import by.htp.store.dao.impl.ProductDAOImpl;
import by.htp.store.dao.impl.UserDAOImpl;

public class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();

	public static DAOFactory getInstance() {
		return instance;
	}

	private DAOFactory() {

	}

	private UserDAO userDAO = new UserDAOImpl();
	private ProductDAO productDAO = new ProductDAOImpl();
	private OrderDAO orderDAO = new OrderDAOImpl();


	public UserDAO getUserDAO() {
		return userDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}


}
