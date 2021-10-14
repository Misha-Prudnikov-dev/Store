package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.Product;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class GoToMain implements Command {

	private static final String COOKIE_PRODUCT = "productCookie";
	private static final String MAIN_PAGE = "WEB-INF/jsp/main_page.jsp";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<Product> productCookie = new ArrayList<>();

		Cookie[] cookies = request.getCookies();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProductService productService = serviceFactory.getProductService();

		String cookieName = "productId&";
		Product product = null;

		try {
			if (cookies != null) {

				for (Cookie c : cookies) {

					String cookieNameAndValue = cookieName + c.getValue();

					if (cookieNameAndValue.equals(c.getName())) {

						product = productService.getProductById(Integer.parseInt(c.getValue()));

						productCookie.add(product);

					}
				}

			}

			session.setAttribute(COOKIE_PRODUCT, productCookie);

			RequestDispatcher dispatcher = request.getRequestDispatcher(MAIN_PAGE);
			dispatcher.forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}
}
