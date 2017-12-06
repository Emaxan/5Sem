package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.Professor;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.ProfessorDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2ProfessorDAOImpl extends Db2AbstractDAO implements ProfessorDAO {


	private static final String GET_ALL = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM LAPUSHA.PROFESSORS";
	private static final String GET_PROFESSOR_BY_ID = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM LAPUSHA.PROFESSORS "
								 + "WHERE ID = ?";
	private static final String GET_PROFESSOR_BY_NAME = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM LAPUSHA.PROFESSORS " 
								+ "WHERE FIRST_NAME = ? AND SECOND_NAME=?";
	private static final String ADD_PROFESSOR = "INSERT INTO LAPUSHA.PROFESSORS (FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK)" 
			+"VALUES(?,?,?,?,?)" ;
	private static final String DELETE_PROFESSOR_BY_ID = "DELETE FROM LAPUSHA.PROFESSORS WHERE ID=?" ;
	private static final String UPDATE_PROFESSOR_BY_ID = "UPDATE  LAPUSHA.PROFESSORS SET FIRST_NAME=?,SECOND_NAME=?,FATHER_NAME=?,BIRTH_DATE=?,AVG_MARK=?        "
								+ "WHERE ID=?" ;

//	private static final String GET_ALL = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM PROFESSORS";
//	private static final String GET_PROFESSOR_BY_ID = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM PROFESSORS "
//								 + "WHERE ID = ?";
//	private static final String GET_PROFESSOR_BY_NAME = "SELECT ID,FIRST_NAME,SECOND_NAME,FATHER_NAME,BIRTH_DATE,AVG_MARK FROM PROFESSORS " 
//								+ "WHERE FIRST_NAME = ? AND SECOND_NAME=?";
//	private static final String ADD_PROFESSOR = "INSERT INTO PROFESSORS VALUES(?,?,?,?,?,?)" ;
//	private static final String DELETE_PROFESSOR_BY_ID = "DELETE FROM PROFESSORS WHERE ID=?" ;
//	private static final String UPDATE_PROFESSOR_BY_ID = "UPDATE  PROFESSORS SET ID=?,FIRST_NAME=?,SECOND_NAME=?,FATHER_NAME=?,BIRTH_DATE=?,AVG_MARK=?        "
//								+ "WHERE ID=?" ;



	public Db2ProfessorDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Professor> getAllProfessors() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();

			List<Professor> list = new ArrayList<Professor>();
			while (set.next()) {
				Professor professor= new Professor();
				professor.setId(set.getInt("ID"));
				professor.setFirstName(set.getString("FIRST_NAME"));
				professor.setSecondName(set.getString("SECOND_NAME"));
				professor.setFatherName(set.getString("FATHER_NAME"));
				professor.setBirthDate(set.getString("BIRTH_DATE"));
				professor.setAvgMark(set.getDouble("AVG_MARK"));	
				list.add(professor);
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
	public Professor getProfessorById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_PROFESSOR_BY_ID);
			statement.setInt(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				Professor professor = new Professor();
				professor.setId(set.getInt("ID"));
				professor.setFirstName(set.getString("FIRST_NAME"));
				professor.setSecondName(set.getString("SECOND_NAME"));
				professor.setFatherName(set.getString("FATHER_NAME"));
				professor.setBirthDate(set.getString("BIRTH_DATE"));
				professor.setAvgMark(set.getDouble("AVG_MARK"));		
				return professor;
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
	public void updateProfessor(int id, Professor professor) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(UPDATE_PROFESSOR_BY_ID);
			statement.setString(1, professor.getFirstName());
			statement.setString(2, professor.getSecondName());
			statement.setString(3, professor.getFatherName());
			statement.setString(4, professor.getBirthDate());
			statement.setDouble(5, professor.getAvgMark());
			statement.setInt(6, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	}

	@Override
	public void deleteProfessorById(int id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			
			statement = connection.prepareStatement(DELETE_PROFESSOR_BY_ID);
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
	public void addProfessor(Professor professor) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_PROFESSOR);
			
			statement.setString(1, professor.getFirstName());
			statement.setString(2, professor.getSecondName());
			statement.setString(3, professor.getFatherName());
			statement.setString(4, professor.getBirthDate());
			statement.setDouble(5, professor.getAvgMark());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
			}
	

	}

	@Override
	public Professor getProfessorByFirstNameAndSecondName(String firstName, String secondName) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_PROFESSOR_BY_NAME);
			statement.setString(1, firstName);
			statement.setString(2, secondName);
			set = statement.executeQuery();
			if (set.next()) {
				Professor professor= new Professor();
				
				professor.setId(set.getInt("ID"));
				professor.setFirstName(set.getString("FIRST_NAME"));
				professor.setSecondName(set.getString("SECOND_NAME"));
				professor.setFatherName(set.getString("FATHER_NAME"));
				professor.setBirthDate(set.getString("BIRTH_DATE"));
				professor.setAvgMark(set.getDouble("AVG_MARK"));
				return professor;
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
