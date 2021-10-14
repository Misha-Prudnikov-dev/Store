package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.store.controller.command.Command;

public class ChangeLocal implements Command {

	private static final String LOCAL_SESSION = "local";
	private static final String LOCAL_PARAMETER = "local";
	private static final String LAST_REQUEST = "lastRequest";
	private static final String GO_TO_MAIN = "Controller?command=go_to_main";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException   {


		HttpSession session = request.getSession(true);System.out.println(request.getParameter(LOCAL_PARAMETER));
		
		session.setAttribute(LOCAL_SESSION, request.getParameter(LOCAL_PARAMETER));
		
		if(session.getAttribute(LAST_REQUEST)!=null) {
			response.sendRedirect(session.getAttribute(LAST_REQUEST).toString());
		}else {
			response.sendRedirect(GO_TO_MAIN);

		}
	}

}
