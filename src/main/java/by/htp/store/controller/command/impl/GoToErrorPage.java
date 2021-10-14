package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.store.controller.command.Command;

public class GoToErrorPage implements Command {

	private static final String ERROR_PAGE = "WEB-INF/jsp/errorPage.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
		dispatcher.forward(request, response);
	}

}
