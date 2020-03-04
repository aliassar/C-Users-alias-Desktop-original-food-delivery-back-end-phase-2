package IE;

import IE.CustomSerializer.CustomCartSerializer;
import IE.Exceptions.*;
import IE.ManagersAndSchedulers.DeliveryManagment;
import IE.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Loghme {
    private static Loghme instance;
    private ArrayList<Restaurant> AllRestaurants;
    private ArrayList<FoodPartyRestaurant> FoodPartyRestaurants;
    //private ArrayList<Delivery> deliveries;
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

    public void setFoodPartyRestaurant(ArrayList<FoodPartyRestaurant> foodPartyRestaurants) {
        FoodPartyRestaurants = foodPartyRestaurants;
    }
    public void AssignDeliveryToUser(ArrayList<Cart> AllCarts,int IndexOfCart, Location restaurantLocation) throws IOException{
        ScheduledExecutorService scheduler;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new DeliveryManagment(AllCarts,IndexOfCart,
                scheduler, restaurantLocation), 0, 5, TimeUnit.SECONDS);

    }
    public float CalculateArrivingTime(Location RestaurantLocation, Delivery delivery){
        float Result = 0;
        Location UserLocation = new Location(0,0);
        float Distance = 0;
        Distance += (float) UserLocation.Distance(delivery.getLocation(), RestaurantLocation);
        Distance += (float) UserLocation.Distance(UserLocation, RestaurantLocation);
        Result = Distance/delivery.getVelocity();
        return Result;

    }

    public float EstimateArivingTime(Location RestaurantLocation){
        float Result = 60;
        Location UserLocation = new Location(0,0);
        float Distance = 0;
        Distance = (float) UserLocation.Distance(UserLocation, RestaurantLocation);
        Result += Distance/5;
        Distance = Distance/2;
        Result += Distance/5;
        return Result;

    }


    //    public void SetDelivery() throws IOException{
//        ObjectMapper mapper = new ObjectMapper();
//        this.deliveries = mapper.readValue(new URL("http://138.197.181.131:8080/deliveries")
//                , new TypeReference<List<Delivery>>() {
//                });
//    }

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

    public void FoodPartyaddToCart(FoodParty food)throws NoRestaurant, WrongFood, DifRestaurants, NoFoodRemained {

            if (this.FoodPartyRestaurants.size() == 0) {
                throw new NoRestaurant("there is no restaurants to choose");
            }
            if (food.getCount() == 0){
                throw new NoFoodRemained("no food from this kind remained");
            }

            boolean UnknownFood = true;
            FoodPartyRestaurant rest = new FoodPartyRestaurant();
            for (FoodPartyRestaurant restaurant : this.FoodPartyRestaurants) {
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
        Cart inProcessCart = this.AppUser.getInProcessCart();
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

    public void addToCart(Food food, String restaurantID) throws NoRestaurant, WrongFood, DifRestaurants {


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

            Cart inProcessCart = this.AppUser.getInProcessCart();
            if (inProcessCart.getOrders().size() == 0){
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

    public Cart getCart() {
        return this.AppUser.getInProcessCart();
    }

    public Restaurant FindRestaurant(String ID) throws NoRestaurant{
        for (int i=0; i<this.AllRestaurants.size(); i++){
            if (this.AllRestaurants.get(i).getId().equals(ID)){
                return this.AllRestaurants.get(i);
            }
        }
        throw new NoRestaurant("no such restaurant found");
    }
    public FoodPartyRestaurant FindFoodPartyRestaurant(String ID) throws NoRestaurant{
        for (int i = 0; i<this.FoodPartyRestaurants.size(); i++){
            if (this.FoodPartyRestaurants.get(i).getId().equals(ID)){
                return this.FoodPartyRestaurants.get(i);
            }
        }
        throw new NoRestaurant("no such restaurant found");
    }


    public void finalizeOrder() throws IOException, InsufficientMoney {
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
        this.AppUser.newProcessedCart(inProcessCart);

        Restaurant chosenRestaurant;
        FoodPartyRestaurant chosenFoodPartyRestaurants;
        System.out.println(inProcessCart.getRestaurantID());

        try {
            chosenRestaurant = FindRestaurant(inProcessCart.getRestaurantID());
            this.AssignDeliveryToUser(this.AppUser.getCartsOfUser(),this.AppUser.getCartsOfUser().size()-1,
                    chosenRestaurant.getLocation());
        }catch (NoRestaurant e){
           try {
               chosenFoodPartyRestaurants = FindFoodPartyRestaurant(inProcessCart.getRestaurantID());
               this.AssignDeliveryToUser(this.AppUser.getCartsOfUser(),this.AppUser.getCartsOfUser().size()-1,
                       chosenFoodPartyRestaurants.getLocation());
           }catch (NoRestaurant error){
               error.printStackTrace();
           }
        }


        this.AppUser.clearInProcessCart();

    }

    public void increaseWallet(Float amount) {
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
