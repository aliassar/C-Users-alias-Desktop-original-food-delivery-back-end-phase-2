package IE;

import IE.Exceptions.*;
import IE.utils.DeliveryManagment;
import IE.model.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Loghme {
    private static Loghme instance;
    private RestaurantMapper restaurantMapper;
    private FoodPartyRestaurantMapper foodPartyRestaurantMapper;
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public User getAppUser() {
        return AppUser;
    }

    private User AppUser;

    public static Loghme getInstance() {
        if (instance == null)
            try {
                instance = new Loghme();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    private Loghme() throws Exception {
        userMapper = UserMapper.getInstance();
        restaurantMapper = RestaurantMapper.getInstance();
        foodPartyRestaurantMapper = FoodPartyRestaurantMapper.getInstance();
        User user = new User("H", "M", "test@test.com","slm");
        this.AppUser = user;
        userMapper.insert(user);
    }


    public void AssignDeliveryToUser(ArrayList<Cart> AllCarts, int IndexOfCart, Location restaurantLocation) {
        ScheduledExecutorService scheduler;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new DeliveryManagment(AllCarts, IndexOfCart,
                scheduler, restaurantLocation), 0, 5, TimeUnit.SECONDS);

    }

    public float CalculateArrivingTime(Location RestaurantLocation, Delivery delivery) {
        float Result;
        Location UserLocation = new Location(0, 0);
        float Distance = 0;
        Distance += (float) UserLocation.Distance(delivery.getLocation(), RestaurantLocation);
        Distance += (float) UserLocation.Distance(UserLocation, RestaurantLocation);
        Result = Distance / delivery.getVelocity();
        return Result;

    }

    public float EstimateArivingTime(Location RestaurantLocation) {
        float Result = 60;
        Location UserLocation = new Location(0, 0);
        float Distance;
        Distance = (float) UserLocation.Distance(UserLocation, RestaurantLocation);
        Result += Distance / 5;
        Distance = Distance / 2;
        Result += Distance / 5;
        return Result;

    }

    public void DecreaseFoodCount(String RestaurantID, FoodParty food) throws MalformedURLException, SQLException {
        FoodPartyMapper foodPartyMapper = FoodPartyMapper.getInstance();
        foodPartyMapper.DecreaseFoodCount(RestaurantID, food);
    }

    public Restaurant getRestaurantInfo(String restaurantId) throws OutOfBoundaryLocation, NoRestaurant, MalformedURLException, SQLException {
        ArrayList<Restaurant> AllRestaurants = this.restaurantMapper.getAll();
        for (Restaurant restaurant : AllRestaurants) {
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

    public void FoodPartyaddToCart(FoodParty food, String restaurantID) throws NoRestaurant, WrongFood, DifRestaurants, NoFoodRemained, MalformedURLException, SQLException {
        ArrayList<FoodPartyRestaurant> FoodPartyRestaurants = foodPartyRestaurantMapper.getAll();

        if (FoodPartyRestaurants.size() == 0) {
            throw new NoRestaurant("there is no restaurants to choose");
        }
        if (food.getCount() <= 0) {
            throw new NoFoodRemained("no food from this kind remained");
        }

        boolean UnknownFood = true;
        FoodPartyRestaurant rest ;
        ArrayList<FoodPartyRestaurant> foodPartyRestaurants = new ArrayList<>();
        try {
            foodPartyRestaurants = foodPartyRestaurantMapper.filter(food.getRestaurantName());
        } catch (SQLException e){
            throw new NoRestaurant("there is no restaurant with that name");
        } finally {
            rest = foodPartyRestaurants.get(0);
        }
        for (int i = 0; i < rest.getMenu().size(); i++) {
            if (rest.getMenu().get(i).getName().equals(food.getName())) {
                UnknownFood = false;
                break;
            }
        }
        if (UnknownFood) {
            throw new WrongFood("no food with this name in this restaurant");
        }
        Cart inProcessCart = this.AppUser.getInProcessCart();
        if (inProcessCart.getOrders().size() == 0) {
            inProcessCart.setRestaurantID(restaurantID);
        }
        boolean sameRestaurant = inProcessCart.getOrders().size() == 0;
        for (Order value : inProcessCart.getOrders()) {
            if (value.getRestaurantName().equals(food.getRestaurantName())) {
                sameRestaurant = true;
                break;
            }
        }
        boolean sameFood = false;
        for (Order order : inProcessCart.getOrders()) {
            if (order.getFoodName().equals(food.getName())) {
                sameFood = true;
                order.AddNum();
                DecreaseFoodCount(restaurantID, food);
                return;
            }
        }
        if (!sameFood) {
            if (sameRestaurant) {
                Order order = new Order(food.getName(), food.getRestaurantName(), 1, food.getPrice());
                inProcessCart.addToOrders(order);
                DecreaseFoodCount(restaurantID, food);
            } else {
                throw new DifRestaurants("you can not choose different restaurant");
            }
        }


    }

    public void addToCart(Food food, String restaurantID, User user) throws NoRestaurant, WrongFood, DifRestaurants, MalformedURLException, SQLException {

        ArrayList<Restaurant> AllRestaurants = this.restaurantMapper.getAll();

        //Check if there is a restaurant there
        if (AllRestaurants.size() == 0) {
            throw new NoRestaurant("there is no restaurants to choose");
        }

        boolean UnknownFood = true;
        Restaurant rest ;
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            restaurants = restaurantMapper.filter(food.getRestaurantName());
        } catch (SQLException e){
            throw new NoRestaurant("there is no restaurant with that name");
        } finally {
            rest = restaurants.get(0);
        }
        for (int i = 0; i < rest.getMenu().size(); i++) {
            if (rest.getMenu().get(i).getName().equals(food.getName())) {
                UnknownFood = false;
                break;
            }
        }
        if (UnknownFood) {
            throw new WrongFood("no food with this name in this restaurant");
        }

        Cart inProcessCart = user.getInProcessCart();
        if (inProcessCart.getOrders().size() == 0) {
            inProcessCart.setRestaurantID(restaurantID);
        }
        boolean sameRestaurant = inProcessCart.getOrders().size() == 0;
        for (Order value : inProcessCart.getOrders()) {
            if (value.getRestaurantName().equals(food.getRestaurantName())) {
                sameRestaurant = true;
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
            if (sameRestaurant) {
                Order order = new Order(food.getName(), food.getRestaurantName(), 1, food.getPrice());
                inProcessCart.addToOrders(order);
            } else {
                throw new DifRestaurants("you can not choose different restaurant");
            }
        }

    }

    public Cart getCart(User user) {
        return user.getInProcessCart();
    }

    public Restaurant FindRestaurant(String ID) throws NoRestaurant, MalformedURLException {
        try {
            return restaurantMapper.find(ID);
        } catch (SQLException e) {
            throw new NoRestaurant("no such restaurant found");
        }
    }

    public FoodPartyRestaurant FindFoodPartyRestaurant(String ID) throws NoRestaurant, MalformedURLException {
        try {
            return foodPartyRestaurantMapper.find(ID);
        } catch (SQLException e) {
            throw new NoRestaurant("no such restaurant found");
        }
    }


    public void finalizeOrder(User user) throws IOException, InsufficientMoney, EmptyCart, SQLException {
        Cart inProcessCart = user.getInProcessCart();
        if (inProcessCart.getOrders().size() < 1) {
            throw new EmptyCart("Your cart is empty. Fill it with some food");
        }
        CartMapper mapper = CartMapper.getInstance();
        float totalPrice = 0;
        for (Order order : inProcessCart.getOrders()) {
            totalPrice += (order.getCost() * order.getNumOfOrder());
        }
        if (user.getWallet() < totalPrice) {
            throw new InsufficientMoney("Insufficient money");
        }
        inProcessCart.setStatus("finding delivery");
        mapper.insert(inProcessCart);
        System.out.println("Order recorded successfully");
        user.setWallet(user.getWallet() - totalPrice);
        user.newProcessedCart(inProcessCart);
        Restaurant chosenRestaurant;
        FoodPartyRestaurant chosenFoodPartyRestaurants;
        System.out.println(inProcessCart.getRestaurantID());

        try {
            chosenRestaurant = FindRestaurant(inProcessCart.getRestaurantID());
            this.AssignDeliveryToUser(user.getCartsOfUser(), user.getCartsOfUser().size() - 1,
                    chosenRestaurant.getLocation());
        } catch (NoRestaurant e) {
            try {
                chosenFoodPartyRestaurants = FindFoodPartyRestaurant(inProcessCart.getRestaurantID());
                this.AssignDeliveryToUser(user.getCartsOfUser(), user.getCartsOfUser().size() - 1,
                        chosenFoodPartyRestaurants.getLocation());
            } catch (NoRestaurant error) {
                error.printStackTrace();
            }
        }


        user.clearInProcessCart();

    }

    public void increaseWallet(Float amount) {
        this.AppUser.AddToWallet(amount);
    }

    public ArrayList<Restaurant> getNearbyRestaurants() throws MalformedURLException, SQLException {
        ArrayList<Restaurant> AllRestaurants = this.restaurantMapper.getAll();

        ArrayList<Restaurant> selectedRestaurants = new ArrayList<>();
        for (Restaurant restaurant : AllRestaurants) {
            if (restaurant.calculateLocation() <= 170) {
                selectedRestaurants.add(restaurant);
            }
        }
        return selectedRestaurants;
    }


}
