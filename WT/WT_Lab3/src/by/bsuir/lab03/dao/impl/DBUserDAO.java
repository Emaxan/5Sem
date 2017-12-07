package by.bsuir.lab03.dao.impl;

import by.bsuir.lab03.dao.DAOFactory;
import by.bsuir.lab03.dao.UserDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.exception.DAOQueryException;
import by.bsuir.lab03.dao.exception.DAOUserExistsException;
import by.bsuir.lab03.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUserDAO implements UserDAO {
    private final String TABLE_NAME = "users";
    private final String COLUMN_ID_LABEL = "id";
    private final String COLUMN_EMAIL_LABEL = "email";
    private final String COLUMN_PASSWORD_LABEL = "passwd";
    private final String COLUMN_ROLE_LABEL = "role";

    private final String SELECT_USER_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE `"
            + COLUMN_EMAIL_LABEL + "` = ?";
    private final String INSERT_USER = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_EMAIL_LABEL + ", "
            + COLUMN_PASSWORD_LABEL + ", " + COLUMN_ROLE_LABEL + ") VALUES (?, ?, ?)";

    @Override
    public void addUser(User user) throws DAOException {
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            if (selectUserByEmail(connection, user.getEmail()) != null) {
                throw new DAOUserExistsException("");
            }
            insertUser(connection, user);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        User result = null;
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            result = selectUserByEmail(connection, email);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
        return result;
    }

    private User selectUserByEmail(Connection connection, String email) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        User result = null;
        try {
            ps = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = new User();
                result.setId(rs.getInt(COLUMN_ID_LABEL));
                result.setEmail(rs.getString(COLUMN_EMAIL_LABEL));
                result.setPassword(rs.getString(COLUMN_PASSWORD_LABEL));
                result.setRole(rs.getString(COLUMN_ROLE_LABEL));
            }
        } catch (SQLException e) {
            throw new DAOQueryException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
        }
        return result;
    }

    private void insertUser(Connection connection, User user) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(INSERT_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRoleInString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOQueryException(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
        }
    }
}
