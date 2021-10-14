package by.htp.store.service.impl;

import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Role;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;
import by.htp.store.dao.DAOException;
import by.htp.store.dao.DAOFactory;
import by.htp.store.dao.PasswordAlreadyExistsDAOException;
import by.htp.store.dao.UserDAO;
import by.htp.store.service.InvalidInputServiceException;
import by.htp.store.service.PasswordAlreadyExistsServiceException;
import by.htp.store.service.ServiceException;
import by.htp.store.service.UserService;
import by.htp.store.service.validation.Validator;

public class UserServiceImpl implements UserService {

	UserAuthorized userAuthorized = null;
	UserInfo userInfo = null;
	DAOFactory daoFactory = DAOFactory.getInstance();
	UserDAO userDAO = daoFactory.getUserDAO();

	@Override
	public UserAuthorized signIn(String email, String password) throws ServiceException, InvalidInputServiceException {

		try {

			if (!Validator.emailAndPasswordValid(email, password)) {
				throw new  InvalidInputServiceException("invalid input data");
			}

			userAuthorized = userDAO.signIn(email, password);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return userAuthorized;
	}

	@Override
	public UserAuthorized registration(UserInfo userInfo, String userPassword)
			throws ServiceException, InvalidInputServiceException, PasswordAlreadyExistsServiceException {

		if (!Validator.userDataValid(userInfo, userPassword)) {

			throw new InvalidInputServiceException("invalid input data");
		}

		try {
			userAuthorized = userDAO.registration(userInfo, userPassword);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (PasswordAlreadyExistsDAOException e) {
			throw new PasswordAlreadyExistsServiceException();
		}

		return userAuthorized;
	}

	@Override
	public UserInfo changeUserInfo(UserInfo userInfo) throws ServiceException, InvalidInputServiceException {

		if (!Validator.userDataValid(userInfo)) {

			throw new InvalidInputServiceException("invalid input data");
		}

		try {
			userInfo = userDAO.changeUserInfo(userInfo);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		return userInfo;
	}

	@Override
	public UserInfo getUserInfo(int id) throws ServiceException {

		try {
			userInfo = userDAO.getUserInfo(id);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		return userInfo;
	}

	@Override
	public void deleteUser(int id) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAddress(int userId, Address address) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Role> getAllRole() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRole(String roleName) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRoleUser(int userId, int roleId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRoleUser(int userId, int roleId) throws ServiceException {
		// TODO Auto-generated method stub

	}

}
