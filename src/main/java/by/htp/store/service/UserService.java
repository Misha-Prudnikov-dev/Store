package by.htp.store.service;

import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Role;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;
import by.htp.store.dao.PasswordAlreadyExistsDAOException;

public interface UserService {

	UserAuthorized signIn(String email, String password)throws ServiceException,InvalidInputServiceException;

	UserAuthorized registration(UserInfo userInfo, String userPassword) throws ServiceException,InvalidInputServiceException,PasswordAlreadyExistsServiceException;

	UserInfo changeUserInfo(UserInfo userInfo) throws ServiceException,InvalidInputServiceException;
	
	UserInfo getUserInfo(int id) throws ServiceException;

	void deleteUser(int id) throws ServiceException;

	void addAddress(int userId, Address address) throws ServiceException;
	
	List<Role> getAllRole()throws ServiceException;
	
	void addRole(String roleName)throws ServiceException;
	
	void addRoleUser(int userId,int roleId)throws ServiceException;
	
	void deleteRoleUser(int userId,int roleId)throws ServiceException;

}
