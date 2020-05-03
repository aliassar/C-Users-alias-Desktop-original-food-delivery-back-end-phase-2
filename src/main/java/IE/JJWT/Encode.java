package IE.JJWT;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Encode {

    private static String SECRET_KEY = "loghme";

    public static String createJWT(String id, String subject, String userID) throws UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        long nowMillis = System.currentTimeMillis();
        Date exp = new Date(nowMillis + TimeUnit.DAYS.toMillis(1));
        Date now = new Date(nowMillis);
        String token = JWT.create()
                .withIssuer("loghme_back")
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .withSubject(subject)
                .withClaim("userID", userID)
                .sign(algorithm);
        return token;

    }
    public static DecodedJWT decodeJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("loghme_back")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;

    }
//    public static void main(String args[]) throws UnsupportedEncodingException  //static method
//    {
//        String code = createJWT("hi","hi","hi");
//        System.out.println(code);
//        DecodedJWT Decode = decodeJWT(code.toString());
//        Claim claim = Decode.getClaim("userID");
//        System.out.println(claim.asString());
//
//    }


}
