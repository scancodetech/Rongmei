package com.rongmei.blservice.account;

import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.user.UserSecurityUpdateInnerParameters;
import com.rongmei.parameters.user.UserSecurityUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserAdvancedSecurityGetResponse;
import com.rongmei.response.user.UserBasisSecurityGetResponse;

public interface UserSecurityBlService {

  UserBasisSecurityGetResponse getUserBasisSecurity() throws UsernameDoesNotFoundException;

  UserAdvancedSecurityGetResponse getUserAdvancedSecurityByCaptcha(String captcha)
      throws WrongUsernameOrPasswordException, UsernameDoesNotFoundException;

  UserAdvancedSecurityGetResponse getUserAdvancedSecurityByPayCode(String payCode)
      throws WrongUsernameOrPasswordException, UsernameDoesNotFoundException;

  SuccessResponse updateUserSecurity(UserSecurityUpdateParameters parameters)
      throws WrongUsernameOrPasswordException;

  UserAdvancedSecurityGetResponse getUserSecurity(String username)
      throws UsernameDoesNotFoundException;

  SuccessResponse updateUserSecurityInner(UserSecurityUpdateInnerParameters parameters)
      throws UsernameDoesNotFoundException;
}
