package IE.controller;

import IE.Loghme;
import IE.model.Cart;
import IE.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {
    @RequestMapping(value = "/user",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public float GetWallet() {
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        return user.getWallet();

    }

    @RequestMapping(value = "/user/cart",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Cart> GetCartsOfUser() {
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        return user.getCartsOfUser();

    }




    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void AddToWallet(@RequestParam(value = "credit") float amount){

        Loghme loghme = Loghme.getInstance();
        loghme.increaseWallet(amount);


    }


}
