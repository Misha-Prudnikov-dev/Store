package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class GoToCart implements Command {

	private static final String ORDER_ID = "id_order";
	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String GROUP_PRODUCT_IN_CART = "groupProductCart";
	private static final String DATE_DELIVERY = "dateDelivery";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String CART_PAGE = "WEB-INF/jsp/Cart.jsp";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_sign_in";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();

		try {

			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 4);

			request.setAttribute(DATE_DELIVERY, (String) (sdf.format(calendar.getTime())));
			session.setAttribute(GROUP_PRODUCT_IN_CART,orderService.getProductInOrder((int) session.getAttribute(ORDER_ID)));

			RequestDispatcher dispatcher = request.getRequestDispatcher(CART_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}

}
