package IE.controller;

import IE.JJWT.Encode;
import IE.Loghme;
import IE.model.Cart;
import IE.model.User;
import IE.model.UserMapper;
import IE.password.Password;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class UserController {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/user",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public float GetWallet() {
        //System.out.println("hi");
        Loghme loghme = Loghme.getInstance();
        User user = loghme.getAppUser();
        return user.getWallet();

    }

    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> LoginUser(@RequestParam(value = "email") String email,
                           @RequestParam(value = "password") String password) {
        //System.out.println("hi");
        Loghme loghme = Loghme.getInstance();
        UserMapper userMapper = loghme.getUserMapper();
        try {
            User user = userMapper.find(email);
            try {
                boolean correctPass = Password.check(password,user.getPassword());
                if (!correctPass){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong Password");
                }
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong Password");
            }

            String token = Encode.createJWT(user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (SQLException | MalformedURLException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found");
        }

    }

    @RequestMapping(value = "/register",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> RegisterUser(@RequestAttribute( value = "email") String email,
                                          @RequestAttribute(value = "password") String password,
                                          @RequestAttribute(value = "firstName") String firstName,
                                          @RequestAttribute(value = "lastName") String lastName) throws SQLException, MalformedURLException {
        //System.out.println("hi");
        Loghme loghme = Loghme.getInstance();
        UserMapper userMapper = loghme.getUserMapper();
        ArrayList<User> users = userMapper.getAll();
        for (int i = 0; i<users.size(); i++){
            if (email.equals(users.get(i).getEmail())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email has been taken");
            }
        }
        User user;
        try {
            user = new User(firstName,lastName,email,password,0,"0");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("problem with password");
        }

        userMapper.insert(user);
        String token = Encode.createJWT(email);
        return ResponseEntity.status(HttpStatus.OK).body(token);



    }


    @RequestMapping(value = "/user/cart",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Cart> GetCartsOfUser(@RequestAttribute(value = "user") User user) {
        //Loghme loghme = Loghme.getInstance();
        //User user = loghme.getAppUser();
        return user.getCartsOfUser();

    }




    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void AddToWallet(@RequestParam(value = "credit") float amount,
                            @RequestAttribute(value = "user") User user){
        user.AddToWallet(amount);

//        Loghme loghme = Loghme.getInstance();
//        loghme.increaseWallet(amount);


    }


}
