package IE.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper extends Mapper<Order, Integer, Integer> {

    private static OrderMapper instance;
    public static final String COLUMNS = " cartId, foodName, restaurantName, cost, numOfOrder ";
    public static final String TABLE_NAME = "orders";

    public static OrderMapper getInstance() {
        if (instance == null)
            try {
                instance = new OrderMapper();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return instance;
    }

    public OrderMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(" +
                        "id integer NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "cartId integer, " +
                        "foodName varchar(100), " +
                        "restaurantName varchar(100), " +
                        "cost float, " +
                        "numOfOrder integer " +
                        ");",
                TABLE_NAME));
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement(Integer id) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected String getInsertStatement(Order order) {
        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                order.getCartId() + ", " +
                '"' + order.getFoodName() + '"' + ", " +
                '"' + order.getRestaurantName() + '"' + ", " +
                order.getCost() + ", " +
                order.getNumOfOrder() +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected Order convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getFloat(6)
        );
    }
    @Override
    protected String getAllStatement() {
        return"SELECT " + COLUMNS +" FROM "+ TABLE_NAME + ";";
    }
    @Override
    protected String getFilterStatement(Integer cartId) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE cartId = " + cartId.toString() + ";";
    }
    @Override
    protected void getInsertCallBack(Order order) {

    }
}
