package com.rongmei.response.certification;

import com.rongmei.entity.certification.UserCertification;
import com.rongmei.response.Response;
import java.util.List;

public class UserCertificationGetResponse extends Response {

  private List<UserCertification> userCertificationList;

  public UserCertificationGetResponse() {
  }

  public UserCertificationGetResponse(
      List<UserCertification> userCertificationList) {
    this.userCertificationList = userCertificationList;
  }

  public List<UserCertification> getUserCertificationList() {
    return userCertificationList;
  }

  public void setUserCertificationList(
      List<UserCertification> userCertificationList) {
    this.userCertificationList = userCertificationList;
  }
}
