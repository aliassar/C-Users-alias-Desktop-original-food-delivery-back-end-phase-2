package IE.controllers;

import IE.Exceptions.NoOrder;
import IE.Exceptions.NoRestaurant;
import IE.Exceptions.OutOfBoundaryLocation;
import IE.Loghme;
import IE.models.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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
