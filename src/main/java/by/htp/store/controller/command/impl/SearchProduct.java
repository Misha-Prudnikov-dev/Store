package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.store.bean.Criteria;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class SearchProduct implements Command {

	private static final String SEARCH_PRODUCT = "searchProduct";
	private static final String GROUP_PRODUCT = "groupProduct";
	private static final String GROUP_PRODUCT_PAGE = "WEB-INF/jsp/GroupProduct.jsp";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProductService productService = serviceFactory.getProductService();

		String searchLine = request.getParameter(SEARCH_PRODUCT);

		try {

			request.setAttribute(GROUP_PRODUCT, productService.searchProduct(searchLine));

			RequestDispatcher dispatcher = request.getRequestDispatcher(GROUP_PRODUCT_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		}
	}

}
