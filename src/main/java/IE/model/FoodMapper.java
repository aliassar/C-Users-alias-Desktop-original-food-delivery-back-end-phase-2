package IE.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class FoodMapper extends Mapper<Food, Integer, String> {
    private static FoodMapper instance;
    public static final String COLUMNS = " name, restaurantId, description, restaurantName, price, popularity, image, Type ";
    public static final String TABLE_NAME = "foods";

    public static FoodMapper getInstance() {
        if (instance == null)
            try {
                instance = new FoodMapper();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return instance;
    }

    private FoodMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(" +
                        "id integer NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "name varchar(100), " +
                        "restaurantId varchar(100), " +
                        "description varchar(500), " +
                        "restaurantName varchar(100), " +
                        "price float, " +
                        "popularity float, " +
                        "image varchar(200), " +
                        "Type varchar(100) " +
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
    protected String getInsertStatement(Food food) {

        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                '"' + food.getName() + '"' + ", " +
                '"' + food.getRestaurantId() + '"' + ", " +
                '"' + food.getDescription() + '"' + ", " +
                '"' + food.getRestaurantName() + '"' + ", " +
                food.getPrice() + ", " +
                food.getPopularity() + ", " +
                '"' + food.getImage().toString() + '"' + ", " +
                '"' + food.getType() + '"' +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected Food convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException {
        URL url = new URL(rs.getString(7));
        return new Food(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getFloat(5),
                rs.getFloat(6),
                url,
                rs.getString(8)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    @Override
    protected String getFilterStatement(String restaurantId) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE restaurantId = " + '"' + restaurantId + '"' + ";";
    }

    @Override
    protected void getInsertCallBack(Food food) throws SQLException {

    }


    public void DecreaseFoodCount(String RestaurantID, FoodParty food) throws SQLException, MalformedURLException {
        String statement = "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE restaurantId = " +
                '"' + RestaurantID + '"' +
                " AND name = " +
                '"' + food.getName() + '"' +
                ";";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                PreparedStatement newSt = con.prepareStatement(statement);
                newSt.executeQuery();
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getLastId query.");
            }

        }
    }
}
