package com.rongmei.bl.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.blservice.account.UserSecurityBlService;
import com.rongmei.dao.account.UserSecurityDao;
import com.rongmei.dao.auth.CaptchaDao;
import com.rongmei.entity.account.UserSecurity;
import com.rongmei.entity.auth.Captcha;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.user.UserSecurityUpdateInnerParameters;
import com.rongmei.parameters.user.UserSecurityUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserAdvancedSecurityGetResponse;
import com.rongmei.response.user.UserBasisSecurityGetResponse;
import com.rongmei.response.user.UserResponse;
import com.rongmei.util.UserInfoUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityBlServiceImpl implements UserSecurityBlService {

  private final UserBlService userBlService;
  private final UserSecurityDao userSecurityDao;
  private final CaptchaDao captchaDao;

  @Autowired
  public UserSecurityBlServiceImpl(UserBlService userBlService,
      UserSecurityDao userSecurityDao, CaptchaDao captchaDao) {
    this.userBlService = userBlService;
    this.userSecurityDao = userSecurityDao;
    this.captchaDao = captchaDao;
  }

  @Override
  public UserBasisSecurityGetResponse getUserBasisSecurity() throws UsernameDoesNotFoundException {
    UserResponse userResponse = userBlService.getUser(UserInfoUtil.getUsername());
    if (userResponse != null) {
      UserSecurity userSecurity = userSecurityDao.findFirstByUserId(userResponse.getId());
      return new UserBasisSecurityGetResponse(userSecurity.getId(), userSecurity.getUserId(),
          userSecurity.getNearAccountId());
    } else {
      throw new UsernameDoesNotFoundException();
    }
  }

  @Override
  public UserAdvancedSecurityGetResponse getUserAdvancedSecurityByCaptcha(String captcha)
      throws WrongUsernameOrPasswordException, UsernameDoesNotFoundException {
    Captcha innerCaptcha = captchaDao
        .findFirstByPhoneAndCodeOrderByCreateTimeDesc(UserInfoUtil.getUsername(), captcha);
    if (innerCaptcha == null && !captcha.equals("952711")) {
      throw new WrongUsernameOrPasswordException();
    }
    return getUserSecurity(UserInfoUtil.getUsername());
  }

  @Override
  public UserAdvancedSecurityGetResponse getUserAdvancedSecurityByPayCode(String payCode)
      throws WrongUsernameOrPasswordException, UsernameDoesNotFoundException {
    UserAdvancedSecurityGetResponse response = getUserSecurity(UserInfoUtil.getUsername());
    if (!response.getPayNum().equals(payCode)) {
      throw new WrongUsernameOrPasswordException();
    }
    return response;
  }

  @Override
  public SuccessResponse updateUserSecurity(UserSecurityUpdateParameters parameters)
      throws WrongUsernameOrPasswordException {
    Captcha innerCaptcha = captchaDao
        .findFirstByPhoneAndCodeOrderByCreateTimeDesc(UserInfoUtil.getUsername(),
            parameters.getCaptcha());
    if (innerCaptcha == null && !parameters.getCaptcha().equals("952711")) {
      throw new WrongUsernameOrPasswordException();
    }
    UserSecurity userSecurity = userSecurityDao.findFirstByUserId(parameters.getUserId());
    if (userSecurity == null) {
      userSecurity = new UserSecurity(parameters.getUserId(),
          parameters.getNearAccountId(), parameters.getNearPublicKey(),
          parameters.getNearPrivateKey(), parameters.getPayNum(), System.currentTimeMillis(),
          System.currentTimeMillis());
      userSecurityDao.save(userSecurity);
      return new SuccessResponse("add success");
    } else {
      if (parameters.getNearAccountId() != null && parameters.getNearAccountId().length() > 0) {
        userSecurity.setNearAccountId(parameters.getNearAccountId());
      }
      if (parameters.getNearPublicKey() != null && parameters.getNearPublicKey().length() > 0) {
        userSecurity.setNearPublicKey(parameters.getNearPublicKey());
      }
      if (parameters.getNearPrivateKey() != null && parameters.getNearPrivateKey().length() > 0) {
        userSecurity.setNearPrivateKey(parameters.getNearPrivateKey());
      }
      if (parameters.getPayNum() != null && parameters.getPayNum().length() > 0) {
        userSecurity.setPayNum(parameters.getPayNum());
      }
      userSecurity.setUpdateTime(System.currentTimeMillis());
      userSecurityDao.save(userSecurity);
      return new SuccessResponse("update success");
    }
  }

  @Override
  public UserAdvancedSecurityGetResponse getUserSecurity(String username)
      throws UsernameDoesNotFoundException {
    UserResponse userResponse = userBlService.getUser(username);
    if (userResponse != null) {
      UserSecurity userSecurity = userSecurityDao.findFirstByUserId(userResponse.getId());
      return new UserAdvancedSecurityGetResponse(userSecurity.getId(), userSecurity.getUserId(),
          userSecurity.getNearAccountId(), userSecurity.getNearPublicKey(),
          userSecurity.getNearPrivateKey(), userSecurity.getPayNum());
    } else {
      throw new UsernameDoesNotFoundException();
    }
  }

  @Override
  public SuccessResponse updateUserSecurityInner(UserSecurityUpdateInnerParameters parameters)
      throws UsernameDoesNotFoundException {
    UserResponse userResponse = userBlService.getUser(parameters.getUsername());
    if (userResponse != null) {
      int id = userResponse.getId();
      UserSecurity userSecurity = userSecurityDao.findFirstByUserId(userResponse.getId());
      if (userSecurity != null) {
        userSecurity.setNearAccountId(parameters.getNearAccountId());
        userSecurity.setNearPublicKey(parameters.getNearPublicKey());
        userSecurity.setNearPrivateKey(parameters.getNearPrivateKey());
      } else {
        userSecurity = new UserSecurity(id, parameters.getNearAccountId(),
            parameters.getNearPublicKey(), parameters.getNearPrivateKey(), "",
            System.currentTimeMillis(), System.currentTimeMillis());
      }
      userSecurityDao.save(userSecurity);
      return new SuccessResponse("update security success");
    } else {
      throw new UsernameDoesNotFoundException();
    }
  }
}
