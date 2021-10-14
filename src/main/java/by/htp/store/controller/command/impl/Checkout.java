package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.Address;
import by.htp.store.bean.Delivery;
import by.htp.store.bean.OrderDetail;
import by.htp.store.bean.Product;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.OrderService;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class Checkout implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String ORDER_ID = "id_order";

	private static final String COUNTRY_DEFULT = "BELARUS";
	private static final String USER_STREET = "street";
	private static final String USER_CITY = "city";
	private static final String USER_NUMBER_HOUSE = "numberHouse";

	private static final String ACTIVE = "ACTIVE";
	private static final String TO_THE_DOOR = "ToTheDoor";
	private static final String TYPE_PAYMENT_USER = "typePayment";
	private static final String STATUS_PAYMENT_USER = "notPayment";

	private static final String STATUS_ORDER_ORDER = "ORDER";

	private static final String CHECOUT_SUCCESS = "&checkout=success&orderId=";
	private static final String CHECKOUT_FAIL = "&checkout=fail";

	private static final String QUABTITY_EXCEEDS = "&idProduct=";
	private static final String PRODUCT_SIZE = "&productSize=";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_sign_in";
	private static final String GO_TO_CART = "Controller?command=go_to_cart";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);
		int orderId = (int) session.getAttribute(ORDER_ID);

		Address address = new Address();
		Delivery delivery = new Delivery();

		String typePayment = request.getParameter(TYPE_PAYMENT_USER);
		
		if(typePayment==null) {
			response.sendRedirect(GO_TO_CART+CHECKOUT_FAIL);
			return;
		}

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		ProductService productService = serviceFactory.getProductService();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 4);

		address.setCountry(COUNTRY_DEFULT);
		address.setCity(request.getParameter(USER_CITY));
		address.setStreet(request.getParameter(USER_STREET));
		address.setNumberHouse(request.getParameter(USER_NUMBER_HOUSE));

		delivery.setStatusDelivery(ACTIVE);
		delivery.setTypeDelivery(TO_THE_DOOR);
		delivery.setDateDelivery(calendar.getTime());

		List<OrderDetail> groupOrderDetail;
		
		try {
			
			groupOrderDetail = orderService.getProductInOrder(orderId);

			for (OrderDetail orderDetail : groupOrderDetail) {

				if (orderDetail.getQuantity() > productService.checkProductQuantiyInStock(orderDetail.getProduct().getId(), orderDetail.getProductSize())) {

					response.sendRedirect(GO_TO_CART + QUABTITY_EXCEEDS + orderDetail.getProduct().getId()+PRODUCT_SIZE+orderDetail.getProductSize());
					return;
				}
			}

			int addressId = orderService.addAddressOrder(address, userAuthorized.getId());
			
			if(addressId==0) {
				response.sendRedirect(GO_TO_CART+CHECKOUT_FAIL);
				return;
			}
			
			int deliveryId = orderService.addDeliveryOrder(delivery, addressId);
			orderService.addPaymentOrder(typePayment, STATUS_PAYMENT_USER, orderId);

			Date dateCreateOrder = new Date();

			for (OrderDetail orderDetail : groupOrderDetail) {

				productService.updateProductQuantity(orderDetail.getProduct().getId(),orderDetail.getQuantity(),orderDetail.getProductSize());

			}

			orderService.checkout(orderId, deliveryId, dateCreateOrder, STATUS_ORDER_ORDER);

			session.setAttribute(ORDER_ID, orderService.createOrder(userAuthorized.getId()));

			response.sendRedirect(GO_TO_CART + CHECOUT_SUCCESS + orderId);

		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(GO_TO_ERROR_PAGE);
		}
	}

}
