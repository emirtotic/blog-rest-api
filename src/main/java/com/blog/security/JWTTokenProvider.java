package com.blog.security;

import com.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    /**
     * 1. Step
     * This method is used for generating a JWT token.
     *
     * @param authentication object for getting the name of the user
     * @return String - Token which includes username, date, expireDate and algorithm
     */
    public String generateToken(Authentication authentication) {

        log.info("Generating a JWT token...");

        String username = authentication.getName();
        Date date = new Date();
        Date expireDate = new Date(date.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        log.info("BEARER TOKEN : {}", token);

        return token;
    }

    /**
     * 2. Step
     * This method is used for getting the username from token
     *
     * @param token String from where the username will be taken.
     * @return String - claims.getSubject().
     */
    public String getUsernameFromJwt(String token) {

        log.info("Getting the username from JWT token...");

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 3. Step
     * This method is used for validating the token
     *
     * @param token - String that needs to be validated
     * @return boolean
     */
    public boolean validateToken(String token) {

        log.info("Validating the JWT token...");

        try {
            Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature!");
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature!");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Signature!");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Signature!");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT Signature is empty!");
        }
    }

}
