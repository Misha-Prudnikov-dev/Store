package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class Favorites implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String GROUP_PRODUCT_FAVORITES = "groupProductFavorites";
	private static final String FAVORITES_PAGE = "WEB-INF/jsp/Favorites.jsp";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_sign_in";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		try {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			ProductService productService = serviceFactory.getProductService();

			request.setAttribute(GROUP_PRODUCT_FAVORITES, productService.getProductFavorites(userAuthorized.getId()));

			RequestDispatcher dispatcher = request.getRequestDispatcher(FAVORITES_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}
	}

}
