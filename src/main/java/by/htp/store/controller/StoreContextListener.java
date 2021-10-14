package by.htp.store.controller;

import java.net.http.HttpResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class StoreContextListener implements ServletContextListener {

	public static final String GROUP_CATEGORY = "groupCategory";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ServletContext servletContext = sce.getServletContext();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProductService productService = serviceFactory.getProductService();

		try {
			servletContext.setAttribute(GROUP_CATEGORY, productService.getAllCategory());
		} catch (ServiceException e) {
			throw new ControllerRuntimeException(e);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
