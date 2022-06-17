package com.rongmei.security.jwt;

import com.rongmei.publicdatas.account.Role;
import com.rongmei.response.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.claimKey.username}")
  private String claimKeyUsername;

  @Value("${jwt.claimKey.authorities}")
  private String claimKeyAuthorities;

  @Value("${jwt.secret}")
  private String secret;

  @Override
  public Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      e.printStackTrace();
      claims = null;
    }
    return claims;
  }

  @Override
  public String getUsernameFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return (String) claims.get(claimKeyUsername);
  }

  @Override
  public JwtUser convertUserToJwtUser(User user) {
    return new JwtUser(
        user.getPhone(),
        "",
        mapToJwtRole(user.getRole())
    );
  }


  private List<JwtRole> mapToJwtRole(Role role) {
    List<Role> roles = new ArrayList<>();
    roles.add(role);
    return roles.stream()
        .map(JwtRole::new)
        .collect(Collectors.toList());
  }


  @Override
  public Date generateExpirationDate(long expiration) {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  @Override
  public boolean validateToken(String authToken) {
    Claims claims = getClaimsFromToken(authToken);
    return new Date().getTime() <= claims.getExpiration().getTime();
  }

  @Override
  public String generateToken(UserDetails userDetails, long expiration) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(claimKeyUsername, userDetails.getUsername());
    claims.put(claimKeyAuthorities, userDetails.getAuthorities());
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generateExpirationDate(expiration))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
}
