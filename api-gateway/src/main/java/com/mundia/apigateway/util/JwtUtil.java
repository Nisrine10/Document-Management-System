package com.mundia.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
@Component
public class JwtUtil {
    private final String SECRET_KEY="DmBpzIhc1gDj05ZlarYM77MJhDvI78znZ39emiYO8M2n4xIDu1V3wxuDuNk6J4K6dyL0MfvCvp5NtnD4KLoSV7wwiBc1TAq0IZjA8yRpo0vc3tRcvPS7h5WJYizd57HevpjA2E7qoNOiEhCCcaXoe2JveKEB1PyHMmAb34QfibjDmtVEIzdJ2LsRIK2DSr8ga6gGOir7mUXzG0hwdD5AfOPsAtSMzxpT0R9IUymiUzp7iooRRKXNyl6RsfHA17L7";

    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);

    }
}
