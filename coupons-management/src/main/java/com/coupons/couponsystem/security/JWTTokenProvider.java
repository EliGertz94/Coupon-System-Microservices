package com.coupons.couponsystem.security;

import com.coupons.couponsystem.exception.CouponSystemException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTTokenProvider {

    public String generateToken(SecuredUser userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String ,Object> extraClaims, SecuredUser userDetails){

        return Jwts.
                builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("authority",userDetails.getAuthorities().stream().toList().get(0).getAuthority())
                .claim("userId",userDetails.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // make an instance constant
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }



    public String getUserNameFromJwt(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token,SecuredUser user) throws CouponSystemException {
        try {
            String userName = getUserNameFromJwt(token);
          //  Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token);
            return (userName.equals(user.getUsername())&& !isTokenExpired(token));

        }catch (ExpiredJwtException e){
            throw new CouponSystemException("JWT was expired",HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException(" incorrect jwt ");
        }
    }

    private boolean isTokenExpired(String token) {  
            return extractExpiration(token).before(new Date()); 
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey(){
      byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
      return Keys.hmacShaKeyFor(keyBytes);
    }

}
