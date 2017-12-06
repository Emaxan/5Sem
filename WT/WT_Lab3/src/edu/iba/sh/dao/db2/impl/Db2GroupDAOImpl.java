package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.Group;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.GroupDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2GroupDAOImpl extends Db2AbstractDAO implements GroupDAO {

	private static final String GET_ALL = "SELECT GROUP_NUMBER,AVG_MARK FROM LAPUSHA.GROUPS order by GROUP_NUMBER";
	private static final String GET_GROUP_BY_ID = "SELECT GROUP_NUMBER,AVG_MARK FROM LAPUSHA.GROUPS "
			+ "WHERE GROUP_NUMBER = ? ";
	private static final String ADD_GROUP = "INSERT INTO LAPUSHA.GROUPS VALUES(?,?) ";
	private static final String DELETE_GROUP_BY_ID = "DELETE FROM LAPUSHA.GROUPS WHERE GROUP_NUMBER=? ";
	private static final String UPDATE_GROUP_BY_ID = "UPDATE  LAPUSHA.GROUPS SET GROUP_NUMBER=?,AVG_MARK=? "
			+ "WHERE GROUP_NUMBER=? ";

	// private static final String GET_ALL = "SELECT GROUP_NUMBER,AVG_MARK FROM
	// GROUPS";
	// private static final String GET_GROUP_BY_ID = "SELECT
	// GROUP_NUMBER,AVG_MARK FROM GROUPS "
	// + "WHERE GROUP_NUMBER = ?";
	// private static final String ADD_GROUP = "INSERT INTO GROUPS VALUES(?,?)"
	// ;
	// private static final String DELETE_GROUP_BY_ID = "DELETE FROM GROUPS
	// WHERE GROUP_NUMBER=?" ;
	// private static final String UPDATE_GROUP_BY_ID = "UPDATE GROUPS SET
	// GROUP_NUMBER=?,AVG_MARK=? "
	// + "WHERE GROUP_NUMBER=?" ;

	public Db2GroupDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Group> getAllGroups() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();
			List<Group> list = new ArrayList<Group>();
			while (set.next()) {
				Group group = new Group();
				group.setGroupNumber(set.getString("GROUP_NUMBER"));
				group.setAvgMark(set.getDouble("AVG_MARK"));
				list.add(group);
			}
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
	public Group getGroupById(String id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_GROUP_BY_ID);
			statement.setString(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				Group group = new Group();
				group.setAvgMark(set.getDouble("AVG_MARK"));
				group.setGroupNumber(set.getString("GROUP_NUMBER"));
				return group;
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
	public void updateGroup(String id, Group group) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(UPDATE_GROUP_BY_ID);
			statement.setString(1, group.getGroupNumber());
			statement.setDouble(2, group.getAvgMark());
			statement.setString(3, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	@Override
	public void deleteGroupById(String id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(DELETE_GROUP_BY_ID);
			statement.setString(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}

	}

	@Override
	public void addGroup(Group group) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_GROUP);
			statement.setString(1, group.getGroupNumber());
			statement.setDouble(2, group.getAvgMark());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}

	}

}
