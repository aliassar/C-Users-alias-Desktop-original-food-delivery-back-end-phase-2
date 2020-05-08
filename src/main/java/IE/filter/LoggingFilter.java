package IE.filter;
import IE.JJWT.Validate;
import IE.model.User;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

@WebFilter(filterName = "loggingFilter")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token =  ((HttpServletRequest) request).getHeader("token");

        try {
            User user =Validate.decodeJWT(token);
            request.setAttribute("user", user);
            chain.doFilter(request,response);
        }catch (JWTVerificationException | SQLException | MalformedURLException error){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);

        }


        //System.out.println("REST: " + httpRequest); // method + URI;

        //chain.doFilter(request, response);

    }
}
