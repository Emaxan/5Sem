package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.Mark;

public interface MarkDAO {
	List<Mark> getAllMarks() throws DAOException;

	Mark getMarkById(int id) throws DAOException;

	void updateMark(int id, Mark mark) throws DAOException;

	void deleteMarkById(int id) throws DAOException;

	void addMark(Mark mark) throws DAOException;


}
