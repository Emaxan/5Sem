package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.Professor;


public interface ProfessorDAO {
	List<Professor> getAllProfessors() throws DAOException;

	Professor getProfessorById(int id) throws DAOException;

	void updateProfessor(int id, Professor professor) throws DAOException;

	void deleteProfessorById(int id) throws DAOException;

	void addProfessor(Professor professor) throws DAOException;

	Professor getProfessorByFirstNameAndSecondName(String firstName, String secondName) throws DAOException;

}
