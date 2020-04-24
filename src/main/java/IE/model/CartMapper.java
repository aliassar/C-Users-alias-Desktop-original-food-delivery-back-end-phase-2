package IE.model;

import IE.Loghme;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartMapper extends Mapper<Cart, Integer, Integer> {

    private static CartMapper instance;
    public static final String COLUMNS = " userId, remainedTimeToArrive, Status, restaurantID ";
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
                        "userId integer, " +
                        "remainedTimeToArrive float, " +
                        "Status varchar(100), " +
                        "restaurantID varchar(100) " +
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
    protected String getInsertStatement(Cart cart)  {

        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                cart.getUserId() + ", " +
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
    protected Cart convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException {
        OrderMapper orderMapper = OrderMapper.getInstance();
        return new Cart(orderMapper.filter(rs.getInt(1)),
                rs.getInt(2),
                rs.getFloat(3),
                rs.getString(4),
                rs.getString(5)
        );
    }
    @Override
    protected String getAllStatement()  {
        return "SELECT * FROM "+ TABLE_NAME + ";";
    }
    @Override
    protected String getFilterStatement(Integer userId) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE userId = " + userId.toString() + ";";
    }

    @Override
    protected void getInsertCallBack(Cart cart) throws SQLException {
        OrderMapper orderMapper = OrderMapper.getInstance();
        int cartId = getLastId().getInt(1);
        for (int i = 0; i < cart.getOrders().size(); i++) {
            Order order = cart.getOrders().get(i);
            order.setCartId(cartId);
            orderMapper.insert(order);
        }
    }

}
