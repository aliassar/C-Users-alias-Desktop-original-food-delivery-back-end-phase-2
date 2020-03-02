package IE.Servlets;

import IE.Exceptions.NoRestaurant;
import IE.Exceptions.OutOfBoundaryLocation;
import IE.Loghme;
import IE.models.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet({"/restaurants", "/restaurants/*"})
public class RestaurantServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        Loghme loghme = Loghme.getInstance();
        if (pathInfo == null) {
            ArrayList<Restaurant> restaurants = loghme.getNearbyRestaurants();
            request.setAttribute("restaurants", restaurants);
            request.getRequestDispatcher("/restaurants.jsp").forward(request, response);
        } else {

            String[] pathParts = pathInfo.split("/");
            String restaurantId = pathParts[1];
            try {
                Restaurant restaurant = loghme.getRestaurantInfo(restaurantId);
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
            } catch (OutOfBoundaryLocation outOfBoundaryLocation) {
                outOfBoundaryLocation.printStackTrace();
                request.setAttribute("error", outOfBoundaryLocation.getMessage());
            } catch (NoRestaurant noRestaurant) {
                noRestaurant.printStackTrace();
                request.setAttribute("error", noRestaurant.getMessage());
                request.getRequestDispatcher("/exception.jsp").forward(request, response);
            }
        }

    }
}

