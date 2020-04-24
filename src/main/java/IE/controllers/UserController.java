package IE.controllers;

import IE.Loghme;
import IE.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @RequestMapping(value = "/user",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User GetUser() {
        Loghme loghme = Loghme.getInstance();
        return loghme.getAppUser();

    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void AddToWallet(@RequestParam(value = "credit") float amount){

        Loghme loghme = Loghme.getInstance();
        loghme.increaseWallet(amount);


    }


}
