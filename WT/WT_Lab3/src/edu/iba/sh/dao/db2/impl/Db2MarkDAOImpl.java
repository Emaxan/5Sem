package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.Mark;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.MarkDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2MarkDAOImpl extends Db2AbstractDAO implements MarkDAO {

	private static final String GET_ALL = "SELECT ID,STUDY_ID,STUDENT_ID,DATE,PROFESSOR_ID,MARK,COMMENTS FROM LAPUSHA.MARKS ";
	private static final String GET_MARK_BY_ID = "SELECT ID,STUDY_ID,STUDENT_ID,DATE,PROFESSOR_ID,MARK,COMMENTS FROM LAPUSHA.MARKS "
			+ "WHERE ID = ? ";
	private static final String ADD_MARK = "INSERT INTO LAPUSHA.MARKS(STUDY_ID,STUDENT_ID,DATE,PROFESSOR_ID,MARK,COMMENTS) "
			+ "VALUES(?,?,?,?,?,?) ";
	private static final String DELETE_MARK_BY_ID = "DELETE FROM LAPUSHA.MARKS WHERE ID=? ";
	private static final String UPDATE_MARK_BY_ID = "UPDATE  LAPUSHA.MARKS SET STUDY_ID=?,STUDENT_ID=?,DATE=?,PROFESSOR_ID=?,MARK=?,COMMENTS=? "
			+ "WHERE ID=? ";

	@Override
	public List<Mark> getAllMarks() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();
			List<Mark> list = new ArrayList<Mark>();
			while (set.next()) {
				Mark mark = new Mark();
				mark.setId(set.getInt("ID"));
				mark.setStudyId(set.getInt("STUDY_ID"));
				mark.setStudentId(set.getInt("STUDENT_ID"));
				mark.setDate(set.getString("DATE"));
				mark.setProfessorId(set.getInt("PROFESSOR_ID"));
				mark.setMark(set.getInt("MARK"));
				mark.setComments(set.getString("COMMENTS"));
				list.add(mark);
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
	public Mark getMarkById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(GET_MARK_BY_ID);
			statement.setInt(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				Mark mark = new Mark();
				mark.setId(set.getInt("ID"));
				mark.setStudyId(set.getInt("STUDY_ID"));
				mark.setStudentId(set.getInt("STUDENT_ID"));
				mark.setDate(set.getString("DATE"));
				mark.setProfessorId(set.getInt("PROFESSOR_ID"));
				mark.setMark(set.getInt("MARK"));
				mark.setComments(set.getString("COMMENTS"));
				return mark;
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
	public void updateMark(int id, Mark mark) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(UPDATE_MARK_BY_ID);
			statement.setInt(1, mark.getStudyId());
			statement.setInt(2, mark.getStudentId());
			statement.setString(3, mark.getDate());
			statement.setInt(4, mark.getProfessorId());
			statement.setDouble(5, mark.getMark());
			statement.setString(6, mark.getComments());
			statement.setInt(7, id);
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	@Override
	public void deleteMarkById(int id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(DELETE_MARK_BY_ID);
			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}

	}

	@Override
	public void addMark(Mark mark) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_MARK);
			statement.setInt(1, mark.getStudyId());
			statement.setInt(2, mark.getStudentId());
			statement.setString(3, mark.getDate());
			statement.setInt(4, mark.getProfessorId());
			statement.setDouble(5, mark.getMark());
			statement.setString(6, mark.getComments());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}

	}

}
