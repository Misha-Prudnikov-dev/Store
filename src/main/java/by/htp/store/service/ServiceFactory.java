package by.htp.store.service;

import by.htp.store.service.impl.OrderServiceImpl;
import by.htp.store.service.impl.ProductServiceImpl;
import by.htp.store.service.impl.UserServiceImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();
	private UserService userService = new UserServiceImpl();
	private ProductService productService = new ProductServiceImpl();
	private  OrderService OrderService = new OrderServiceImpl();


	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}
	public ProductService getProductService() {
		return productService;
	}
	public OrderService getOrderService() {
		return OrderService;
	}


}
