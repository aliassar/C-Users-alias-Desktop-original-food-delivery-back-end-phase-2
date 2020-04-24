package IE.utils;

import IE.Loghme;
import IE.model.FoodPartyRestaurant;
import IE.model.FoodPartyRestaurantMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodPartyManagement implements Runnable  {

    @Override
    public void run()  {
        try {
            ManagePartyRestaurants();
        }catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void ManagePartyRestaurants() throws IOException, SQLException {
        FoodPartyRestaurantMapper foodPartyRestaurantMapper = FoodPartyRestaurantMapper.getInstance();
        foodPartyRestaurantMapper.updateRestaurants();
    }
}
