package edu.iba.sh.dao;

import edu.iba.sh.dao.db2.impl.Db2GroupDAOImpl;
import edu.iba.sh.dao.db2.impl.Db2MarkDAOImpl;
import edu.iba.sh.dao.db2.impl.Db2ProfessorDAOImpl;
import edu.iba.sh.dao.db2.impl.Db2StudentDAOImpl;
import edu.iba.sh.dao.db2.impl.Db2StudyDAOImpl;
import edu.iba.sh.dao.db2.impl.Db2UserDAOImpl;

public class DAOFactory {

	private enum DdType {
		DB2
	}

	private static DdType type = DdType.DB2;

	public static StudentDAO getStudentDAO() {
		switch (type) {
		case DB2:
			return new Db2StudentDAOImpl();
		default:
			return null;
		}

	}

	public static UserDAO getUserDAO() {
		switch (type) {
		case DB2:
			return new Db2UserDAOImpl();
		default:
			return null;
		}

	}

	public static GroupDAO getGroupDAO() {
		switch (type) {
		case DB2:
			return new Db2GroupDAOImpl();
		default:
			return null;
		}
	}
	
	public static MarkDAO getMarkDAO() {
		switch (type) {
		case DB2:
			return new Db2MarkDAOImpl();
		default:
			return null;
		}
	}
	
	public static StudyDAO getStudyDAO() {
		switch (type) {
		case DB2:
			return new Db2StudyDAOImpl();
		default:
			return null;
		}
	}
	
	public static ProfessorDAO getProfessorDAO() {
		switch (type) {
		case DB2:
			return new Db2ProfessorDAOImpl();
		default:
			return null;
		}
	}
	

}
