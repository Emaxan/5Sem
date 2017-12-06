package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.Student;

public interface StudentDAO {

	List<Student> getAllStudents() throws DAOException;

	Student getStudentById(int id) throws DAOException;

	void updateStudent(int id, Student student) throws DAOException;

	void deleteStudentById(int id) throws DAOException;

	void addStudent(Student student) throws DAOException;

	Student getStudentByFirstnameAndSecondname(String firstName, String secondName) throws DAOException;




	List<Student> getRecords(int start,int total);

	int getNoOfRecords();
}
