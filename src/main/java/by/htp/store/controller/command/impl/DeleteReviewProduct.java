package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.Role;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class DeleteReviewProduct implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String PRODUCT_ID = "productId";
	private static final String STATUS_REVIEW = "DELETED";
	private static final String LAST_REQUEST = "lastRequest";
	private static final String USER_ID = "userId";
	private static final String ROLE_ADMIN = "admin";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProductService productService = serviceFactory.getProductService();

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		try {

			for (Role role : userAuthorized.getRoles()) {

				if (!role.getRole().equalsIgnoreCase(ROLE_ADMIN)) {
					response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());
					return;

				}
			}

			productService.deleteReviewProduct(Integer.parseInt(request.getParameter(USER_ID)),
					Integer.parseInt(request.getParameter(PRODUCT_ID)), STATUS_REVIEW);

			response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());

		} catch (NumberFormatException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}

}
