package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;
import by.htp.store.service.UserService;

public class UserProfile implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String USER_INFO = "userInfo";
	private static final String USER_PROFILE_PAGE = "WEB-INF/jsp/UserProfile.jsp";
	private static final String GO_TO_SIGN_IN_PAGE = "Controller?command=go_to_sign_in";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		if (userAuthorized == null) {

			response.sendRedirect(GO_TO_SIGN_IN_PAGE);
			return;
		}

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {

			request.setAttribute(USER_INFO, userService.getUserInfo(userAuthorized.getId()));

			RequestDispatcher dispatcher = request.getRequestDispatcher(USER_PROFILE_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);
		}

	}

}
