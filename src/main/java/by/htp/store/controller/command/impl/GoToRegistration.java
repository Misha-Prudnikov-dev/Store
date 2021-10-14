package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.store.controller.command.Command;

public class GoToRegistration implements Command {

	private static final String REGISTRATION_PAGE = "WEB-INF/jsp/Registration.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE);
		dispatcher.forward(request, response);
	}

}
