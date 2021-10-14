package by.htp.store.dao.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.store.bean.Address;
import by.htp.store.bean.Role;
import by.htp.store.bean.UserAuthorized;
import by.htp.store.bean.UserInfo;
import by.htp.store.dao.DAOException;
import by.htp.store.dao.PasswordAlreadyExistsDAOException;
import by.htp.store.dao.UserDAO;
import by.htp.store.dao.connection.ConnectionPool;
import by.htp.store.dao.connection.ConnectionPoolRuntimeException;
import by.htp.store.dao.util.PBKDFHashing;

public class UserDAOImpl implements UserDAO {

	private static final String SQL_TABLE_COLUMN_USER_ID = "id_users";
	private static final String SQL_TABLE_COLUMN_NAME = "name_users";
	private static final String SQL_TABLE_COLUMN_EMAIL = "email_users";
	private static final String SQL_TABLE_COLUMN_SURNAME = "surname_users";
	private static final String SQL_TABLE_COLUMN_PHONE = "phone_users";
	private static final String SQL_TABLE_COLUMN_PASSWORD = "password_users";


	private static final int TABLE_ROLE_ID_USER = 1;
	private static final String TABLE_ROLE_USER = "user";

	private static final String SQL_TABLE_ROLE_ID = "id_roles";
	private static final String SQL_TABLE_ROLE = "role_roles";
	private static final String ACTIVE = "ACTIVE";

	private static final String SQL_TABLE_COLUMN_STATUS = "status_users";
	private static final String SQL_TABLE_COLUMN_DATE_OF_BIRTH = "date_of_birth_users";
	private static final String SQL_TABLE_COLUMN_GENDER = "gender_users";

	private static final String SELECT_EMAIL_AND_PASSWORD = "SELECT * FROM users,roles_has_users,roles "
			+ "WHERE users.email_users=?  AND users.id_users=roles_has_users.users_id_users "
			+ "AND roles_has_users.roles_id_roles=id_roles AND users.status_users=?";

	private static final String SELECT_PASSWORD_USERS = "SELECT * FROM users WHERE password_users=?";

	private static final String INSERT_USER_INFO = "INSERT INTO users(name_users,surname_users,email_users,password_users,phone_users,gender_users,status_users,date_of_birth_users) VALUES(?,?,?,?,?,?,?,?)";

	private static final String INSERT_ROLE = "INSERT INTO roles_has_users(roles_id_roles,users_id_users) VALUES(?,?)";

	private static final String SQL_SELECT_USER_INFO = "SELECT * FROM users,roles_has_users,roles WHERE id_users = ? "
			+ "AND users.id_users=roles_has_users.users_id_users AND roles_has_users.roles_id_roles=id_roles ";

	private static final String UPDATE_USER_INFO = "UPDATE shopdb.users SET shopdb.users.name_users = ?,shopdb.users.surname_users = ?, "
			+ "shopdb.users.phone_users = ?,shopdb.users.gender_users = ?,shopdb.users.email_users = ?,shopdb.users.date_of_birth_users = ? "
			+ " WHERE shopdb.users.id_users = ?";

