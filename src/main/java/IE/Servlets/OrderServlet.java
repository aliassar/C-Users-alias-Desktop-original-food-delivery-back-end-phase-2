package IE.Servlets;

import IE.Exceptions.InsufficientMoney;
import IE.Exceptions.NoInProcessOrder;
import IE.Exceptions.NoOrder;
import IE.Loghme;
import IE.models.Cart;
import IE.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        try {
            Cart cart = user.getLastCart();
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (NoOrder noOrder) {
            noOrder.printStackTrace();
            request.setAttribute("error", noOrder.getMessage());
            request.getRequestDispatcher("/exception.jsp").forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Loghme loghme = Loghme.getInstance();
        try {
            loghme.finalizeOrder();
            User user = loghme.getAppUser();
            Cart cart = user.getInProcessCart();
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (NoInProcessOrder | InsufficientMoney | NoOrder e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/exception.jsp").forward(request, response);
        }
    }
}

