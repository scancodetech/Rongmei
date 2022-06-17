package com.rongmei.security.jwt;

import com.rongmei.response.user.User;
import com.rongmei.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

  private final JwtService jwtService;

  @Autowired
  public JwtUserDetailsServiceImpl(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Locates the user based on the username. In the actual implementation, the search may possibly
   * be case sensitive, or case insensitive depending on how the implementation instance is
   * configured. In this case, the <code>UserDetails</code> object that comes back may have a
   * username that is of a different case than what was actually requested..
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   * GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = UserInfoUtil.getUser(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      return jwtService.convertUserToJwtUser(user);
    }
  }
}
