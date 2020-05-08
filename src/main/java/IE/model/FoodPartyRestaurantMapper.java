package IE.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodPartyRestaurantMapper extends Mapper<FoodPartyRestaurant, String, String> {
    private String id;
    private String name;
    private String description;
    private ArrayList<Food> menu;
    private Location location;
    private URL logo;
    private static FoodPartyRestaurantMapper instance;
    public static final String COLUMNS = " id, name, description, x, y, logo ";
    public static final String TABLE_NAME = "foodPartyRestaurants";

    public static FoodPartyRestaurantMapper getInstance() {
        if (instance == null)
            try {
                instance = new FoodPartyRestaurantMapper();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        return instance;
    }

    private FoodPartyRestaurantMapper() throws SQLException, IOException {

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
                " WHERE id = " + '"'+ id + '"'+ ";";
    }

    @Override
    protected String getInsertStatement(FoodPartyRestaurant restaurant) {
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
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected FoodPartyRestaurant convertResultSetToObject(ResultSet rs) throws SQLException, MalformedURLException {
        Location location = new Location(rs.getFloat(4), rs.getFloat(5));
        URL url = new URL(rs.getString(6));
        FoodPartyMapper foodMapper = FoodPartyMapper.getInstance();
        return new FoodPartyRestaurant(
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
        return"SELECT " + COLUMNS +" FROM " + TABLE_NAME + ";";
    }
    @Override
    protected String getFilterStatement(String name)  {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE name = " +'"'+  name +'"'+  ";";
    }

    @Override
    protected void getInsertCallBack(FoodPartyRestaurant foodPartyRestaurant) throws SQLException {
        FoodPartyMapper foodPartyMapper = FoodPartyMapper.getInstance();
        String foodPartyRestaurantId = foodPartyRestaurant.getId();
        for (int i = 0; i < foodPartyRestaurant.getMenu().size(); i++) {
            FoodParty food = foodPartyRestaurant.getMenu().get(i);
            food.setRestaurantId(foodPartyRestaurantId);
            foodPartyMapper.insert(food);
        }
    }
    public void updateRestaurants() throws SQLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<FoodPartyRestaurant> restaurants;
        restaurants = mapper.readValue(new URL("http://138.197.181.131:8080/foodparty")
                , new TypeReference<List<FoodPartyRestaurant>>() {
                });
        for ( FoodPartyRestaurant restaurant: restaurants){
            try{
                this.find(restaurant.getId());
            } catch (SQLException e){
                this.insert(restaurant);
            }
        }
    }
}
