package IE.Servlets;

import IE.Loghme;
import IE.models.Restaurant;
import IE.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/restaurants")
public class RestaurantsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Loghme loghme = Loghme.getInstance();
        ArrayList<Restaurant> restaurants = loghme.getAllRestaurants();
        request.setAttribute("restaurants", restaurants);
        request.getRequestDispatcher("/restaurants.jsp").forward(request, response);
    }
}

