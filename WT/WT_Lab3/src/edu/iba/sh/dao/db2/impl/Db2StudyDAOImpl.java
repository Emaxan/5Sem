package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.Study;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.StudyDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2StudyDAOImpl extends Db2AbstractDAO implements StudyDAO {
	
	private static final String GET_ALL = "SELECT ID,NAME,HOURS,PROFESSOR_ID,AVG_MARK FROM LAPUSHA.STUDIES ";
	private static final String GET_STUDY = "SELECT ID,NAME,HOURS,PROFESSOR_ID,AVG_MARK FROM LAPUSHA.STUDIES "
								 + "WHERE ID = ?";
	private static final String GET_STUDY_BY_NAME= "SELECT ID,NAME,HOURS,PROFESSOR_ID,AVG_MARK FROM LAPUSHA.STUDIES " 
								+ "WHERE NAME= ?";
	private static final String ADD_STUDY = "INSERT INTO LAPUSHA.STUDIES(NAME,HOURS,PROFESSOR_ID,AVG_MARK) "
								+ " VALUES(?,?,?,?)" ;
	private static final String DELETE_STUDY_BY_ID = "DELETE FROM LAPUSHA.STUDIES WHERE ID=?" ;
	private static final String UPDATE_STUDY_BY_ID = "UPDATE LAPUSHA.STUDIES SET NAME=?,HOURS=?,PROFESSOR_ID=?,AVG_MARK=? "
								+ "WHERE ID=?" ;

	public Db2StudyDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Study> getAllStudies() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();

			List<Study> list = new ArrayList<Study>();
			while (set.next()) {
				Study study = new Study();
				study.setId(set.getInt("ID"));
				study.setName(set.getString("NAME"));
				study.setHours(set.getDouble("HOURS"));
				study.setProfessorId(set.getInt("PROFESSOR_ID"));
				study.setAvgMark(set.getDouble("AVG_MARK"));
				
				list.add(study);
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
	public Study getStudyById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_STUDY);
			statement.setInt(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				Study study = new Study();
				study.setId(set.getInt("ID"));
				study.setName(set.getString("NAME"));
				study.setHours(set.getDouble("HOURS"));
				study.setProfessorId(set.getInt("PROFESSOR_ID"));
				study.setAvgMark(set.getDouble("AVG_MARK"));

				return study;
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
	public void updateStudy(int id, Study study) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(UPDATE_STUDY_BY_ID);
			statement.setString(1, study.getName());
			statement.setDouble(2, study.getHours());
			statement.setInt(3, study.getProfessorId());
			statement.setDouble(4, study.getAvgMark());
			statement.setInt(5, id);
			
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	}

	@Override
	public void deleteStudyById(int id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			
			statement = connection.prepareStatement(DELETE_STUDY_BY_ID);
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
	public void addStudy(Study study) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_STUDY);
			statement.setString(1, study.getName());
			statement.setDouble(2, study.getHours());
			statement.setInt(3, study.getProfessorId());
			statement.setDouble(4, study.getAvgMark());			
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	

	}

	@Override
	public Study getStudyByName(String name) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_STUDY_BY_NAME);
			statement.setString(1, name);
			set = statement.executeQuery();
			if (set.next()) {
				Study study= new Study();
				study.setId(set.getInt("ID"));
				study.setName(set.getString("NAME"));
				study.setHours(set.getDouble("HOURS"));
				study.setProfessorId(set.getInt("PROFESSOR_ID"));
				study.setAvgMark(set.getDouble("AVG_MARK"));
				
				return study;
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
