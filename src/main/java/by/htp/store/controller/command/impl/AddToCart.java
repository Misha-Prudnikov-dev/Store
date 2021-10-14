package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.controller.command.Command;
import by.htp.store.service.OrderService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class AddToCart implements Command {

	private static final String PRODUCT_ID = "productId";
	private static final String QUANTITY_PRODUCT = "quantityProduct";
	private static final String SIZE_PRODUCT = "productSize";

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String ORDER_ID = "id_order";
	private static final String LAST_REQUEST = "lastRequest";

	private static final String SIGN_IN_PAGE = "Controller?command=go_to_Sign_In";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";
	private static final String GO_TO_PRODUCT_INFO = "Controller?command=go_to_product_info&prodictId=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));
		int quantityProduct = Integer.parseInt(request.getParameter(QUANTITY_PRODUCT));
		String size = request.getParameter(SIZE_PRODUCT);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();

		try {

			orderService.addProductToOrder((int) session.getAttribute(ORDER_ID), productId, quantityProduct,size);

			if (session.getAttribute(LAST_REQUEST) != null) {

				response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());
			} else {
				response.sendRedirect(GO_TO_PRODUCT_INFO + productId);
			}

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}
}
