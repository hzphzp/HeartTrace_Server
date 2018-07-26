package Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JavaWebToken {
    public static String createToken(String username, String modelnum){
        String token = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date isa = new Date();
            Date nbf = new Date();
            Date exp = new Date();
            nbf.setTime(isa.getTime() + 1000);
            exp.setTime(isa.getTime() + 86400000L);//设置有效期为一天
            token = JWT.create()
                    .withIssuer("huangzp")
                    .withAudience(username)
                    .withSubject(modelnum)
                    .withIssuedAt(isa)
                    .withNotBefore(nbf)
                    .withExpiresAt(exp)
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }
        return token;
    }
    public static void verifyToken(String token, String username, String modelnum){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("huangzp")
                    .withAudience(username)
                    .withSubject(modelnum)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        }catch (JWTCreationException exception){
            exception.printStackTrace();
        }
    }
}
