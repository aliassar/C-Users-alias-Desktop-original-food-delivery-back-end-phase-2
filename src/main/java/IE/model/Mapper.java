package IE.model;

import IE.Loghme;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<T, I, K> {

    private static Loghme instance;

    abstract protected String getFindStatement(I id);

    abstract protected String getInsertStatement(T t) throws SQLException;

    abstract protected String getDeleteStatement(I id);

    abstract protected String getAllStatement();

    abstract protected String getFilterStatement(K id);

    abstract protected void getInsertCallBack(T t) throws SQLException;


    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException;

    public T find(I id) throws SQLException, MalformedURLException {

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement(id))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToObject(resultSet);
                }
                return convertResultSetToObject(resultSet);
            } catch (SQLException | MalformedURLException ex) {
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getInsertStatement(obj))
        ) {
            try {
                st.executeUpdate();
                getInsertCallBack(obj);
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
            }
        }
    }

    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement(id))
        ) {
            try {
                st.executeUpdate();
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
            }
        }
    }

    public ArrayList<T> getAll() throws SQLException, MalformedURLException {
        ArrayList<T> result = new ArrayList<T>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAllStatement());
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                st.close();
                con.close();
                return result;
            } catch (SQLException | MalformedURLException ex) {
                System.out.println("error in Mapper.getAll query.");
                return null;
            }
        }
    }

    public ArrayList<T> filter(K id) throws SQLException, MalformedURLException {
        ArrayList<T> result = new ArrayList<T>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFilterStatement(id));
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                st.close();
                con.close();
                return result;
            } catch (SQLException | MalformedURLException ex) {
                System.out.println("error in Mapper.filter query.");
                return null;
            }
        }
    }

    public int getLastIdInt() throws SQLException {
        String statement = "SELECT LAST_INSERT_ID()";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getLastId query.");
                return 0;
            }
        }
    }

    public String getLastIdString() throws SQLException {
        String statement = "SELECT LAST_INSERT_ID()";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
                return "";
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getLastId query.");
                return "";
            }
        }
    }
}