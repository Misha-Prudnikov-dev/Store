package by.htp.store.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.store.bean.UserAuthorized;
import by.htp.store.controller.command.Command;
import by.htp.store.service.ProductService;
import by.htp.store.service.ServiceException;
import by.htp.store.service.ServiceFactory;

public class ProductInfo implements Command {

	private static final String USER_AUTHORIZED = "userAuthorized";
	private static final String PRODUCT_ID = "productId";
	private static final String PRODUCT_INFO = "productInfo";
	private static final String GROUP_IMAGE = "groupImage";
	private static final String GROUP_REVIEW = "groupReview";
	private static final String GROUP_SIZE = "groupSize";
	private static final String RESULT_FAVORITES = "resultFavorites";
	private static final String PRODUCT_INFO_PAGE = "WEB-INF/jsp/ProductInfo.jsp";
	private static final String GO_TO_ERROR_PAGE = "Controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		UserAuthorized userAuthorized = (UserAuthorized) request.getSession().getAttribute(USER_AUTHORIZED);
		int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));

		try {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			ProductService productService = serviceFactory.getProductService();

			Cookie productCookie = new Cookie(PRODUCT_ID + "&" + productId, request.getParameter(PRODUCT_ID));
			response.addCookie(productCookie);

			if (userAuthorized != null) {

				request.setAttribute(RESULT_FAVORITES,productService.checkProductFavorites(userAuthorized.getId(), productId));

			}

			request.setAttribute(PRODUCT_INFO, productService.getProductById(productId));
			request.setAttribute(GROUP_IMAGE, productService.getGroupProductImageByIdProduct(productId));
			request.setAttribute(GROUP_REVIEW, productService.getReviewByIdProduct(productId));
			request.setAttribute(GROUP_SIZE, productService.getGroupProductSize(productId));

			RequestDispatcher dispatcher = request.getRequestDispatcher(PRODUCT_INFO_PAGE);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(GO_TO_ERROR_PAGE);

		}
	}

}
