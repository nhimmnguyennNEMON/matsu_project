/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author pallgree
 */
public class Jwt {



    public String createJwt(String _email,int _role_id) throws IllegalArgumentException, UnsupportedEncodingException {

        try {
            Algorithm algorithm = Algorithm.HMAC256(env.JWT_ACCESS_KEY);
            String token = JWT.create()
                    .withClaim("email", _email)
                    .withClaim("role_id", _role_id)
                    .withExpiresAt(new Date(System.currentTimeMillis() + env.TIME_OUT_COOKIE_JWT)) // gia hạn cho tiken kéo dài 1h
                    .sign(algorithm);

           return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        return "";
    }
    
    public boolean verifyJwt(String _token) throws IllegalArgumentException, UnsupportedEncodingException{
        try {
            Algorithm algorithm = Algorithm.HMAC256(env.JWT_ACCESS_KEY); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT jwt = verifier.verify(_token);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }

    }
    public String getEmailFromToken(String _token) throws IllegalArgumentException, UnsupportedEncodingException{
        String data= null;
        Map<String, Claim> claims;
        try {
            Algorithm algorithm = Algorithm.HMAC256(env.JWT_ACCESS_KEY); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT jwt = verifier.verify(_token);
            claims = jwt.getClaims();
            //System.out.println(claims.get(field).asString());
            return claims.get("email").asString();
        } catch (JWTVerificationException exception){
            
        }
       return data;
    }
    
     public int getRoleFromToken(String _token) throws IllegalArgumentException, UnsupportedEncodingException{
        int data = 0;
        Map<String, Claim> claims;
        try {
            Algorithm algorithm = Algorithm.HMAC256(env.JWT_ACCESS_KEY); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT jwt = verifier.verify(_token);
            claims = jwt.getClaims();
            //System.out.println(claims.get(field).asString());
            return claims.get("role_id").asInt();
        } catch (JWTVerificationException exception){
            
        }
       return data;
    }


}
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlX2lkIjo0LCJleHAiOjE2NTI5MzU1MjksImVtYWlsIjoiaHVuZ3Z0MTY0QGdtYWlsLmNvbSJ9.tIqzGZu9YesAO66qnZEkP0jXZXmbtp2agnsiSauEOCM
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlX2lkIjo0LCJleHAiOjE2NTI5MzU1NDIsImVtYWlsIjoiaHVuZ3Z0MTY0QGdtYWlsLmNvbSJ9.MxrhSwH_ZK9ushAtkPtRIt9U2V-xEzUWUFC9NOVHXMM