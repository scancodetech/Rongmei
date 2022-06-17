package com.rongmei.security.jwt;

import com.rongmei.entity.account.Employees;
import com.rongmei.entity.account.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    Claims getClaimsFromToken(String token);

    String getUsernameFromToken(String token);

    JwtUser convertUserToJwtUser(User user);

    JwtEmployees convertUserToJwtEmployees(Employees employees);

    Date generateExpirationDate(long expiration);

    boolean validateToken(String authToken);

    String generateToken(UserDetails userDetails, long expiration);
}
