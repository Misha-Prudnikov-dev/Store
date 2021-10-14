package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.OrderService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class OrderInfo implements Command {

	private static final String ORDER = "order";
	private static final String ORDER_ID = "orderId";
	private static final String ORDER_INFO_PAGE = "WEB-INF/jsp/OrderInfo.jsp";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_sign_in";
	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;

		}

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();

		int orderId = Integer.parseInt(request.getParameter(ORDER_ID));

		try {

			request.setAttribute(ORDER, orderService.getOrderInfo(orderId, userAuthorized.getId()));

			RequestDispatcher dispatcher = request.getRequestDispatcher(ORDER_INFO_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		}
	}

}
