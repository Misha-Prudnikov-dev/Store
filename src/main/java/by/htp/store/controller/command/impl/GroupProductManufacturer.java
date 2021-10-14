package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class GroupProductManufacturer implements Command {

	private static final String MANUFACTURER = "manufacturer";
	private static final String MANUFACTURER_ID = "manufacturer";
	private static final String GROUP_PRODUCT_MANUFACTURER = "groupProductManufacturer";
	private static final String GROUP_PRODUCT_MANUFACTURER_PAGE = "WEB-INF/jsp/GroupProductManufacturer.jsp";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int manufacturerId = Integer.parseInt(request.getParameter(MANUFACTURER_ID));

		try {
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			ProductService productService = serviceFactory.getProductService();
			
            request.setAttribute(MANUFACTURER, productService.getManufacturerById(manufacturerId));
			request.setAttribute(GROUP_PRODUCT_MANUFACTURER, productService.getProductByManufacturerId(manufacturerId));

			RequestDispatcher dispatcher = request.getRequestDispatcher(GROUP_PRODUCT_MANUFACTURER_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

}
}