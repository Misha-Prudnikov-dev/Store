package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;
import by.htp.store.controller.command.Command;
import by.htp.store.service.InvalidInputServiceException;
import by.htp.store.service.OrderService;
import by.htp.store.service.PasswordAlreadyExistsServiceException;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;
import by.htp.store.service.UserService;

public class Registration implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String USER_PASSWORD = "password";
	private static final String USER_NAME = "name";
	private static final String USER_SURNAME = "surname";
	private static final String USER_PHONE = "phone";
	private static final String USER_EMAIL = "email";
	private static final String USER_DATE_OF_BIRTH = "dateOfBirth";
	private static final String USER_GENDER = "gender";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String ACTIVE = "ACTIVE";
	private static final String ORDER_ID = "id_order";

	private static final String REGISTRATION_IN_SUCCESS = "Controller?command=go_to_main&registration=success";
	private static final String REGISTRATION_IN_FAIL = "Controller?command=go_to_registration&registration=fail";
	private static final String REGISTRATION_WRONG_DATE = "Controller?command=go_to_registration&registration=wrong_date";
	private static final String REGISTRATION_DUPLICATE = "Controller?command=go_to_registration&registration=duplicate";

	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		UserAuthorized userAuthorized = null;
		UserInfo userInfo = new UserInfo();

		try {

			SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_PATTERN);
			Date dateOfBirth = sdf.parse(request.getParameter(USER_DATE_OF_BIRTH));

			userInfo.setName(request.getParameter(USER_NAME));
			userInfo.setSurname(request.getParameter(USER_SURNAME));
			userInfo.setPhone(request.getParameter(USER_PHONE));
			userInfo.setEmail(request.getParameter(USER_EMAIL));
			userInfo.setStatus(ACTIVE);
			userInfo.setGender(request.getParameter(USER_GENDER));
			userInfo.setDateOfBirth(dateOfBirth);

			Integer orderId = null;

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			UserService userService = serviceFactory.getUserService();
			OrderService orderService = serviceFactory.getOrderService();

			userAuthorized = userService.registration(userInfo, request.getParameter(USER_PASSWORD));

			if (userAuthorized != null) {

				orderId = orderService.createOrder(userAuthorized.getId());

				session.setAttribute(USER_AUTHORIZED, userAuthorized);
				session.setAttribute(ORDER_ID, orderId);

				response.sendRedirect(REGISTRATION_IN_SUCCESS);

			} else {
				response.sendRedirect(REGISTRATION_IN_FAIL);
			}

		} catch (ParseException e) {
			response.sendRedirect(REGISTRATION_WRONG_DATE);
		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		} catch (InvalidInputServiceException e) {
			response.sendRedirect(REGISTRATION_IN_FAIL);
		} catch (PasswordAlreadyExistsServiceException e) {
			response.sendRedirect(REGISTRATION_DUPLICATE);
		}
	}

}
