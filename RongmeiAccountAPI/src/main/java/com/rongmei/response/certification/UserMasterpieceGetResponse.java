package com.rongmei.response.certification;

import com.rongmei.entity.certification.UserMasterpiece;
import com.rongmei.response.Response;
import java.util.List;

public class UserMasterpieceGetResponse extends Response {

  private List<UserMasterpiece> userMasterpieceList;

  public UserMasterpieceGetResponse() {
  }

  public UserMasterpieceGetResponse(
      List<UserMasterpiece> userMasterpieceList) {
    this.userMasterpieceList = userMasterpieceList;
  }

  public List<UserMasterpiece> getUserMasterpieceList() {
    return userMasterpieceList;
  }

  public void setUserMasterpieceList(
      List<UserMasterpiece> userMasterpieceList) {
    this.userMasterpieceList = userMasterpieceList;
  }
}
