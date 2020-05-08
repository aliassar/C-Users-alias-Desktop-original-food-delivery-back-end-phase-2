package IE.controller;

import IE.Exceptions.DifRestaurants;
import IE.Exceptions.NoFoodRemained;
import IE.Exceptions.NoRestaurant;
import IE.Exceptions.WrongFood;
import IE.Loghme;
import IE.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.net.MalformedURLException;
import java.sql.SQLException;

@RestController
public class CartController {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/cart",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cart GetCart(@RequestAttribute(value = "user") User user) {
        Loghme loghme = Loghme.getInstance();
        //User user = loghme.getAppUser();
        Cart cart = user.getInProcessCart();
        if (cart.getOrders().size() > 0) {
            float estimatedArrive = 10000;
            try {
                Restaurant chosenRestaurant = loghme.FindRestaurant(cart.getRestaurantID());
                estimatedArrive = loghme.EstimateArivingTime(chosenRestaurant.getLocation());
            } catch (NoRestaurant | MalformedURLException e) {
                try {
                    FoodPartyRestaurant chosenFoodPartyRestaurants = loghme.FindFoodPartyRestaurant(cart.getRestaurantID());
                    estimatedArrive = loghme.EstimateArivingTime(chosenFoodPartyRestaurants.getLocation());
                } catch (NoRestaurant | MalformedURLException error) {
                    error.printStackTrace();
                }
            }


        }
        return cart;
    }
    @RequestMapping(value = "/cart",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddToCart(@RequestAttribute(value = "orders") ArrayList<Food> orders,
                                       @RequestAttribute(value = "user") User user)
    {
        Loghme loghme = Loghme.getInstance();
        boolean ErrorDetected = false;
        for (int i=0; i<orders.size(); i++){
            try {
                loghme.addToCart(orders.get(i), orders.get(i).getRestaurantName(), user);

            }catch (NoRestaurant | WrongFood | DifRestaurants e) {
                e.printStackTrace();
                ErrorDetected = true;
            }catch (SQLException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        float estimatedArrive = 10000;
        if (ErrorDetected){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(estimatedArrive);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(estimatedArrive);
        }

    }

    @RequestMapping(value = "/cart/foodparty",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddToCartFoodParty(@RequestAttribute(value = "orders") ArrayList<FoodParty> orders,
                                                @RequestAttribute(value = "user") User user)
    {
        Loghme loghme = Loghme.getInstance();
        boolean ErrorDetected = false;
        for (int i=0; i<orders.size(); i++){
            try {
                loghme.FoodPartyaddToCart(orders.get(i), orders.get(i).getRestaurantName());
                loghme.addToCart(orders.get(i), orders.get(i).getRestaurantName(), user);

            }catch (NoRestaurant | WrongFood | DifRestaurants | NoFoodRemained e) {
                e.printStackTrace();
                ErrorDetected = true;
            }catch (SQLException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        float estimatedArrive = 10000;
        if (ErrorDetected){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(estimatedArrive);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(estimatedArrive);
        }

    }
}

