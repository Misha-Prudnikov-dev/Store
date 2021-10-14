package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.controller.command.Command;

public class SignOut implements Command {

	private static final String SESSION_USER = "userAuthorized";
	private static final String LAST_REQUEST = "lastRequest";
	private static final String GO_TO_MAIN = "Controller?command=go_to_main";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_USER);

		if (session.getAttribute(LAST_REQUEST) != null) {

			response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());
		} else {
			response.sendRedirect(GO_TO_MAIN);
		}

	}

}
