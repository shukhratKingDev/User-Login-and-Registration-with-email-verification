package com.company.springsecurity_jwt_emailsending.security;

import com.company.springsecurity_jwt_emailsending.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final long expirationTime=60000000;
    private static final String key="Secret";
    public String generateToken(String username, Set<Role> roles){
String token= Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
        .claim("roles",roles)
        .signWith(SignatureAlgorithm.HS512,key)
        .compact();
return token;
    }

    public String getEmailFromToken(String token){
        try{
            String email = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            return email;
        }catch (Exception e){
            return null;
        }
    }
}
