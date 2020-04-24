package IE.controller;


import IE.exceptions.NoRestaurant;
import IE.exceptions.OutOfBoundaryLocation;
import IE.Loghme;
import IE.model.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@RestController
public class RestaurantController {
    @RequestMapping(value = "/restaurants",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Restaurant> GetAllRestaurants(){
        Loghme loghme = Loghme.getInstance();
        return loghme.getNearbyRestaurants();
    }

    @RequestMapping(value = "/restaurants/{restaurantId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetRestaurant(@PathVariable(value = "restaurantId") String restaurantId){
        Loghme loghme = Loghme.getInstance();
        try {
            Restaurant restaurant = loghme.getRestaurantInfo(restaurantId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    restaurant);

        } catch (OutOfBoundaryLocation outOfBoundaryLocation) {
            outOfBoundaryLocation.printStackTrace();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(outOfBoundaryLocation.getMessage());
        } catch (NoRestaurant noRestaurant) {
            noRestaurant.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(noRestaurant.getMessage());
        }
    }
}

