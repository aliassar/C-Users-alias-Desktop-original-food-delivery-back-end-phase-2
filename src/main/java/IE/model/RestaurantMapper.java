package IE.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMapper extends Mapper<Restaurant, String, String> {
    private String id;
    private String name;
    private String description;
    private ArrayList<Food> menu;
    private Location location;
    private URL logo;
    private static RestaurantMapper instance;
    public static final String COLUMNS = " id, name, description, x, y, logo ";
    public static final String TABLE_NAME = "restaurants";

    public static RestaurantMapper getInstance() {
        if (instance == null)
            try {
                instance = new RestaurantMapper();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        return instance;
    }

    private RestaurantMapper() throws SQLException, IOException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(" +
                        "id varchar(100) NOT NULL PRIMARY KEY, " +
                        "name varchar(100), " +
                        "description varchar(500), " +
                        "x float, " +
                        "y float, " +
                        "logo varchar(500) " +
                        ");",
                TABLE_NAME));
        st.close();
        con.close();
        this.updateRestaurants();
    }

    @Override
    protected String getFindStatement(String id) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = " + '"' + id + '"' + ";";
    }

    @Override
    protected String getInsertStatement(Restaurant restaurant) {
        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                '"' + restaurant.getId() + '"' + ", " +
                '"' + restaurant.getName() + '"' + ", " +
                '"' + restaurant.getDescription() + '"' + ", " +
                restaurant.getLocation().getX() + ", " +
                restaurant.getLocation().getY() + ", " +
                '"' + restaurant.getLogo().toString() + '"' +
                ");";
    }

    @Override
    protected String getDeleteStatement(String id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + '"' + id + '"' + ";";
    }

    @Override
    protected Restaurant convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException {
        Location location = new Location(rs.getFloat(4), rs.getFloat(5));
        URL url = new URL(rs.getString(6));
        FoodMapper foodMapper = FoodMapper.getInstance();
        return new Restaurant(
                rs.getString(1),
                rs.getString(2),
                rs.getString(5),
                foodMapper.filter(rs.getString(1)),
                location,
                url
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT "+ COLUMNS +" FROM " + TABLE_NAME + ";";
    }

    protected String getFilterStatement(String name)  {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE name = " +'"'+  name +'"'+  ";";
    }
    @Override
    protected void getInsertCallBack(Restaurant restaurant) throws SQLException {
        FoodMapper foodMapper = FoodMapper.getInstance();
        String restaurantId =  restaurant.getId();
        for (int i = 0; i < restaurant.getMenu().size(); i++) {
            Food food = restaurant.getMenu().get(i);
            food.setRestaurantId(restaurantId);
            foodMapper.insert(food);
        }
    }
    public void updateRestaurants() throws SQLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants;
        restaurants = mapper.readValue(new URL("http://138.197.181.131:8080/restaurants")
                , new TypeReference<List<Restaurant>>() {
                });
        for (Restaurant restaurant : restaurants) {
            try {
                this.find(restaurant.getId());
            } catch (SQLException e) {
                this.insert(restaurant);
            }
        }
    }

}
