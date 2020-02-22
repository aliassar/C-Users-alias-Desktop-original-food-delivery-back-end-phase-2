package IE;

import IE.CustomSerializer.CustomCartSerializer;
import IE.CustomSerializer.CustomFoodSerializer;
import IE.CustomSerializer.CustomRestaurantSerializer;
import IE.Exceptions.DifRestaurants;
import IE.Exceptions.NoRestaurant;
import IE.Exceptions.WrongFood;
import IE.models.Food;
import IE.models.Order;
import IE.models.Restaurant;
import IE.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Loghme {
    private ArrayList<Restaurant> AllRestaurants;
    private User AppUser;



    public static void addToCart(Food food, User user, ObjectMapper mapper, ArrayList<Restaurant> restaurants, ArrayList<Order> cart) throws NoRestaurant, WrongFood, DifRestaurants {

        //Food food = mapper.readValue(Order, Food.class);
        if (restaurants.size() == 0) {
            //System.out.println("there is no restaurants to choose");
            throw new NoRestaurant("there is no restaurants to choose");
            //return user;
        }
        boolean UnknownFood = true;
        Restaurant rest = new Restaurant();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(food.getRestaurantName())) {
                rest = restaurant;
                UnknownFood = false;

            }
        }
        if (UnknownFood) {
            //System.out.println("there is no restaurant with that name");
            //return user;
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
            //System.out.println("no food with this name in this restaurant");
            //return user;
            throw new WrongFood("no food with this name in this restaurant");
        }


        boolean addable = false;

        for (Order value : cart) {
            //System.out.println(food.getRestaurantName());
            if (value.getRestaurantName().equals(food.getRestaurantName())) {
                addable = true;
                break;
            }
        }

        if (cart.size() == 0) {
            addable = true;
        }

        boolean SameFood = false;
        if (addable) {
            for (Order order : user.getOrders()) {
                if (order.getFoodName().equals(food.getName())) {
                    order.AddNum();
                    return;
                    //mapper.writeValue(new File(cartPath), cart);
                }
            }
            Order order = new Order(food.getName(), food.getRestaurantName(), 1, food.getPrice());
//                order.setFoodName(food.getName());
//                order.setRestaurantName(food.getRestaurantName());
//                order.setNumOfOrder(1);
//                cart.add(order);
            user.AddToCart(order);
            //System.out.println(order.getRestaurantName());
            //mapper.writeValue(new File(cartPath), cart);
        } else {
            //System.out.println("different restaurant");
            //return user;
            throw new DifRestaurants("you can not choose different restaurant");
        }
    }

    public static void finalizeOrder(User user, ObjectMapper mapper, ArrayList<Order> cart,float totalPrice) throws IOException {
        SimpleModule module =
                new SimpleModule();
        module.addSerializer(Order.class, new CustomCartSerializer());
        mapper.registerModule(module);
        String cartJson = mapper.writeValueAsString(cart);
        //System.out.println(cartJson);
        System.out.println("Order recorded successfully");
        cart.clear();
        user.setWallet(user.getWallet()-totalPrice);
        user.cleanOrders();
        //mapper.writeValue(new File(cartPath), cart);
    }

    public static void addRestaurant(String Order, String restaurantsPath, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
        boolean Addable = true;
        for (Restaurant value : restaurants) {
            if (value.getName().equals(restaurant.getName())) {
                Addable = false;
                break;
            }
        }
        if (Addable) {
            //System.out.println("hi");
            for (int i = 0; i < restaurant.getMenu().size(); i++) {
                restaurant.getMenu().get(i).setRestaurantName(restaurant.getName());
            }
            restaurants.add(restaurant);
            mapper.writeValue(new File(restaurantsPath), restaurants);
        }
    }

    public static void addFood(String Order, String restaurantsPath, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Food food = mapper.readValue(Order, Food.class);
        //System.out.println(food.getRestaurantName());
        boolean Addable = false;
        int index = -1;
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getName().equals(food.getRestaurantName())) {
                Addable = true;
                index = i;
            }
        }
        if (!Addable) {
            System.out.println("restaurant not found");
        }
        if (Addable) {
            for (int i = 0; i < restaurants.get(index).getMenu().size(); i++) {
                if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())) {
                    Addable = false;
                    break;
                }
            }
        }
        if (Addable) {
            restaurants.get(index).addToMenu(food);
            mapper.writeValue(new File(restaurantsPath), restaurants);

        }
    }

    public static void getRestaurants(ArrayList<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName());
        }
    }

    public static void getRestaurant(String Order, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        //System.out.println(Order);
        Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
        SimpleModule module =
                new SimpleModule();
        module.addSerializer(Restaurant.class, new CustomRestaurantSerializer());
        mapper.registerModule(module);
        //System.out.println(restaurant.getName());
        boolean getable = false;

        for (Restaurant value : restaurants) {
            if (value.getName().equals(restaurant.getName())) {

                getable = true;
                String json = mapper.writeValueAsString(value);
                System.out.println(json);
                break;
            }
        }
        if (!getable) {
            System.out.println("restaurant not found");
        }

    }

    public static void getFood(String Order, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Food food = mapper.readValue(Order, Food.class);
        SimpleModule module =
                new SimpleModule();
        module.addSerializer(Food.class, new CustomFoodSerializer());
        mapper.registerModule(module);
        boolean getable = false;
        int index = -1;
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getName().equals(food.getRestaurantName())) {
                index = i;
                getable = true;
            }
        }
        if (getable) {
            for (int i = 0; i < restaurants.get(index).getMenu().size(); i++) {
                if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())) {
                    String json = mapper.writeValueAsString(restaurants.get(index).getMenu().get(i));
                    System.out.println(json);
                    break;
                }
            }
        } else {
            System.out.println("restaurant not found");
        }
    }

    public static void getCart(ObjectMapper mapper, ArrayList<Order> cart) throws IOException {
        SimpleModule module =
                new SimpleModule();
        module.addSerializer(Order.class, new CustomCartSerializer());
        mapper.registerModule(module);
        String cartJson = mapper.writeValueAsString(cart);
        System.out.println(cartJson);
        //String json = mapper.writeValueAsString(cart);
        //System.out.println(json);
    }

    public static Set<String> getRecommendedRestaurants(ArrayList<Restaurant> restaurants) {
        HashMap<String, Double> suggestions = new HashMap<>();
        for (Restaurant restaurant : restaurants) {
            suggestions.put(restaurant.getName(), restaurant.calculatePopularity());
            //System.out.println(restaurants.get(i).calculatePopularity());
        }
        Set<String> restaurantNames = new HashSet<>();
        if (restaurants.size() < 3) {
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant.getName());
                restaurantNames.add(restaurant.getName());
            }
            return restaurantNames;
        }
        for (int i = 0; i < 3; i++) {
            Map.Entry<String, Double> maxEntry = null;

            for (Map.Entry<String, Double> entry : suggestions.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            if (maxEntry != null) {
                System.out.println(maxEntry.getKey());
                suggestions.remove(maxEntry.getKey());
                restaurantNames.add(maxEntry.getKey());
            }
        }
        return restaurantNames;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Order> NewOrders = new ArrayList<>();
        User user = new User();
        user.setOrders(NewOrders);
        label:
        while (true) {
            Scanner input = new Scanner(System.in);
            String myString = input.next();
            String Order = input.nextLine();
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                    , new TypeReference<List<Restaurant>>() {
                    });
            ArrayList<IE.models.Order> cart = mapper.readValue(new File("src/main/resources/cart.json")
                    , new TypeReference<List<Order>>() {
                    });
            switch (myString) {
                case "exit":
                    break label;
                case "addRestaurant":
                    Loghme.addRestaurant(Order, "src/main/resources/restaurants.json", mapper, restaurants);
                    break;
                case "addFood":
                    Loghme.addFood(Order, "src/main/resources/restaurants.json", mapper, restaurants);
                    break;
                case "getRestaurants":
                    Loghme.getRestaurants(restaurants);
                    break;
                case "getRestaurant":
                    Loghme.getRestaurant(Order, mapper, restaurants);
                    break;
                case "getFood":
                    Loghme.getFood(Order, mapper, restaurants);
                    break;
                case "getCart":
                    Loghme.getCart(mapper, cart);
                    break;
                case "getRecommendedRestaurants":
                    Loghme.getRecommendedRestaurants(restaurants);
                    break;
                default:
                    System.out.println("wrong command");
                    break;
            }
        }
    }
}
