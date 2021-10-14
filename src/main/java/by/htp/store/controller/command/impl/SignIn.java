package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.InvalidInputServiceException;
import by.htp.store.service.OrderService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;
import by.htp.store.service.UserService;

public class SignIn implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String USER_EMAIL = "email";
	private static final String USER_PASSWORD = "password";
	private static final String ORDER_ID = "id_order";

	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";
	private static final String SIGN_IN_SUCCESS = "Controller?command=go_to_main&signIn=success";
	private static final String SIGN_IN_FAIL = "Controller?command=go_to_sign_in&signIn=fail";
	private static final String SIGN_IN_FAIL_USER_NOT_FOUND = "Controller?command=go_to_sign_in&signIn=not_found";

	private ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private UserService userService = serviceFactory.getUserService();
	private OrderService orderService = serviceFactory.getOrderService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		String email = request.getParameter(USER_EMAIL);
		String password = request.getParameter(USER_PASSWORD);


		
		try {

			UserAuthorized userAuthorized = userService.signIn(email, password);

			if (userAuthorized != null) {

				session.setAttribute(USER_AUTHORIZED, userAuthorized);
				session.setAttribute(ORDER_ID, orderService.getOrderId(userAuthorized.getId()));
				
				response.sendRedirect(SIGN_IN_SUCCESS);

			} else {
				response.sendRedirect(SIGN_IN_FAIL_USER_NOT_FOUND);
			}

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		} catch (InvalidInputServiceException e) {
			response.sendRedirect(SIGN_IN_FAIL);
		}

	}

}
