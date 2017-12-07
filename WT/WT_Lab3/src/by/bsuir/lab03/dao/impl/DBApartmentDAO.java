package by.bsuir.lab03.dao.impl;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.DAOFactory;
import by.bsuir.lab03.dao.exception.DAOApartmentNotExistsException;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.exception.DAOQueryException;
import by.bsuir.lab03.domain.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DBApartmentDAO implements ApartmentDAO {
    private final String TABLE_NAME = "apartments";
    private final String COLUMN_ID_LABEL = "id";
    private final String COLUMN_HOTEL_LABEL = "hotel";
    private final String COLUMN_ROOM_LABEL = "room";
    private final String COLUMN_CLIENT_ID_LABEL = "guest_id";

    private final String SELECT_ALL_APARTMENTS = "SELECT * FROM " + TABLE_NAME;
    private final String SELECT_BOOKED_APARTMENTS = "SELECT * FROM " + TABLE_NAME + " WHERE `" +
            COLUMN_CLIENT_ID_LABEL + "` = ?";
    private final String SELECT_NOT_BOOKED_APARTMENTS = "SELECT * FROM " + TABLE_NAME + " WHERE `" +
            COLUMN_CLIENT_ID_LABEL + "` IS NULL";
    private final String SELECT_APARTMENT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE `"
            + COLUMN_ID_LABEL + "` = ?";
    private final String SELECT_BOOKED_APARTMENT = "SELECT * FROM " + TABLE_NAME + " WHERE `"
            + COLUMN_ID_LABEL + "` = ? AND `" + COLUMN_CLIENT_ID_LABEL + "` = ?";
    private final String SELECT_NOT_BOOKED_APARTMENT = "SELECT * FROM " + TABLE_NAME + " WHERE `"
            + COLUMN_ID_LABEL + "` = ? AND `" + COLUMN_CLIENT_ID_LABEL + "` IS NULL";
    private final String UPDATE_APARTMENT_CLIENT = "UPDATE " + TABLE_NAME + " SET `" + COLUMN_CLIENT_ID_LABEL +
            "` = ? WHERE `" + COLUMN_ID_LABEL + "` = ?";
    private final String DELETE_APARTMENT = "DELETE FROM " + TABLE_NAME + " WHERE `" + COLUMN_ID_LABEL + "` = ?";
    private final String INSERT_APARTMENT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_HOTEL_LABEL + ", "
            + COLUMN_ROOM_LABEL + ", " + COLUMN_CLIENT_ID_LABEL + ") VALUES (?, ?, ?)";

    @Override
    public ArrayList<Apartment> getAllApartments() throws DAOException {
        ArrayList<Apartment> result = new ArrayList<Apartment>();
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            selectAllApartments(connection, result);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public ArrayList<Apartment> getBookedApartments(int clientId) throws DAOException {
        ArrayList<Apartment> result = new ArrayList<Apartment>();
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            selectBookedApartments(connection, clientId, result);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public ArrayList<Apartment> getNotBookedApartments() throws DAOException {
        ArrayList<Apartment> result = new ArrayList<Apartment>();
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            selectNotBookedApartments(connection, result);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public void setBooking(int apartmentId, int clientId) throws DAOException {
        Apartment apartment;
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            apartment = selectNotBookedApartment(connection, apartmentId);
            if (apartment == null) {
                throw new DAOApartmentNotExistsException("");
            }
            apartment.setGuestId(clientId);
            updateApartmentClient(connection, apartment);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
    }

    @Override
    public void removeBooking(int apartmentId, int clientId) throws DAOException {
        Apartment apartment;
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            apartment = selectBookedApartment(connection, apartmentId, clientId);
            if (apartment == null) {
                throw new DAOApartmentNotExistsException("");
            }
            apartment.setGuestId(0);
            updateApartmentClient(connection, apartment);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
    }

    @Override
    public void removeApartment(int apartmentId) throws DAOException {
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            deleteApartment(connection, apartmentId);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
    }

    @Override
    public void addApartment(Apartment apartment) throws DAOException {
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection();
            insertApartment(connection, apartment);
        } finally {
            DAOFactory.releaseConnection(connection);
        }
    }

    private void selectAllApartments(Connection connection, ArrayList<Apartment> apartments)
            throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SELECT_ALL_APARTMENTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                apartments.add(getApartmentFromResultSet(rs));
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
    }

    private void selectBookedApartments(Connection connection, int clientId, ArrayList<Apartment> apartments)
            throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SELECT_BOOKED_APARTMENTS);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            while (rs.next()) {
                apartments.add(getApartmentFromResultSet(rs));
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
    }

    private void selectNotBookedApartments(Connection connection, ArrayList<Apartment> apartments)
            throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SELECT_NOT_BOOKED_APARTMENTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                apartments.add(getApartmentFromResultSet(rs));
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
    }

    private Apartment selectApartmentById(Connection connection, int id) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Apartment result = null;
        try {
            ps = connection.prepareStatement(SELECT_APARTMENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                result = getApartmentFromResultSet(rs);
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

    private Apartment selectBookedApartment(Connection connection, int apartmentId, int clientId) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Apartment result = null;
        try {
            ps = connection.prepareStatement(SELECT_BOOKED_APARTMENT);
            ps.setInt(1, apartmentId);
            ps.setInt(2, clientId);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = getApartmentFromResultSet(rs);
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

    private Apartment selectNotBookedApartment(Connection connection, int apartmentId) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Apartment result = null;
        try {
            ps = connection.prepareStatement(SELECT_NOT_BOOKED_APARTMENT);
            ps.setInt(1, apartmentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = getApartmentFromResultSet(rs);
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

    private void updateApartmentClient(Connection connection, Apartment apartment) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(UPDATE_APARTMENT_CLIENT);
            if (apartment.getGuestId() > 0) {
                ps.setInt(1, apartment.getGuestId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setInt(2, apartment.getId());
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

    private void deleteApartment(Connection connection, int apartmentId) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_APARTMENT);
            ps.setInt(1, apartmentId);
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

    private void insertApartment(Connection connection, Apartment apartment) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(INSERT_APARTMENT);
            ps.setString(1, apartment.getHotel());
            ps.setString(2, apartment.getRoom());
            if (apartment.getGuestId() > 0) {
                ps.setInt(3, apartment.getGuestId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
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

    private Apartment getApartmentFromResultSet(ResultSet rs) throws SQLException {
        Apartment result = new Apartment();
        result.setId(rs.getInt(COLUMN_ID_LABEL));
        result.setHotel(rs.getString(COLUMN_HOTEL_LABEL));
        result.setRoom(rs.getString(COLUMN_ROOM_LABEL));
        result.setGuestId(rs.getInt(COLUMN_CLIENT_ID_LABEL));
        return result;
    }
}
