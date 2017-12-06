package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.Study;

public interface StudyDAO {

	List<Study> getAllStudies() throws DAOException;

	Study getStudyById(int id) throws DAOException;

	void updateStudy(int id, Study study) throws DAOException;

	void deleteStudyById(int id) throws DAOException;

	void addStudy(Study study) throws DAOException;

	Study getStudyByName(String name) throws DAOException;

}
