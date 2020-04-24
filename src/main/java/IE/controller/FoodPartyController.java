package IE.controller;

import IE.Loghme;
import IE.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class FoodPartyController {
    @RequestMapping(value = "/foodparty",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<FoodPartyRestaurant> GetAllFoodPartyRestaurants(){
        Loghme loghme = Loghme.getInstance();
        ArrayList<FoodPartyRestaurant> restaurants = loghme.getFoodPartyRestaurants();
        return restaurants;

    }
}
