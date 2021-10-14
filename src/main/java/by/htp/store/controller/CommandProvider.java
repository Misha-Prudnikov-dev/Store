package by.htp.store.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.store.controller.command.Command;
import by.htp.store.controller.command.CommandName;
import by.htp.store.controller.command.impl.AddProductFavorites;
import by.htp.store.controller.command.impl.AddReviewToProduct;
import by.htp.store.controller.command.impl.AddToCart;
import by.htp.store.controller.command.impl.ChangeLocal;
import by.htp.store.controller.command.impl.ChangeUserInfo;
import by.htp.store.controller.command.impl.Checkout;
import by.htp.store.controller.command.impl.DeleteProductCart;
import by.htp.store.controller.command.impl.DeleteProductFavorites;
import by.htp.store.controller.command.impl.DeleteReviewProduct;
import by.htp.store.controller.command.impl.Favorites;
import by.htp.store.controller.command.impl.GoToCart;
import by.htp.store.controller.command.impl.GoToErrorPage;
import by.htp.store.controller.command.impl.GoToMain;
import by.htp.store.controller.command.impl.GoToRegistration;
import by.htp.store.controller.command.impl.GoToSignIn;
import by.htp.store.controller.command.impl.GroupProduct;
import by.htp.store.controller.command.impl.GroupProductManufacturer;
import by.htp.store.controller.command.impl.OrderHistory;
import by.htp.store.controller.command.impl.OrderInfo;
import by.htp.store.controller.command.impl.ProductInfo;
import by.htp.store.controller.command.impl.Registration;
import by.htp.store.controller.command.impl.SearchProduct;
import by.htp.store.controller.command.impl.SignIn;
import by.htp.store.controller.command.impl.SignOut;
import by.htp.store.controller.command.impl.Subcategory;
import by.htp.store.controller.command.impl.UserProfile;

public class CommandProvider {

	private final Map<CommandName, Command> repository = new HashMap<>();

	CommandProvider() {
		repository.put(CommandName.SIGN_IN, new SignIn());
		repository.put(CommandName.SIGN_OUT, new SignOut());
		repository.put(CommandName.REGISTRATION, new Registration());
		repository.put(CommandName.SUBCATEGORY, new Subcategory());
		repository.put(CommandName.GROUP_PRODUCT_BY_SUBCATEGORY, new GroupProduct());
		repository.put(CommandName.GO_TO_PRODUCT_INFO, new ProductInfo());
		repository.put(CommandName.ADD_REVIEW_TO_PRODUCT, new AddReviewToProduct());
		repository.put(CommandName.GO_TO_SIGN_IN, new GoToSignIn());
		repository.put(CommandName.GO_TO_REGISTRATION, new GoToRegistration());
		repository.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
		repository.put(CommandName.GO_TO_MAIN, new GoToMain());
		
        repository.put(CommandName.ADD_PRODUCT_FAVORITES, new AddProductFavorites());
        repository.put(CommandName.DELETE_PRODUCT_FAVORITES, new DeleteProductFavorites());
        repository.put(CommandName.FAVORITES, new Favorites());
        
		repository.put(CommandName.ADD_TO_CART, new AddToCart());
		repository.put(CommandName.GO_TO_CART, new GoToCart());
        repository.put(CommandName.DELETE_PRODUCT_IN_CART, new DeleteProductCart());
        repository.put(CommandName.GROUP_PRODUCT_MANUFACTURER, new GroupProductManufacturer());
        repository.put(CommandName.USER_PROFILE, new UserProfile());
        repository.put(CommandName.CHANGE_USER_INFO, new ChangeUserInfo());
        repository.put(CommandName.CHECKOUT, new Checkout());
        repository.put(CommandName.ORDER_HISTORY, new OrderHistory());
        repository.put(CommandName.GO_TO_ORDER_INFO, new OrderInfo());
        repository.put(CommandName.DELETE_REVIEW_PRODUCT, new DeleteReviewProduct());
        repository.put(CommandName.CHANGE_LOCAL, new ChangeLocal());
        repository.put(CommandName.SEARCH_PRODUCT, new SearchProduct());



	}

	 Command getCommand(String name) {

		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);

		} catch (IllegalArgumentException | NullPointerException e) {

			command = repository.get(CommandName.WRONG_REQUEST);
		}

		return command;
	}

}
