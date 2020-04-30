package IE.controller;


import IE.Exceptions.NoRestaurant;
import IE.Exceptions.OutOfBoundaryLocation;
import IE.Loghme;
import IE.model.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class RestaurantController {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/restaurants",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Restaurant> GetAllRestaurants() throws MalformedURLException, SQLException {
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
        } catch (NoRestaurant | SQLException | MalformedURLException noRestaurant) {
            noRestaurant.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(noRestaurant.getMessage());
        }
    }
}

