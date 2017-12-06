package edu.iba.sh.dao.db2.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iba.sh.bean.Student;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.StudentDAO;
import edu.iba.sh.dao.db2.Db2AbstractDAO;

public class Db2StudentDAOImpl extends Db2AbstractDAO implements StudentDAO {

	private static final String GET_ALL = "SELECT id,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM LAPUSHA.STUDENTS ORDER BY ID";
	private static final String GET_STUDENT_BY_ID = "SELECT ID,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM LAPUSHA.STUDENTS "
								 + "WHERE ID = ?";
	private static final String GET_STUDENT_BY_NAME = "SELECT ID,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM LAPUSHA.STUDENTS " 
								+ "WHERE FIRST_NAME = ? AND SECOND_NAME=?";
	private static final String ADD_STUDENT = "INSERT INTO LAPUSHA.STUDENTS (FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER) " 
								+ "VALUES(?,?,?,?)" ;
	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM LAPUSHA.STUDENTS WHERE ID=?" ;
	private static final String UPDATE_STUDENT_BY_ID = "UPDATE  LAPUSHA.STUDENTS SET FIRST_NAME=?,SECOND_NAME=?,AVG_MARK=?,GROUP_NUMBER=? "
								+ "WHERE ID=?" ;
	
//	private static final String GET_ALL = "SELECT id,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM STUDENTS";
//	private static final String GET_STUDENT_BY_ID = "SELECT ID,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM STUDENTS "
//								 + "WHERE ID = ?";
//	private static final String GET_STUDENT_BY_NAME = "SELECT ID,FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER FROM STUDENTS " 
//								+ "WHERE FIRST_NAME = ? AND SECOND_NAME=?";
//	private static final String ADD_STUDENT = "INSERT INTO STUDENTS(FIRST_NAME,SECOND_NAME,AVG_MARK,GROUP_NUMBER) "
//								+ "VALUES(?,?,?,?)" ;
//	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM STUDENTS WHERE ID=?" ;
//	private static final String UPDATE_STUDENT_BY_ID = "UPDATE  STUDENTS SET ID=?,FIRST_NAME=?,SECOND_NAME=?,AVG_MARK=?,GROUP_NUMBER=? "
//								+ "WHERE ID=?" ;

	@Override
	public List<Student> getAllStudents() throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_ALL);
			set = statement.executeQuery();

			List<Student> list = new ArrayList<Student>();
			while (set.next()) {
				Student student= new Student();
				student.setId(set.getInt("ID"));
				student.setFirstName(set.getString("FIRST_NAME"));
				student.setSecondName(set.getString("SECOND_NAME"));
				student.setAvgMark(set.getDouble("AVG_MARK"));
				student.setGroupNumber(set.getString("GROUP_NUMBER"));	
				list.add(student);
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
	public Student getStudentById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_STUDENT_BY_ID);
			statement.setInt(1, id);
			set = statement.executeQuery();
			if (set.next()) {
				Student student = new Student();
				student.setId(set.getInt("ID"));
				student.setFirstName(set.getString("FIRST_NAME"));
				student.setSecondName(set.getString("SECOND_NAME"));
				student.setAvgMark(set.getDouble("AVG_MARK"));
				student.setGroupNumber(set.getString("GROUP_NUMBER"));	
				return student;
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
	public void updateStudent(int id, Student student) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(UPDATE_STUDENT_BY_ID);
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getSecondName());
			statement.setDouble(3, student.getAvgMark());
			statement.setString(4, student.getGroupNumber());
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
	public void deleteStudentById(int id) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			
			statement = connection.prepareStatement(DELETE_STUDENT_BY_ID);
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
	public void addStudent(Student student) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(ADD_STUDENT,PreparedStatement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getSecondName());
			statement.setDouble(3, student.getAvgMark());
			statement.setString(4, student.getGroupNumber());
			statement.executeUpdate();
			
			set=statement.getGeneratedKeys();
			set.next();
			int id=set.getInt(1);
			student.setId(id);
			System.out.println(student);
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(set);
			closeStatement(statement);
			closeConnection(connection);
			}
	

	}

	@Override
	public Student getStudentByFirstnameAndSecondname(String firstName, String secondName) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();

			statement = connection.prepareStatement(GET_STUDENT_BY_NAME);
			statement.setString(1, firstName);
			statement.setString(2, secondName);
			set = statement.executeQuery();
			if (set.next()) {
				Student student= new Student();
				student.setId(set.getInt("ID"));
				student.setFirstName(set.getString("FIRST_NAME"));
				student.setSecondName(set.getString("SECOND_NAME"));
				student.setAvgMark(set.getDouble("AVG_MARK"));
				student.setGroupNumber(set.getString("GROUP_NUMBER"));
				return student;
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




	public  List<Student> getRecords(int start,int total){  
	        List<Student> list=new ArrayList<Student>();  
	        try{  
	            Connection con=getConnection();  
	            PreparedStatement ps=con.prepareStatement(  
	"select * from  students limit ?,? ");
	int temp= start-1;
	ps.setInt(1,temp);
	ps.setInt(2, total);
	            ResultSet rs=ps.executeQuery();  
	            while(rs.next()){  
	            	Student e=new Student();  
	                e.setId(rs.getInt(1));  
	                e.setId(rs.getInt(1));
	                e.setFirstName(rs.getString("FIRST_NAME"));
	                e.setSecondName(rs.getString("SECOND_NAME"));
	                e.setAvgMark(rs.getDouble("AVG_MARK"));
	                e.setGroupNumber(rs.getString("GROUP_NUMBER"));
	                list.add(e);  
	            }  
	            con.close();  
	        }catch(Exception e){System.out.println(e);}  
	        return list;  
	    } 

	
	public int getNoOfRecords(){  
	int result=0;
        try{  
            Connection con=getConnection();  
            PreparedStatement ps=con.prepareStatement("select COUNT(*) from  students");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
            	result=rs.getInt(1);
            }  
            con.close();  
        }catch(Exception e){System.out.println(e);}  
        return result;  
    
	
	}

}
