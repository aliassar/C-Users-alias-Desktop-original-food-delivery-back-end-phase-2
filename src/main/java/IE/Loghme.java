package IE;

import IE.CustomSerializer.CustomCartSerializer;
import IE.CustomSerializer.CustomFoodSerializer;
import IE.Exceptions.*;
import IE.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Loghme {
    private static Loghme instance;
    private ArrayList<Restaurant> AllRestaurants;
    private User AppUser;

    public static Loghme getInstance() {
        if (instance == null)
            try {
                instance = new Loghme();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //instance = new Loghme();
        return instance;
    }

    private Loghme() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.AllRestaurants = mapper.readValue(new URL("http://138.197.181.131:8080/restaurants")
                , new TypeReference<List<Restaurant>>() {
                });
        this.AppUser = new User("H", "M", "+0", "test@test.com", 2500000);
    }

    public ArrayList<Restaurant> getAllRestaurants() {
        return this.AllRestaurants;
    }

    public Restaurant getRestaurantInfo(String restaurantId) throws OutOfBoundaryLocation, NoRestaurant {
        for (Restaurant restaurant : this.AllRestaurants) {
            if (restaurant.getId().equals(restaurantId)) {
                if (restaurant.calculateLocation() < 170) {
                    return restaurant;
                } else {
                    throw new OutOfBoundaryLocation("chosen restaurant is too far");
                }
            }
        }
        throw new NoRestaurant("no such restaurant found");
    }

    public User getAppUser() {
        return this.AppUser;
    }

    public void addToCart(Food food) throws NoRestaurant, WrongFood, DifRestaurants {

        //Check if there is a restaurant there
        if (this.AllRestaurants.size() == 0) {
            throw new NoRestaurant("there is no restaurants to choose");
        }
        boolean UnknownFood = true;
        Restaurant rest = new Restaurant();
        for (Restaurant restaurant : this.AllRestaurants) {
            if (restaurant.getName().equals(food.getRestaurantName())) {
                rest = restaurant;
                UnknownFood = false;
            }
        }
        if (UnknownFood) {
            throw new NoRestaurant("there is no restaurant with that name");
        }
        UnknownFood = true;
        for (int i = 0; i < rest.getMenu().size(); i++) {
            if (rest.getMenu().get(i).getName().equals(food.getName())) {
                UnknownFood = false;
                break;
            }
        }
        if (UnknownFood) {
            throw new WrongFood("no food with this name in this restaurant");
        }

        boolean differentRestaurant = false;
        try {
            Cart inProcessCart = this.AppUser.getInProcessCart();
            for (Order value : inProcessCart.getOrders()) {
                if (value.getRestaurantName().equals(food.getRestaurantName())) {
                    differentRestaurant = true;
                    break;
                }
            }
            boolean sameFood = false;
            for (Order order : inProcessCart.getOrders()) {
                if (order.getFoodName().equals(food.getName())) {
                    sameFood = true;
                    order.AddNum();
                    return;
                }
            }
            if (!sameFood) {
                if (differentRestaurant) {
                    Order order = new Order(food.getName(), food.getRestaurantName(), 1, food.getPrice());
                    inProcessCart.addToOrders(order);
                } else {
                    throw new DifRestaurants("you can not choose different restaurant");
                }
            }
        } catch (NoInProcessOrder | NoOrder noInProcessOrder) {
            Cart newCart = new Cart();
            newCart.setStatus("inProcess");
            Order order = new Order(food.getName(), food.getRestaurantName(), 1, food.getPrice());
            newCart.addToOrders(order);
            this.AppUser.newCart(newCart);
        }
    }

    public Cart getCart() throws NoInProcessOrder, NoOrder {
        return this.AppUser.getInProcessCart();
    }

    public void finalizeOrder() throws IOException, NoInProcessOrder, InsufficientMoney, NoOrder {
        Cart inProcessCart = this.AppUser.getInProcessCart();
        ObjectMapper mapper = new ObjectMapper();
        float totalPrice = 0;
        for (Order order : inProcessCart.getOrders()) {
            totalPrice += (order.getCost() * order.getNumOfOrder());
        }
        if (this.AppUser.getWallet() < totalPrice) {
            throw new InsufficientMoney("Insufficient money");
        }
        SimpleModule module = new SimpleModule();
        module.addSerializer(Cart.class, new CustomCartSerializer());
        mapper.registerModule(module);
        mapper.writeValueAsString(inProcessCart);
        System.out.println("Order recorded successfully");
        inProcessCart.setStatus("finding delivery");
        this.AppUser.setWallet(this.AppUser.getWallet() - totalPrice);

    }

    public void increaseWallet(Float amount){
        this.AppUser.AddToWallet(amount);
    }
    public ArrayList<Restaurant> getNearbyRestaurants() {
        ArrayList<Restaurant> selectedRestaurants = new ArrayList<>();
        for (Restaurant restaurant : this.AllRestaurants) {
            if (restaurant.calculateLocation() <= 170) {
                selectedRestaurants.add(restaurant);
            }
        }
        return selectedRestaurants;
    }

}
