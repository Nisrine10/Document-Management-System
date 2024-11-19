package com.mundia.mssecurity.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
//    private final static Dotenv dotenv = Dotenv.load();
//    private final static String SECRET_KEY = dotenv.get("SECRET_KEY");

    private final String SECRET_KEY="DmBpzIhc1gDj05ZlarYM77MJhDvI78znZ39emiYO8M2n4xIDu1V3wxuDuNk6J4K6dyL0MfvCvp5NtnD4KLoSV7wwiBc1TAq0IZjA8yRpo0vc3tRcvPS7h5WJYizd57HevpjA2E7qoNOiEhCCcaXoe2JveKEB1PyHMmAb34QfibjDmtVEIzdJ2LsRIK2DSr8ga6gGOir7mUXzG0hwdD5AfOPsAtSMzxpT0R9IUymiUzp7iooRRKXNyl6RsfHA17L7";

    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return  claims.getSubject();
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
      return Jwts.builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)) // valide pour 24h
              .signWith(getSigningKey(), SignatureAlgorithm.HS256)
              .compact();
    }
    //----------générer token à partir de userDetail----
    public String generateToken(UserDetails userDetails){

        return  generateToken(new HashMap<>(),userDetails);
    }

    //-------------------------------
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return ExtractExpiration(token).before(new Date());
    }

    private Date ExtractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }


    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
