package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JWTServiceIMPL implements JWTService {
    @Value("${spring.jwtKey}")
    String jwtKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }
    @Override
    public String generateToken(UserDetails userDetails) {
        return genToken(new HashMap<>(),userDetails);
    }

    private String genToken(Map<String, Object> genClaims, UserDetails userDetails) {
        genClaims.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 600);
        return Jwts.builder().setClaims(genClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(),userDetails);
    }

    private String refreshToken(Map<String, Object> genClaimsRefresh, UserDetails userDetails) {
        genClaimsRefresh.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date refreshExpire = new Date(now.getTime() + 1000 * 600 * 600);
        return Jwts.builder().setClaims(genClaimsRefresh)
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolve) {
        final Claims claims = getClaims(token);
        return claimsResolve.apply(claims);
    }
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJwt(token)
                .getBody();
    }

    private Key getSignKey() {
        byte [] decodedJWT = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decodedJWT);
    }

    private Key getKey() {
        byte [] decodedJWT = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decodedJWT);
    }
}
