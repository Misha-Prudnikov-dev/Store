package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class AddProductFavorites implements Command {

	private static final String PRODUCT_ID = "productId";
	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String LAST_REQUEST = "lastRequest";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_Sign_In";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}
		
		int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProductService productService = serviceFactory.getProductService();

		try {

			productService.addProductFavorites(userAuthorized.getId(), productId);

			if (session.getAttribute(LAST_REQUEST) != null) {

				response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());
			}

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}

}
