package by.htp.store.dao;

import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Role;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;

public interface UserDAO {

	UserAuthorized signIn(String email, String password) throws DAOException;

	UserAuthorized registration(UserInfo userInfo, String userPassword) throws DAOException,PasswordAlreadyExistsDAOException;

	boolean checkPasswordUsed(String userPassword) throws DAOException;

	UserInfo changeUserInfo(UserInfo userInfo) throws DAOException;

	UserInfo getUserInfo(int id) throws DAOException;

	void deleteUser(int id) throws DAOException;

	void addAddress(int userId, Address address) throws DAOException;
	
	List<Role> getAllRole()throws DAOException;
	
	void addRole(String roleName)throws DAOException;
	
	void addRoleUser(int userId,int roleId)throws DAOException;
	
	void deleteRoleUser(int userId,int roleId)throws DAOException;

}
