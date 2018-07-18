package Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JavaWebToken {
    public static String createToken(){
        String token = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create().withIssuer("huangzp").sign(algorithm);
        }catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }
        return token;
    }
    public static void verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("huangzp").build();
            DecodedJWT jwt = verifier.verify(token);
        }catch (JWTCreationException exception){
            exception.printStackTrace();
        }
    }
}
