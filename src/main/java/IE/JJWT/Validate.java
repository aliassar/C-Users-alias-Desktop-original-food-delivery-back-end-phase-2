package IE.JJWT;

import IE.Loghme;
import IE.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


public class Validate {
    private static String SECRET_KEY = "loghme";
    private static String ISSUER = "loghme_back";

    public static User decodeJWT(String token) throws JWTVerificationException  {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String email = jwt.getSubject();
            // TODO : get user from database or throw exception
            Loghme loghme = Loghme.getInstance();
            User user = loghme.getAppUser();
            return user;



        }catch (JWTVerificationException exception){
            throw exception;
        }



    }
}
