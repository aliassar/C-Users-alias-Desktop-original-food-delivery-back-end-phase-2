package IE.Servlets;

import IE.Loghme;
import IE.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        request.setAttribute("cartsOfUser", user.getCartsOfUser());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loghme loghme = Loghme.getInstance();
        String amount = request.getParameter("credit");
        loghme.increaseWallet(Float.parseFloat(amount));
        User user = loghme.getAppUser();
        request.setAttribute("user", user);
        request.setAttribute("cartsOfUser", user.getCartsOfUser());
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}

