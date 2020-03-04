package IE.Servlets;

import IE.Exceptions.*;
import IE.Loghme;
import IE.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        Cart cart = user.getInProcessCart();
        if(cart.getOrders().size()>0){
            float estimatedArrive = 10000;
            try {
                Restaurant chosenRestaurant = loghme.FindRestaurant(cart.getRestaurantID());
                estimatedArrive = loghme.EstimateArivingTime(chosenRestaurant.getLocation());
            } catch (NoRestaurant e) {
                try {
                    FoodPartyRestaurant chosenFoodPartyRestaurants = loghme.FindFoodPartyRestaurant(cart.getRestaurantID());
                    estimatedArrive = loghme.EstimateArivingTime(chosenFoodPartyRestaurants.getLocation());
                } catch (NoRestaurant error) {
                    error.printStackTrace();
                }
            }
            request.setAttribute("estimatedArrive", estimatedArrive);
        }
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        String name =  request.getParameter("name");
        String restaurantName = request.getParameter("restaurantName");
        String restaurantID = request.getParameter("ID");
        String type =  request.getParameter("type");
        String oldPrice =  request.getParameter("oldPrice");
        String count =  request.getParameter("count");
        float price = Float.parseFloat(Objects.requireNonNull(request.getParameter("price")));
        try {
            if(type!=null && type.equals("foodParty")){
                FoodParty foodParty = new FoodParty(name, restaurantName, price,Float.parseFloat(oldPrice),Integer.parseInt(count));
                loghme.FoodPartyaddToCart(foodParty,restaurantID);
            }
            else{
                Food food = new Food(name, restaurantName, price);
                loghme.addToCart(food, restaurantID);
            }
        } catch (NoRestaurant | WrongFood | DifRestaurants | NoFoodRemained e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/exception.jsp").forward(request, response);
        }
        User user = loghme.getAppUser();
        Cart cart = user.getInProcessCart();
        float estimatedArrive = 10000;
        try {
            Restaurant chosenRestaurant = loghme.FindRestaurant(cart.getRestaurantID());
            estimatedArrive = loghme.EstimateArivingTime(chosenRestaurant.getLocation());
        } catch (NoRestaurant e) {
            try {
                FoodPartyRestaurant chosenFoodPartyRestaurants = loghme.FindFoodPartyRestaurant(cart.getRestaurantID());
                estimatedArrive = loghme.EstimateArivingTime(chosenFoodPartyRestaurants.getLocation());
            } catch (NoRestaurant error) {
                error.printStackTrace();
            }
        }
        request.setAttribute("cart", cart);
        request.setAttribute("estimatedArrive", estimatedArrive);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}

