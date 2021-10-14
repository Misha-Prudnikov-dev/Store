package by.htp.store.controller.command.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import by.htp.store.bean.Review;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class AddReviewToProduct implements Command {

	private static final String PRODUCT_ID = "productId";
	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String TEXT_REVIEW = "text";
	private static final String TYPE_REVIEW = "none";
	private static final String RATING_REVIEW = "rating";
	private static final String RESULT_ADD_REVIEW = "&add_review=";
	private static final String STATUS_REVIEW = "ACTIVE";

	private static final String PRODUCT_INFO_PAGE = "Controller?command=go_to_product_Info&productId=";
	private static final String SIGN_IN_PAGE = "Controller?command=go_to_SignIn";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(USER_AUTHORIZED) == null) {

			response.sendRedirect(SIGN_IN_PAGE);
			return;
		}

		UserAuthorized userAuthorized = (UserAuthorized) session.getAttribute(USER_AUTHORIZED);

		Review review = new Review();
		review.setText(request.getParameter(TEXT_REVIEW));
		review.setType(TYPE_REVIEW);

		boolean result;

		if (request.getParameter(RATING_REVIEW) != null) {
			review.setRating(Byte.parseByte(request.getParameter(RATING_REVIEW)));
		}

		Date dateAddReview = new Date();

		try {

			review.setDateAddReview(dateAddReview);

			int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			ProductService productService = serviceFactory.getProductService();

			result = productService.addReviewProduct(review, userAuthorized.getId(), productId, STATUS_REVIEW);

			response.sendRedirect(PRODUCT_INFO_PAGE + productId + RESULT_ADD_REVIEW + result);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}

	}

}
