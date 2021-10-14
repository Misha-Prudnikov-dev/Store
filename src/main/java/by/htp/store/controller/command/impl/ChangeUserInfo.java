package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;
import by.htp.store.controller.command.Command;
import by.htp.store.service.InvalidInputServiceException;
import by.htp.store.service.OrderService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;
import by.htp.store.service.UserService;

public class ChangeUserInfo implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String USER_NAME = "name";
	private static final String USER_SURNAME = "surname";
	private static final String USER_PHONE = "phone";
	private static final String USER_EMAIL = "email";
	private static final String USER_DATE_OF_BIRTH = "dateOfBirth";
	private static final String USER_GENDER = "gender";
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private static final String LAST_REQUEST = "lastRequest";

	private static final String CHANGE_USER_INFO_WRONG_DATE = "Controller?command=user_profile&change=wrong_date";
	private static final String CHANGE_USER_INFO_IN_FAIL = "Controller?command=user_profile&change=fail";
	private static final String CHANGE_USER_PROFILE_IN_SUCCESS = "Controller?command=user_profile&change=success";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_sign_In";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);
		UserInfo userInfo = new UserInfo();

		try {

			SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_PATTERN);

			userInfo.setId(userAuthorized.getId());
			userInfo.setName(request.getParameter(USER_NAME));
			userInfo.setSurname(request.getParameter(USER_SURNAME));
			userInfo.setPhone(request.getParameter(USER_PHONE));
			userInfo.setEmail(request.getParameter(USER_EMAIL));
			userInfo.setGender(request.getParameter(USER_GENDER));
			userInfo.setDateOfBirth(sdf.parse(request.getParameter(USER_DATE_OF_BIRTH)));

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			UserService userService = serviceFactory.getUserService();

			userService.changeUserInfo(userInfo);

			response.sendRedirect(CHANGE_USER_PROFILE_IN_SUCCESS);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		} catch (ParseException e) {
			response.sendRedirect(CHANGE_USER_INFO_WRONG_DATE);
		} catch (InvalidInputServiceException e) {
			response.sendRedirect(CHANGE_USER_INFO_IN_FAIL);
		}
	}

}