	@Override
	public UserAuthorized signIn(String email, String password) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		Role role = null;
		UserAuthorized userAuthorized = null;
		List<Role> groupRole = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_EMAIL_AND_PASSWORD);
			ps.setString(1, email);
			ps.setString(2, ACTIVE);

			rs = ps.executeQuery();

			userAuthorized = new UserAuthorized();
			groupRole = new ArrayList<>();

			if (!rs.next()|| !PBKDFHashing.validatePassword(password,rs.getString(SQL_TABLE_COLUMN_PASSWORD) ) ) {
				return null;
			}
			rs.beforeFirst();

			while (rs.next()) {

				userAuthorized.setId(rs.getInt(SQL_TABLE_COLUMN_USER_ID));
				userAuthorized.setName(rs.getString(SQL_TABLE_COLUMN_NAME));
				userAuthorized.setEmail(rs.getString(SQL_TABLE_COLUMN_EMAIL));

				role = new Role();
				role.setId(rs.getInt(SQL_TABLE_ROLE_ID));
				role.setRole(rs.getString(SQL_TABLE_ROLE));

				groupRole.add(role);

			}

			userAuthorized.setRoles(groupRole);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new DAOException(e);
		} catch (InvalidKeySpecException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return userAuthorized;
	}

	@Override
	public UserAuthorized registration(UserInfo userInfo, String userPassword) throws DAOException, PasswordAlreadyExistsDAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		UserAuthorized userAuthorized = null;
		List<Role> roles = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			if (checkPasswordUsed(userPassword)) {
				throw new PasswordAlreadyExistsDAOException();
			}
			
			System.out.println(userPassword);

			ps = connection.prepareStatement(INSERT_USER_INFO, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, userInfo.getName());
			ps.setString(2, userInfo.getSurname());
			ps.setString(3, userInfo.getEmail());
			ps.setString(4, PBKDFHashing.generateStorngPasswordHash(userPassword));
			ps.setString(5, userInfo.getPhone());
			ps.setString(6, userInfo.getGender());
			ps.setString(7, userInfo.getStatus());
			ps.setDate(8, new java.sql.Date(userInfo.getDateOfBirth().getTime()));
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			rs.next();

			int userId = rs.getInt(1);

			ps.close();

			ps = connection.prepareStatement(INSERT_ROLE);

			ps.setInt(1, TABLE_ROLE_ID_USER);
			ps.setInt(2, userId);
			ps.execute();

			userAuthorized = new UserAuthorized();
			roles = new ArrayList<>();

			userAuthorized.setId(userId);
			userAuthorized.setName(userInfo.getName());
			userAuthorized.setEmail(userInfo.getEmail());
			userAuthorized.setRoles(roles);
			userAuthorized.getRoles().add(new Role(TABLE_ROLE_ID_USER, TABLE_ROLE_USER));

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new DAOException(e);
		} catch (InvalidKeySpecException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return userAuthorized;
	}

	@Override
	public boolean checkPasswordUsed(String userPassword) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SELECT_PASSWORD_USERS);

			ps.setString(1, PBKDFHashing.generateStorngPasswordHash(userPassword));
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new DAOException(e);
		} catch (InvalidKeySpecException e) {
			throw new DAOException(e);
		}  finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return false;
	}

	@Override
	public UserInfo changeUserInfo(UserInfo userInfo) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(UPDATE_USER_INFO);

			ps.setString(1, userInfo.getName());
			ps.setString(2, userInfo.getSurname());
			ps.setString(3, userInfo.getPhone());
			ps.setString(4, userInfo.getGender());
			ps.setString(5, userInfo.getEmail());
			ps.setDate(6, new java.sql.Date(userInfo.getDateOfBirth().getTime()));
			ps.setInt(7, userInfo.getId());

			ps.executeUpdate();

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}
		return userInfo;
	}

	@Override
	public UserInfo getUserInfo(int id) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = null;

		Role role = null;

		UserInfo userInfo = new UserInfo();
		List<Role> groupRole = new ArrayList<Role>();

		try {

			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_SELECT_USER_INFO);

			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {

				userInfo.setId(rs.getInt(SQL_TABLE_COLUMN_USER_ID));
				userInfo.setName(rs.getString(SQL_TABLE_COLUMN_NAME));
				userInfo.setSurname(rs.getString(SQL_TABLE_COLUMN_SURNAME));
				userInfo.setEmail(rs.getString(SQL_TABLE_COLUMN_EMAIL));
				userInfo.setPhone(rs.getString(SQL_TABLE_COLUMN_PHONE));
				userInfo.setStatus(rs.getString(SQL_TABLE_COLUMN_STATUS));
				userInfo.setDateOfBirth(rs.getDate(SQL_TABLE_COLUMN_DATE_OF_BIRTH));
				userInfo.setGender(rs.getString(SQL_TABLE_COLUMN_GENDER));

				role = new Role();
				role.setId(rs.getInt(SQL_TABLE_ROLE_ID));
				role.setRole(rs.getString(SQL_TABLE_ROLE));

				groupRole.add(role);

			}
			userInfo.setRoles(groupRole);

		} catch (ConnectionPoolRuntimeException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.closeConnection(connection, ps, rs);
		}

		return userInfo;
	}

	@Override
	public void deleteUser(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAddress(int userId, Address address) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Role> getAllRole() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRole(String roleName) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRoleUser(int userId, int roleId) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRoleUser(int userId, int roleId) throws DAOException {
		// TODO Auto-generated method stub

	}

}
