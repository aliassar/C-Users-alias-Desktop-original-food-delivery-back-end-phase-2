package IE.Servlets;

import IE.Loghme;
import IE.models.FoodPartyRestaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/foodparty")
public class FoodPartyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        ArrayList<FoodPartyRestaurant> restaurants = loghme.getFoodPartyRestaurants();
        request.setAttribute("restaurants",restaurants);
        request.getRequestDispatcher("/foodparty.jsp").forward(request, response);
    }
}

