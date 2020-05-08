package IE.JJWT;

import IE.Loghme;
import IE.model.User;
import IE.model.UserMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.net.MalformedURLException;
import java.sql.SQLException;


public class Validate {
    private static String SECRET_KEY = "loghme";
    private static String ISSUER = "loghme_back";

    public static User decodeJWT(String token) throws JWTVerificationException,SQLException,MalformedURLException  {
        Loghme loghme = Loghme.getInstance();
        if (token == null){

            throw new  JWTVerificationException("token is empty");

        }

        try{

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String email = jwt.getSubject();
            UserMapper userMapper = loghme.getUserMapper();
            User user = userMapper.find(email);

            return user;



        }catch (JWTVerificationException | SQLException | MalformedURLException e){
            throw e;
        }



    }
}
