package edu.iba.sh.dao.db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import edu.iba.sh.dao.DAOException;

public abstract class Db2AbstractDAO {

	public static final Logger log = Logger.getLogger(Db2AbstractDAO.class);

	protected final Connection getConnection() throws DAOException {

		final String DB_URL = "jdbc:sqlite:D:\\University\\5_sem\\Programs\\WT\\WT_Lab3\\database\\Lab3DB.db";
		final String DB_USR = "root";
		final String DB_PAS = "root";
		final String DB_DRIVER = "org.sqlite.JDBC";

		try {
			Class.forName(DB_DRIVER);
			Connection cn = DriverManager.getConnection(DB_URL, DB_USR, DB_PAS);
			log.info("connected to mysql");
			return cn;
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e);
		}
	}

	protected void closeConnection(Connection cn) throws DAOException {
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

	protected void closeStatement(Statement statement) throws DAOException {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

	protected void closeResultSet(ResultSet set) throws DAOException {
		if (set != null) {
			try {
				set.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

}
