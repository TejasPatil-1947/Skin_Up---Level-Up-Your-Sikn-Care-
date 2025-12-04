package com.skincare.Services.Impl;

import com.skincare.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public Long extractUserId(String jwtToken){

        String userId = extractClaim(jwtToken,claims -> claims.get("userId",String.class));
        return userId != null ? Long.parseLong(userId):null;
    }

    public String extractUsername(String jwtToken){
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user){
        return generateToken(new HashMap(),user);
    }

    public String generateToken(Map<String, Object> extraClaims,User user){
        Map<String,Object> claims = new HashMap<>(extraClaims);
        claims.put("userId",user.getId().toString());

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String jwtToken,User user){
        final Long UserIdFromToken = extractUserId(jwtToken);
        final Long userIdDetails = user.getId();
        return (UserIdFromToken != null && UserIdFromToken.equals(userIdDetails)
            && !isTokenExpired(jwtToken)
        );
    }
    private boolean isTokenExpired(String jwtToken){
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwt){
            return extractClaim(jwt,Claims::getExpiration);
    }
}
