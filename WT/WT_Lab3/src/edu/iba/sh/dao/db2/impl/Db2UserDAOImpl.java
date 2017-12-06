package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.User;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.UserDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2UserDAOImpl extends Db2AbstractDAO implements UserDAO {
	


//	private static final String GET_ALL = "SELECT \"USER\",PASSWORD,ROLE FROM LAPUSHA.USERS";
//	private static final String GET_USER = "SELECT \"USER\",PASSWORD,ROLE FROM LAPUSHA.USERS "
//								 + "WHERE \"USER\" = ?";
////	private static final String GET_USER_BY_ID_PASSWORD = "SELECT \"USER\",PASSWORD,ROLE FROM LAPUSHA.USERS " 
////								+ "WHERE \"USER\" = ? AND PASSWORD=?";
//	private static final String ADD_USER = "INSERT INTO LAPUSHA.USERS VALUES(?,?,?)" ;
//	private static final String DELETE_USER_BY_ID = "DELETE FROM LAPUSHA.USERS WHERE \"USER\"=?" ;
//	private static final String UPDATE_USER_BY_ID= "UPDATE  LAPUSHA.USERS SET \"USER\" =?, PASSWORD=?, ROLE=? "
//			+ "WHERE \"USER\"=?" ;
	
	private static final String GET_ALL = "SELECT USER,PASSWORD,ROLE FROM USERS";
	private static final String GET_USER = "SELECT USER,PASSWORD,ROLE FROM USERS "
								 + "WHERE USER = ?";
	private static final String GET_USER_BY_ID_PASSWORD = "SELECT USER,PASSWORD,ROLE FROM USERS "
								+ "WHERE USER = ? AND PASSWORD=?";
	private static final String ADD_USER = "INSERT INTO USERS(User, password, Role) VALUES(?,?,?)" ;
	private static final String DELETE_USER_BY_ID = "DELETE FROM USERS WHERE USER=?" ;
	private static final String UPDATE_USER_BY_ID = "UPDATE  USERS SET USER=?,PASSWORD=?,ROLE=? "
								+ "WHERE USER=?" ;

	@Override
	public List<User> getAllUsers() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();
			List<User> list = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				String role = set.getString("ROLE").toUpperCase();
				user.setRole(role);
				user.setId(set.getString("USER"));
				user.setPassword(set.getString("PASSWORD"));
				list.add(user);
				
			}
			log.info("getAllUsers dao method");
			return list;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(set);
			closeStatement(statement);
			closeConnection(connection);
			}
	}

	@Override
	public User getUserId(String id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_USER);
			statement.setString(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				User user = new User();
				String role = set.getString("ROLE").toUpperCase();
				user.setRole(role);
				user.setId(set.getString("USER"));
				user.setPassword(set.getString("PASSWORD"));
				log.info("getUserById dao method "+"user:"+user.toString());
				return user;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(set);
			closeStatement(statement);
			closeConnection(connection);
			}
	}

	@Override
	public void updateUser(String id, User user) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(UPDATE_USER_BY_ID);
			statement.setString(1, user.getId());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole());
			statement.setString(4, id);
			statement.executeUpdate();
			log.info("upd user dao method");

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	}

	@Override
	public void deleteUserById(String id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			
			statement = connection.prepareStatement(DELETE_USER_BY_ID);
			statement.setString(1, id);
			statement.executeUpdate();
			log.info("delete user dao method");

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	

	}

	@Override
	public void addUser(User user) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_USER);
			statement.setString(1, user.getId());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole());
			statement.executeUpdate();
			log.info("add user dao method");

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	

	}

	@Override
	public User getUserByIdAndPassword(String id, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_USER_BY_ID_PASSWORD);
			statement.setString(1, id);
			statement.setString(2, password);
			set = statement.executeQuery();
			if (set.next()) {
				User user = new User();
				String role = set.getString("ROLE").toUpperCase();
				user.setRole(role);
				user.setId(set.getString("USER"));
				user.setPassword(set.getString("PASSWORD"));
				log.info("getUserByIdPass dao method");
				return user;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(set);
			closeStatement(statement);
			closeConnection(connection);
			}
	}



}
