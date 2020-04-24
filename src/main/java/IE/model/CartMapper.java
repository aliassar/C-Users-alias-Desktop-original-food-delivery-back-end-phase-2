package IE.model;

import IE.Loghme;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartMapper extends Mapper<Cart, Integer> {

    private static CartMapper instance;
    public static final String COLUMNS = " remainedTimeToArrive, Status, restaurantID ";
    public static final String TABLE_NAME = "carts";

    public static CartMapper getInstance() {
        if (instance == null)
            try {
                instance = new CartMapper();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return instance;
    }

    public CartMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(" +
                        "id integer NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "remainedTimeToArrive float, " +
                        "Status varchar(100), " +
                        "restaurantID varchar(100), " +
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
    protected String getInsertStatement(Cart cart) throws SQLException {
        OrderMapper orderMapper = OrderMapper.getInstance();
        int cartId = getLastId() + 1;
        for (int i = 0; i < cart.getOrders().size(); i++) {
            Order order = cart.getOrders().get(i);
            order.setCartId(cartId);
            orderMapper.insert(order);
        }
        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                cart.getRemainedTimeToArrive() + ", " +
                '"' + cart.getStatus() + '"' + ", " +
                '"' + cart.getRestaurantID() + '"' +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected Cart convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Cart(null,
                rs.getFloat(2),
                rs.getString(3),
                rs.getString(4)
        );
    }

    public int getLastId() throws SQLException {
        String statement = "SELECT LAST_INSERT_ID()";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return resultSet.getInt(1);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getLastId query.");
                throw ex;
            }
        }
    }
}
