package com.rongmei.security.jwt;

import com.rongmei.response.user.User;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  Claims getClaimsFromToken(String token);

  String getUsernameFromToken(String token);

  JwtUser convertUserToJwtUser(User user);

  Date generateExpirationDate(long expiration);

  boolean validateToken(String authToken);

  String generateToken(UserDetails userDetails, long expiration);
}
