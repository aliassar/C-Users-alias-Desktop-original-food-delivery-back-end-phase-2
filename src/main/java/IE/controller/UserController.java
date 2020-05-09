package IE.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import IE.JJWT.Encode;
import IE.Loghme;
import IE.model.Cart;
import IE.model.User;
import IE.model.UserMapper;
import IE.password.Password;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

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

    @RequestMapping(value = "/googleAuth",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetWallet(@RequestAttribute( value = "googleIdToken") GoogleIdToken token) {
        //System.out.println("hi");
        Loghme loghme = Loghme.getInstance();
        JacksonFactory jacksonFactory = new JacksonFactory();
        HttpTransport transport = new ApacheHttpTransport();



        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport,jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("ID"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

        try {

            GoogleIdToken idToken = null;
             boolean flag = verifier.verify(token);

            if (flag) {
                Payload payload = token.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                //boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                UserMapper userMapper = loghme.getUserMapper();
                try {
                    User user = userMapper.find(email);
                    String JWTtoken = Encode.createJWT(user.getEmail());
                    return ResponseEntity.status(HttpStatus.OK).body(JWTtoken);

                }catch (SQLException e){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no user with that email");
                }





            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("bad token");
            }}catch (GeneralSecurityException | IOException e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

            }



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
