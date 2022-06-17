package com.rongmei.response.certification;

import com.rongmei.entity.certification.UserCertification;
import com.rongmei.entity.certification.UserMasterpiece;
import com.rongmei.response.Response;
import java.util.List;

public class CertificationResponse extends Response {

  private boolean isUserCertificationChecked;
  private UserCertification userCertification;
  private boolean isUserMasterpieceChecked;
  private UserMasterpiece userMasterpiece;
  private List<String> userTypes;

  public CertificationResponse() {
  }

  public CertificationResponse(boolean isUserCertificationChecked,
      UserCertification userCertification, boolean isUserMasterpieceChecked,
      UserMasterpiece userMasterpiece, List<String> userTypes) {
    this.isUserCertificationChecked = isUserCertificationChecked;
    this.userCertification = userCertification;
    this.isUserMasterpieceChecked = isUserMasterpieceChecked;
    this.userMasterpiece = userMasterpiece;
    this.userTypes = userTypes;
  }

  public boolean isUserCertificationChecked() {
    return isUserCertificationChecked;
  }

  public void setUserCertificationChecked(boolean userCertificationChecked) {
    isUserCertificationChecked = userCertificationChecked;
  }

  public UserCertification getUserCertification() {
    return userCertification;
  }

  public void setUserCertification(UserCertification userCertification) {
    this.userCertification = userCertification;
  }

  public boolean isUserMasterpieceChecked() {
    return isUserMasterpieceChecked;
  }

  public void setUserMasterpieceChecked(boolean userMasterpieceChecked) {
    isUserMasterpieceChecked = userMasterpieceChecked;
  }

  public UserMasterpiece getUserMasterpiece() {
    return userMasterpiece;
  }

  public void setUserMasterpiece(UserMasterpiece userMasterpiece) {
    this.userMasterpiece = userMasterpiece;
  }

  public List<String> getUserTypes() {
    return userTypes;
  }

  public void setUserTypes(List<String> userTypes) {
    this.userTypes = userTypes;
  }
}
