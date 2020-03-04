package IE.Servlets;

import IE.Exceptions.EmptyCart;
import IE.Exceptions.InsufficientMoney;
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


@WebServlet({"/order", "/order/*"})
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        if (pathInfo == null) {
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
        else{
            String[] pathParts = pathInfo.split("/");
            String orderId = pathParts[1];
            Cart cart = user.getCartsOfUser().get(Integer.parseInt(orderId));
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        try {
            loghme.finalizeOrder();
            User user = loghme.getAppUser();
            Cart cart = user.getLastCart();
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (InsufficientMoney | NoOrder | EmptyCart e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/exception.jsp").forward(request, response);
        }
    }
}

