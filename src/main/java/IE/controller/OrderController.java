package IE.controller;


import IE.exceptions.*;
import IE.Loghme;
import IE.model.Cart;
import IE.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class OrderController {
    @RequestMapping(value = "/order",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetAllOrders() {
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        try {
            Cart cart = user.getLastCart();
            return ResponseEntity.status(HttpStatus.OK).body(cart);
        } catch (NoOrder noOrder) {
            noOrder.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noOrder.getMessage());
        }
    }

    @RequestMapping(value = "/order/{OrderId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cart GetOrder(@PathVariable(value = "OrderId") int OrderId){
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        Cart cart = user.getCartsOfUser().get(OrderId);
        return cart;

    }

    @RequestMapping(value = "/order",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> FinalizeOrders(){
        Loghme loghme = Loghme.getInstance();
        try {
            loghme.finalizeOrder();
            User user = loghme.getAppUser();
            Cart cart = user.getLastCart();
            return ResponseEntity.status(HttpStatus.OK).body(cart);
        } catch (InsufficientMoney | NoOrder | EmptyCart | IOException | SQLException e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }
}
