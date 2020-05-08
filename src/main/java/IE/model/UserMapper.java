package IE.model;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer, Integer> {
    private static UserMapper instance;
    public static final String COLUMNS = " fname, lname, phoneNumber, email, wallet ";
    public static final String TABLE_NAME = "users";

    public static UserMapper getInstance() {
        if (instance == null)
            try {
                instance = new UserMapper();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return instance;
    }

    public UserMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(" +
                        "email varchar(100) NOT NULL PRIMARY KEY, " +
                        "fname varchar(100), " +
                        "lname varchar(100), " +
                        "phoneNumber varchar(100), " +
                        "wallet float " +
                        ");",
                TABLE_NAME));
        con.close();
    }

    @Override
    protected String getFindStatement(Integer id) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE email = " + id.toString() + ";";
    }

    @Override
    protected String getInsertStatement(User user) throws SQLException {

        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                '"' + user.getEmail() + '"' + ", " +
                '"' + user.getFname() + '"' + ", " +
                '"' + user.getLname() + '"' + ", " +
                '"' + user.getPhoneNumber() + '"' + ", " +
                user.getWallet() +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected User convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException {
        CartMapper cartMapper = CartMapper.getInstance();
        return new User(cartMapper.filter(rs.getString(1)),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(1),
                rs.getFloat(5)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT "+ COLUMNS +" FROM " + TABLE_NAME + ";";
    }

    @Override
    protected String getFilterStatement(Integer id) {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    protected void getInsertCallBack(User user) throws SQLException {
        CartMapper cartMapper = CartMapper.getInstance();
        String userId = user.getEmail();
        for (int i = 0; i < user.getCartsOfUser().size(); i++) {
            Cart cart = user.getCartsOfUser().get(i);
            cart.setUserId(userId);
            cartMapper.insert(cart);
        }
    }
}
