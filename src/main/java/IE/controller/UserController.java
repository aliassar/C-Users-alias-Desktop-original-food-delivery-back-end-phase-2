package IE.controller;

import IE.Loghme;
import IE.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @CrossOrigin(origins = "http://localhost:3000")
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
