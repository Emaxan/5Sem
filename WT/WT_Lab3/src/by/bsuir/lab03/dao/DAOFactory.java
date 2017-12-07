package by.bsuir.lab03.dao;

import by.bsuir.lab03.dao.exception.DAOConnectionException;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.DBApartmentDAO;
import by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.TPApartmentDAO;
import by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO.TPUserDAO;
import by.bsuir.lab03.dao.impl.DBUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
    private static final DAOFactory factory = new DAOFactory();

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DBURL = "jdbc:sqlite:D:\\University\\5_sem\\Programs\\WT\\WT_Lab3\\database\\Lab3DB.db";
    private static final String DBUSER = "root";
    private static final String DBPASSWD = "root";

    private final UserDAO userDAO = new TPUserDAO(new DBUserDAO());
    private final ApartmentDAO apartmentDAO = new TPApartmentDAO(new DBApartmentDAO());

    public static DAOFactory getInstance() {
        return factory;
    }

    public static Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOConnectionException(e.getMessage());
        }
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }

    private DAOFactory() {}

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public ApartmentDAO getApartmentDAO() {
        return apartmentDAO;
    }
}
