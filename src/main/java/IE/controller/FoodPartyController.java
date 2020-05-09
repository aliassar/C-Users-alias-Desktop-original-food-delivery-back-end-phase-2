package IE.controller;

import IE.Loghme;
import IE.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FoodPartyController {
    @RequestMapping(value = "/foodparty",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<FoodPartyRestaurant> GetAllFoodPartyRestaurants(@RequestAttribute(value = "user") User user)
            throws MalformedURLException, SQLException {
        FoodPartyRestaurantMapper foodPartyRestaurantMapper = FoodPartyRestaurantMapper.getInstance();
        return foodPartyRestaurantMapper.getAll();

    }
}
